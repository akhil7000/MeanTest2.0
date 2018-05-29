


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
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

@SuppressWarnings("rawtypes")
public class componentSpecGenerator 
{

	
		File file;
		BufferedReader ts;
		BufferedWriter output;
		FileWriter fw;
		FileReader fr;
		String rootPath=System.getProperty("user.dir");

		public componentSpecGenerator(File path,String fileName) {
			super();
			//generate standard name for spec.ts files
			String specName=fileName+".spec.ts";
			String specPath = path.getParent()+"\\"+specName;
			System.out.println("checking path "+path);
			
			
			file= new File(specPath);
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
			
			//for reading imports from ts file
			try {
				ts = new BufferedReader(new FileReader(path));
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
		}
		//creating spec template
		
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

		
		public void generator(String serviceName) throws IOException
		{
			
			String replacementString,line,tsLine;
			
			InputStream stream = this.getClass().getResourceAsStream("/templates/ComponentTemplate.properties");
			InputStreamReader isr=new InputStreamReader(stream);
			BufferedReader br=new BufferedReader(isr);
			

			tsLine=null;
			int flag=0;
			//copying imports from .ts to spec.ts
			while((tsLine = ts.readLine()) != null)
			{		
			     if (tsLine.contains("import")) // put your test conditions here
			     {
		    			output.write("\r\n");
		                output.write(tsLine);
		                flag=1; 
			     }
			      
			}
		     ts.close();
		     if(flag==1)
		     System.out.println("this file contains imports");

			
			//replacement scenarios
	    	while((line=br.readLine())!=null)
	    	{
	    		
//	    		if(line.equals("import { serviceName } from './importPath';"))
//	    		{
//	    			
//	    			replacementString=line.replaceAll("serviceName",serviceName);
//	    			line=replacementString;
//	    			//System.out.println("line  :"+line);
//	    			output.write("\r\n");
//	                output.write(line);
//	    			
//	    		}
	    		 if(line.equals("describe('Testing serviceName', () => {"))
	    		{
	    			
	    			replacementString=line.replaceAll("serviceName",serviceName);
	    			line=replacementString;
	    			//System.out.println("line  :"+line);
	    			output.write("\r\n");
	                output.write(line);
	    			
	    		}
	    		else if(line.equals("  let subject: serviceName;"))
	    		{
	    			
	    			replacementString=line.replaceAll("serviceName",serviceName);
	    			line=replacementString;
	    			//System.out.println("line  :"+line);
	    			output.write("\r\n");
	                output.write(line);
	    			
	    		}
	    		else if(line.equals("    subject = new serviceName();"))
	    		{
	    			
	    			replacementString=line.replaceAll("serviceName",serviceName);
	    			line=replacementString;
	    			//System.out.println("line  :"+line);
	    			output.write("\r\n");
	                output.write(line);
	    			
	    		}
	    		else {
	    			output.write("\r\n");
	                output.write(line); 
	                
	    		}
	    		
	    		
	    	}//while
	    	br.close();
	    	
			
		}//gen()



}



