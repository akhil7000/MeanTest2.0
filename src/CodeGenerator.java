


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

@SuppressWarnings("unused")
public class CodeGenerator 
{
	
	File file;
	BufferedWriter output;
	FileWriter fw;
	FileReader fr;
	String rootPath;
	String conditionPath="\\MEANTest_modules\\MEANTest_conditions\\";
	public CodeGenerator(String rootPath) {
		super();
		this.rootPath=rootPath;
		
	}

	public ArrayList<String> getCondition(ArrayList<String> jsonStrings, String methodName) throws InterruptedException 
	{
		// TODO Auto-generated method stub
		BufferedReader br = null;
		ArrayList<String> ifConditions=new ArrayList<String>();
		try{
			
			    //get string from txt and separate it into responses
			    br = new BufferedReader(new FileReader(rootPath+conditionPath+methodName+".txt"));
			    for ( String line = br.readLine();line!=null; line = br.readLine())
				{
			    	if(!line.contentEquals("#"))
			    	{
			    		//System.out.println("file read as : "+line);
			    		ifConditions.add(line);
			    	}
				}	    
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		finally{
			try {
				br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		
		return ifConditions;
	}
	
	public void executeBatch(String methodName)
	{
		ProcessBuilder Msys = new ProcessBuilder("cmd.exe", "/C",rootPath+conditionPath+methodName+".bat");
	    try {
			Process p = Msys.start();
			p.waitFor();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	

	public void createGenerator(ArrayList<String> jsonStrings, String methodName) throws IOException 
	{
		
		
		File file=new File(rootPath+conditionPath+methodName+".js");
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
			
			e.printStackTrace();
		}
		
		
		//get esprima
		output.write("var escodegen = require(\'escodegen\');");
		output.write("\r\n");
		
		
		
		// TODO Auto-generated method stub
		for(int index=0;index<jsonStrings.size();index++)
		{
			String line1="var condition_no=escodegen.generate(";
			String result=line1.replace("_no","_"+(index+1));
			output.write(result);
			output.write("\r\n");
			output.write(jsonStrings.get(index));
			output.write("\r\n");
			output.write(");");
			output.write("\r\n");
			String log="console.log(condition_no);";
			String resultLog=log.replace("_no","_"+(index+1));
			output.write(resultLog);
			output.write("\r\n");
			output.write("console.log(\"#\");");
			output.write("\r\n");
		}
		
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
	}//close

	public void createBat(String methodName) throws IOException 
	{
		
		
		
		File file=new File(rootPath+conditionPath+methodName+".bat");
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
		
		//output.write("\r\n");
		//output.write("c:");
		//output.write("\r\n");
		//output.write("cd "+rootPath+"\\MEANTest_modules\\MEANTest_conditions\\");
		output.write("\r\n");
		output.write("node \""+rootPath+conditionPath+methodName+".js\" >\""+rootPath+conditionPath+methodName+".txt\"");
		output.write("\r\n");
		output.write("ECHO done!");
		output.write("\r\n");
		output.write("exit");
		output.write("\r\n");
		
		
	}
	

}
