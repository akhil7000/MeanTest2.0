import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.json.simple.parser.ParseException;




public class Runner 
{

	public static void main(String[] args) throws DocumentException, Exception
	{
		//get rootpath
		String rootPath=System.getProperty("user.dir");
		//String rootPath="C:\\Users\\10614157\\Desktop\\Meantest 2 utilities\\New test\\angular2-sample-app-master";
		//File file = new File("C:/Users/10614157/Desktop/Meantest 2 utilities/angular2-sample-app-master");
		//String rootPath = file.getAbsolutePath();
		
		//create batch
		TscConfigurator tsc=new TscConfigurator(rootPath);
		tsc.generator();
		tsc.closeStreams();
		System.out.println("tsc.bat created");
		
		//create json
		Convertor sg=new Convertor(rootPath);
		sg.generator();
		sg.closeStreams();
		System.out.println("tscConfig and converter.bat");
		
		//execute batch
		Executer e=new Executer();
		e.execute(rootPath);
		System.out.println("convertor.bat executed");
		System.out.println("Files Converted");
		
		
		//delete json and batch
		ArrayList<String> directories=new  ArrayList<String>();
		directories.add("tsc.bat");
		directories.add("tsconfigsample.json");
		directories.add("convertor.bat");
		
		//To delete all the MEANTest_* folders(Json, Batch, Xml folders)
		clearDirectories(directories);
		
		//Setup esprima
		//Delete old library files and copy library zip file and unZip new library files
		UnzipUtility unzipper = new UnzipUtility();
		unzipper.unZipMethod(rootPath);
		
		//Read js from MEANTest folder recursively and pass it to esprima
		EsprimaJsToJson ej=new EsprimaJsToJson();
		ej.generate();
		ej.closeStreams();
		
		//Read jsons recursively and convert them to xmls
		System.out.print("Start");
		JsonToXml jTX = new JsonToXml();
		jTX.jsonToXmlConvertor();
		System.out.print("End");
		//Parse Xmls

		String className = null;
		String unitType= null;
		
		//String rootPath=System.getProperty("user.dir");
		ArrayList<String> xmls=new ArrayList<String>();
		ArrayList<String> methodList=new ArrayList<String>();
		HashMap<String,ArrayList<String>> methodDetails=new HashMap<String,ArrayList<String>>();
		
		HashMap<String,Element> classBody=new HashMap<String, Element>();
		HashMap<String,String> components=new HashMap<String, String>();
		HashMap<String,String> services=new HashMap<String, String>();
		
		String unitDetails=null;
		UnitTraverser traverser = null;
		String[] temp;
		File xmlFolder=new File(rootPath+"//MEANTest_Xml");
		System.out.println("reading");
		
		//unit identification
		if(xmlFolder.exists())
		{
			
			xmls=listXmlsForFolder(xmlFolder,xmls);
			for(int i=0;i<xmls.size();i++)
			{
				System.out.println("reading : "+xmls.get(i)+" for component traversal");
				traverser=new UnitTraverser(xmls.get(i));
				unitDetails=traverser.getComponentDetails();
				temp=unitDetails.split("__");
				className=temp[0];
				unitType=temp[1];
				//System.out.println("traverser.getBody() : "+traverser.getBody());
				classBody.put(className,traverser.getBody());
				if(unitType.equalsIgnoreCase("component"))
				{
					components.put(className,xmls.get(i));
				}
				if(unitType.equalsIgnoreCase("injectable"))
				{
					services.put(className,xmls.get(i));
				}
				

			}
		}
		
		//detailed traversal
		Iterator servIterator = services.entrySet().iterator();
		while (servIterator.hasNext()) 
		{
			Map.Entry mEntry = (Map.Entry) servIterator.next();
			Object key=mEntry.getKey();
			String serviceName=(String) key;
			String filePath=services.get(key);
			System.out.println("");
			System.out.println("reading : "+filePath+" for detailed traversal");
			traverser=new UnitTraverser(filePath);
			//System.out.println("this : "+classBody.get(serviceName));
			traverser.methodExtract(serviceName,classBody.get(serviceName));
			//methodList=traverser.getMethods();
			methodDetails=traverser.getMethodDetails();
			traverser.callServiceGenerator(serviceName,filePath,methodDetails);
			
		}
		
		
//		if(xmlFolder.exists())
//		{
//			
//			xmls=listXmlsForFolder(xmlFolder,xmls);
//			for(int i=0;i<xmls.size();i++)
//			{
//				System.out.println("reading : "+xmls.get(i)+" for detailed traversal");
//				//UnitTraverser traverser=new UnitTraverser(xmls.get(i));
//				traverser.methodExtract(className);
//				
//			}
//		}
		
		//spec generation
		/*Iterator serviceIterator = services.entrySet().iterator();
		while (serviceIterator.hasNext()) 
		{
			Map.Entry mEntry = (Map.Entry) serviceIterator.next();
			Object key=mEntry.getKey();
			String serviceName=(String) key;
			String filePath=services.get(key);
			System.out.println("service name :"+serviceName+" is in file : "+filePath);
//			traverser.methodExtract(serviceName);
			//call parser
			//call to msethodfecthing
			
			//call spec generator
			traverser=new UnitTraverser(filePath);
			methodDetails=traverser.getMethodDetails();
			System.out.println("checkjkk"+methodDetails.size());
			traverser.callServiceGenerator(serviceName,filePath,methodDetails);
			
		}*/
		Iterator componentIterator = components.entrySet().iterator();
		while (componentIterator.hasNext()) 
		{
			Map.Entry mEntry = (Map.Entry) componentIterator.next();
			Object key=mEntry.getKey();
			String componentName=(String) key;
			String filePath=components.get(key);
			System.out.println("component name :"+componentName+" is in file : "+filePath);
			
			//call parser
			//call to methodfecthing
			
			
			//call spec generator
			traverser=new UnitTraverser(filePath);
			traverser.callComponentGenerator(componentName,filePath);
		}
		 

		//Generate specs
		
		//delete unnecessary data
		//Delete all unnecessary directories
		ArrayList<String> dir=new  ArrayList<String>();
		dir.add(ej.jsonsPath);
		dir.add(ej.batchPath);
		dir.add(ej.xmlPath);
		dir.add(rootPath.concat("\\MEANTest_modules"));
				
		//To delete all the MEANTest_* folders(Json, Batch, Xml folders)
		clearDirectories(dir);
		System.out.println("Exited Spec Generation Function");
		System.out.println("Spec Generation completed! Check the autogenerated specs in MEANTest_Specs directory.");
	}
	
	@SuppressWarnings("rawtypes")
	/*public static void parse() throws DocumentException, Exception 
	{
		String className = null;
		String unitType= null;
		
		String rootPath=System.getProperty("user.dir");
		ArrayList<String> xmls=new ArrayList<String>();
		ArrayList<String> methodList=new ArrayList<String>();
		HashMap<String,ArrayList<String>> methodDetails=new HashMap<String,ArrayList<String>>();
		
		HashMap<String,Element> classBody=new HashMap<String, Element>();
		HashMap<String,String> components=new HashMap<String, String>();
		HashMap<String,String> services=new HashMap<String, String>();
		
		String unitDetails=null;
		UnitTraverser traverser = null;
		String[] temp;
		File xmlFolder=new File(rootPath+"//MEANTest_Xml");
		System.out.println("reading");
		
		//unit identification
		if(xmlFolder.exists())
		{
			
			xmls=listXmlsForFolder(xmlFolder,xmls);
			for(int i=0;i<xmls.size();i++)
			{
				System.out.println("reading : "+xmls.get(i)+" for component traversal");
				traverser=new UnitTraverser(xmls.get(i));
				unitDetails=traverser.getComponentDetails();
				temp=unitDetails.split("__");
				className=temp[0];
				unitType=temp[1];
				//System.out.println("traverser.getBody() : "+traverser.getBody());
				classBody.put(className,traverser.getBody());
				if(unitType.equalsIgnoreCase("component"))
				{
					components.put(className,xmls.get(i));
				}
				if(unitType.equalsIgnoreCase("injectable"))
				{
					services.put(className,xmls.get(i));
				}
				

			}
		}
		
		//detailed traversal
		Iterator servIterator = services.entrySet().iterator();
		while (servIterator.hasNext()) 
		{
			Map.Entry mEntry = (Map.Entry) servIterator.next();
			Object key=mEntry.getKey();
			String serviceName=(String) key;
			String filePath=services.get(key);
			System.out.println("");
			System.out.println("reading : "+filePath+" for detailed traversal");
			traverser=new UnitTraverser(filePath);
			//System.out.println("this : "+classBody.get(serviceName));
			traverser.methodExtract(serviceName,classBody.get(serviceName));
			//methodList=traverser.getMethods();
			methodDetails=traverser.getMethodDetails();
			traverser.callServiceGenerator(serviceName,filePath,methodDetails);
			
		}
		
		
//		if(xmlFolder.exists())
//		{
//			
//			xmls=listXmlsForFolder(xmlFolder,xmls);
//			for(int i=0;i<xmls.size();i++)
//			{
//				System.out.println("reading : "+xmls.get(i)+" for detailed traversal");
//				//UnitTraverser traverser=new UnitTraverser(xmls.get(i));
//				traverser.methodExtract(className);
//				
//			}
//		}
		
		//spec generation
		Iterator serviceIterator = services.entrySet().iterator();
		while (serviceIterator.hasNext()) 
		{
			Map.Entry mEntry = (Map.Entry) serviceIterator.next();
			Object key=mEntry.getKey();
			String serviceName=(String) key;
			String filePath=services.get(key);
			System.out.println("service name :"+serviceName+" is in file : "+filePath);
//			traverser.methodExtract(serviceName);
			//call parser
			//call to msethodfecthing
			
			//call spec generator
			traverser=new UnitTraverser(filePath);
			methodDetails=traverser.getMethodDetails();
			System.out.println("checkjkk"+methodDetails.size());
			traverser.callServiceGenerator(serviceName,filePath,methodDetails);
			
		}
		Iterator componentIterator = components.entrySet().iterator();
		while (componentIterator.hasNext()) 
		{
			Map.Entry mEntry = (Map.Entry) componentIterator.next();
			Object key=mEntry.getKey();
			String componentName=(String) key;
			String filePath=components.get(key);
			System.out.println("component name :"+componentName+" is in file : "+filePath);
			
			//call parser
			//call to methodfecthing
			
			
			//call spec generator
			traverser=new UnitTraverser(filePath);
			traverser.callComponentGenerator(componentName,filePath);
		}
		 
}*/
	public static ArrayList<String> listXmlsForFolder(final File folder, ArrayList<String> xmls) throws IOException 
	{
		
	    for (final File fileEntry : folder.listFiles()) 
	    {
	        if (!(fileEntry.isDirectory())) 
	        {
	        	String FileName=fileEntry.getName();
	        	if(FileName.endsWith(".xml"))
	        	{
	        		String FilePath=fileEntry.getAbsolutePath();
	        		
	        		System.out.println("XML File : "+FileName+" is at  : "+FilePath);
	        		if(!(xmls.contains(FilePath)))
	        		{
	        			xmls.add(FilePath);
	        		}
	        		
	        	}
	        	
	           
	        } 
	        else 
	        {
	        	
	        	 listXmlsForFolder(fileEntry,xmls);
	        }
	    }
	    
	    return xmls;
	}

	private static void clearDirectories(ArrayList<String> directories) 
	{
		File f = null;
		for(int i=0; i<directories.size();i++)
		{
			f = new File(directories.get(i));
			FileUtils.deleteQuietly(f);
		}
		
	}

}
