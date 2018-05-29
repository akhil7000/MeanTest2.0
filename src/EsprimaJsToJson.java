
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.apache.commons.io.FileUtils;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


public class EsprimaJsToJson
{
	public static JSONObject obj = new JSONObject();
	public static FileWriter fw;
	public static FileReader fr;
	public static BufferedWriter output;
	public String rootPath;
	public String generatorsPath;
	public String esprimaGenPath;
	public String jsonsPath;
	public String batchPath;
	public String xmlPath;
	public String testPath;
	public String extrasPath;
	
	ArrayList<String> excludeLibFiles = new ArrayList<String>();
	ArrayList<String> excludeLibFolders = new ArrayList<String>();
	
	public EsprimaJsToJson() throws IOException
	{
		super();
		/*get the path of jar*/
		rootPath=System.getProperty("user.dir");
		/*end get the path of jar*/
	
		jsonsPath=rootPath.concat("\\MEANTest_Json");
		batchPath=rootPath.concat("\\MEANTest_Batch");
		xmlPath=rootPath.concat("\\MEANTest_Xml");
		testPath=rootPath.concat("\\MEANTest_Specs");
		extrasPath=rootPath.concat("\\MEANTest_Extras");
		
		/*create directory for es generator files for each js*/
		ArrayList<String> directories=new  ArrayList<String>();
		directories.add(jsonsPath);
		directories.add(batchPath);
		directories.add(xmlPath);
		directories.add(testPath);
		directories.add(extrasPath);
		//directories.add(rootPath+"\\MEANTest_Extras\\ScriptMappings.json");
		clearDirectories(directories);
		
		//directories.remove(rootPath+"\\MEANTest_Extras\\ScriptMappings.json");
		createDirectories(directories);
		
		
	}
	

	private void createDirectories(ArrayList<String> directories) 
	{
		File f = null;
		for(int i=0; i<directories.size();i++)
		{
			f = new File(directories.get(i));
			f.mkdir();
		}
		
	}


	private void clearDirectories(ArrayList<String> directories)
	{
		File f = null;
		for(int i=0; i<directories.size();i++)
		{
			f = new File(directories.get(i));
			FileUtils.deleteQuietly(f);
		}
	}

	public void generate() throws IOException
	{
		
		
		// 1 : Retrieve all js files and store with their relative paths
		ArrayList<String> scripts=new ArrayList<String>();
		File mappingFile= new File(rootPath+"\\MEANTest_Extras\\ScriptMappings.json");
		fw= new FileWriter(mappingFile);
		
		final File folder = new File(rootPath+"\\MeanTest");
		
		//call to ignore library files
		//excludeLibraries();

		//fetch all the JS files from the Application Folder
		scripts=listScriptsForFolder(folder,scripts);
		System.out.println("List of all the js Scripts :"+scripts);
		
		fw.write(obj.toJSONString());
		fw.close();
		System.out.println("Mapping js files with their relative paths done!");
		
		
		//3 : Take each script location from mapped json and pass it to esprima
		try {
			retrieveData(scripts);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// Delete all batch files
		//FolderToDelete=new File(batchPath);
		//FileUtils.deleteDirectory(FolderToDelete);
		
	}
	

	private void retrieveData(ArrayList<String> scripts) throws FileNotFoundException, IOException, ParseException 
	{
		
		JSONParser parser = new JSONParser();
		Object obj = parser.parse(new FileReader(rootPath+"\\MEANTest_Extras\\ScriptMappings.json")); 
		JSONObject jsonObject = (JSONObject) obj;
		for(int index=0;index<scripts.size();index++)
		{
			String file=scripts.get(index);
			String filePath = (String) jsonObject.get(file);
			String outputFileName=file.substring(0,file.indexOf("."));
			String outputFilePath=jsonsPath+"\\"+outputFileName+".json";
			//String generatorFilePath=esprimaGenPath;
			String generatorFilePath=rootPath+"\\MEANTest_modules\\esprimaGenerator.js";
			File generatorFile = new File(generatorFilePath);
			if(!generatorFile.exists())
				generatorFilePath=rootPath+"\\bin\\MEANTest_modules\\esprimaGenerator.js";
			createProcess(filePath,outputFilePath,outputFileName,generatorFilePath);
			System.out.println("executing bat : "+outputFileName+".bat");
			//ProcessBuilder Msys = new ProcessBuilder("cmd.exe", "/C",batchPath+"\\"+outputFileName+".bat");
			ProcessBuilder Msys = new ProcessBuilder("cmd.exe", "/C","\""+batchPath+"\\"+outputFileName+".bat\"");
			Process p = Msys.start();
			try {
				p.waitFor();
				printLines(" stdout:", p.getInputStream());
				printLines(" stderr:", p.getErrorStream());
			} catch (InterruptedException e) {
				e.printStackTrace();
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		
	}
	private static void printLines(String name, InputStream ins)
			throws Exception {
		String line = null;
		BufferedReader in = new BufferedReader(new InputStreamReader(ins));
		try {
			while ((line = in.readLine()) != null) {
				//System.out.println(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	private void createProcess(String inputFilePath, String outputFilePath, String fileName, String generatorFilePath) throws IOException 
	{
		
		// TODO Auto-generated method stub
		File file=new File(batchPath+"\\"+fileName+".bat");
		file.getParentFile().mkdirs();
		try {
			file.createNewFile();
			//file.setReadable(false);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		try {
			fw=new FileWriter(file);
			fw.flush();
			output = new BufferedWriter(fw);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		//get esprima
		output.write("ECHO start!");
		output.write("\r\n");
		output.write("c:");
		output.write("\r\n");
		output.write("node \""+generatorFilePath+"\" \""+inputFilePath+"\" \""+outputFilePath+"\"");
		output.write("\r\n");
		output.write("ECHO done!");
		output.write("\r\n");
		output.write("exit");
		output.write("\r\n");
		output.close();
		
	}

	public void closeStreams() 
	{
		try 
		{
			output.close();
		} catch (IOException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}



	@SuppressWarnings("unchecked")
	public ArrayList<String> listScriptsForFolder(final File folder, ArrayList<String> scripts) throws IOException 
	{
		
		
	    for (final File fileEntry : folder.listFiles()) 
	    {
	        if (!(fileEntry.isDirectory())) 
	        {
	        	String FileName=fileEntry.getName();
	        	if(FileName.endsWith(".js"))
	        	{
	        		String FilePath=fileEntry.getAbsolutePath();
	        		//if(!(FileName.equalsIgnoreCase("ESGeneratorTemplate.js")))
	        		if(!excludeLibFiles.contains(fileEntry.getAbsolutePath()))
	        		{
	        			
	        			System.out.println("File : "+FileName+" is at  : "+FilePath);
		        		//write to json
		        		obj.put(FileName,FilePath);
		        		if(!(scripts.contains(FileName)))
		        		{
		        			scripts.add(FileName);
		        		}
		        		
	        		}
	        		
	        	}
	        	
	           
	        } 
	        else 
	        {
	        	if(!excludeLibFolders.contains(fileEntry.getAbsolutePath()))
	        	 listScriptsForFolder(fileEntry,scripts);
	        }
	    }
	    
	    return scripts;
	}
	
	public void excludeLibraries()
	{
		//Exclude library files and folders
		excludeLibFiles = new ArrayList<String>();
		excludeLibFolders = new ArrayList<String>();
		excludeLibFolders.add(rootPath+"\\MEANTest_modules");
		excludeLibFolders.add(rootPath+"\\MEANTest_modules_karma");
		excludeLibFolders.add(rootPath+"\\MEANTest_Reports");
		excludeLibFolders.add(rootPath+"\\TestCoverage_Reports");
		excludeLibFolders.add(rootPath+"\\TestExecution_Reports");
		String libTxtPath = ""; 
		
		for(int i=0;i<2;i++)
		{
			if(i==0)
				libTxtPath = rootPath+"\\ExcludeLibraries.txt"; 
			else
				libTxtPath = rootPath+"\\ExcludeScripts.txt"; 
			FileReader fs = null;
			BufferedReader br =  null;
			String line = "";
			File fileOrDirectory = null;
			try{
				fs = new FileReader(new File(libTxtPath));
				br = new BufferedReader(fs);
				while((line = br.readLine())!=null)
				{
					
					fileOrDirectory = new File(rootPath+"\\"+line);
					if(fileOrDirectory.isDirectory())
					{
						excludeLibFolders.add(fileOrDirectory.getAbsolutePath());
					}
					else if(fileOrDirectory.isFile())
					{
						excludeLibFiles.add(fileOrDirectory.getAbsolutePath());
					}
				}
			}catch(FileNotFoundException e)
			{
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
		
		
	}

}
