

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

import org.dom4j.Element;

@SuppressWarnings("rawtypes")
public class serviceSpecGenerator 
{

	
		File file;
		BufferedReader ts;
		BufferedWriter output;
		FileWriter fw;
		FileReader fr;
		String rootPath=System.getProperty("user.dir");

		public serviceSpecGenerator(File path,String fileName) {
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

		
		public void generator(String serviceName,HashMap<String, ArrayList<String>> methodDetails) throws IOException
		{
			
			String replacementString,line,tsLine;
			String[] dep = new String[10];
			InputStream stream = this.getClass().getResourceAsStream("/templates/ServiceTemplate.properties");
			InputStreamReader isr=new InputStreamReader(stream);
			BufferedReader br=new BufferedReader(isr);
			
			System.out.println("methodList size"+methodDetails.size());
			System.out.println("Full Method Details="+methodDetails);


			tsLine=null;
			int flag=0,count=0;

			//copying imports from .ts to spec.ts
			while((tsLine = ts.readLine()) != null)
			{		
			     if (tsLine.contains("import")) // put your test conditions here
			     {	
			    	output.write("\r\n");
	                output.write(tsLine);
	                flag=1;
	                
		    			if(!(tsLine.contains("@angular")) && (tsLine.contains("{"))) 
		    			{
		    				dep[count]=tsLine.substring(tsLine.indexOf("{")+1, tsLine.indexOf("}")).trim();
		    				System.out.println(dep);
		    				count++;
		    			}
		    			
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
	    		 
	    		//in case of Testbed providers
	    		else if(line.equals("        providers: [serviceName"))
	    		{
	    			
	    			replacementString=line.replaceAll("serviceName",serviceName);
	    			line=replacementString;
	    			//System.out.println("line  :"+line);
	    			output.write("\r\n");
	                output.write(line);
	                if(dep!=null)
	                	for(int i=0;i<count;i++)
		    				output.write(","+dep[i]);
		    		output.write("]");
	    			
	    		} 
	    		 
	    		else if(line.equals("    subject = new serviceName();"))
	    		{
	    			
	    			replacementString=line.replaceAll("serviceName",serviceName);
	    			line=replacementString;
	    			//System.out.println("line  :"+line);
	    			output.write("\r\n");
	                output.write(line);
	    			
	    		}
	    		 
	    		 
	    		else if(line.equals("  	//testing service methods"))
	    		{
	    			

	    			output.write("\r\n");
        			String tab="";
                    
        			//tests for methods to be defined
        			output.write(tab+"		it('Test if all methods are Defined',function()");
                    output.write("\r\n");
                    output.write(tab+"		{");
                    output.write("\r\n");
//    	            output.write(tab+"			//test if method defined	");
//    	            output.write("\r\n");

	    			Iterator methodIterator = methodDetails.entrySet().iterator();
        			while (methodIterator.hasNext()) 
	        		{
	        			Map.Entry mEntry2 = (Map.Entry) methodIterator.next();
	        			Object key2=mEntry2.getKey();
	        			String methodName=(String) key2;
	        			ArrayList<String> details=methodDetails.get(key2);
	        			
	        			int paramsStartPoint=details.indexOf("MTEST_PAR");
	        			int ifsStartPoint=details.indexOf("MTEST_IFS");
	        			
	        			
	        			ArrayList<String> variables= new ArrayList<String>(details.subList(0,paramsStartPoint));
    					ArrayList<String> params=new ArrayList<String>(details.subList(paramsStartPoint+1,ifsStartPoint));
    					ArrayList<String> ifConditions=new ArrayList<String>(details.subList(ifsStartPoint+1,details.size()));
    					
	        			//System.out.println("variables list : : "+variables);
	        			//System.out.println("deps list : "+deps);
	        			//System.out.println("params list : "+params);
	        			//System.out.println("mockVars list : "+mockVars);
	        			//System.out.println("serviceMethods list : "+serviceMethods);
	        			//get parameters
	        			String paramList="";
		                for(int i=0;i<params.size();i++)
		                {
		                	if(i==params.size()-1)
		                	{
		                		paramList+=params.get(i);
		                	}
		                	else
		                	{
		                		paramList+=params.get(i)+",";
		                	}
			                
		                }	        			
	        			

	        			
	        			//System.out.println("if conditions for :"+methodName+"are :"+ifConditions);
	    	            output.write(tab+"			expect(subject."+methodName+"()).toBeDefined();");
//	    	            output.write(tab+"			service."+methodName+"("+paramList+");");
	                    output.write("\r\n");
	                    
//	                    System.out.println("Method Details="+details);


		                
	                    System.out.println("written tests for service!");
		    			
	        		}
                    output.write(tab+"		});");
                    output.write("\r\n");
                    output.write("\r\n");
        			

	    			
	    		}
	    		 
	    		else if(line.equals("  	//Test individual methods"))
	    		{
	    			

	    			output.write("\r\n");
        			String tab="";
                    
        			//tests for methods to be defined


	    			Iterator methodIterator = methodDetails.entrySet().iterator();
        			while (methodIterator.hasNext()) 
	        		{
	        			Map.Entry mEntry2 = (Map.Entry) methodIterator.next();
	        			Object key2=mEntry2.getKey();
	        			String methodName=(String) key2;
	        			ArrayList<String> details=methodDetails.get(key2);

	        			int paramsStartPoint=details.indexOf("MTEST_PAR");
	        			int ifsStartPoint=details.indexOf("MTEST_IFS");
	        			
	        			
	        			ArrayList<String> variables= new ArrayList<String>(details.subList(0,paramsStartPoint));
    					ArrayList<String> params=new ArrayList<String>(details.subList(paramsStartPoint+1,ifsStartPoint));
    					ArrayList<String> ifConditions=new ArrayList<String>(details.subList(ifsStartPoint+1,details.size()));
    					
    					
	        			String paramList="";
		                for(int i=0;i<params.size();i++)
		                {
		                	if(i==params.size()-1)
		                	{
		                		paramList+=params.get(i);
		                	}
		                	else
		                	{
		                		paramList+=params.get(i)+",";
		                	}
			                
		                }

	        			
	        			output.write(tab+"		xit('Test for "+methodName+"()',function()");
	                    output.write("\r\n");
	                    output.write(tab+"		{");
	                    output.write("\r\n");

	                    if(variables.size()>=1)
		                {
		                	output.write(tab+"			//SET ONLY the value for selective variables from below that affect the method code");
		                	
		                }
		                for(int i=0;i<variables.size();i++)
		                {
		                	
			                output.write("\r\n");
			                output.write(tab+"			var "+variables.get(i)+";");
		                }


	                    
	                    
	                    if(params.size()>=1 && !(params.get(0).contentEquals("")))
		                {
		                	output.write("\r\n");
		                	output.write(tab+"			//set value for paramaters of method");
		                	
		                }
		                for(int i=0;i<params.size();i++)
		                {
		                	if(!(params.get(0).contentEquals("")))
			                {
		                		output.write("\r\n");
				                output.write(tab+"			var "+params.get(i)+";");
			                }
			                
		                }
		                
                		output.write("\r\n");
	                    output.write("\r\n");
	                	output.write(tab+"			//make a method call");
		                output.write("\r\n");
		                output.write(tab+"			"+methodName+"("+paramList+");");
		                
		                //expectations for variables
		                for(int i=0;i<variables.size();i++)
		                {
		                	output.write("\r\n");
		                	output.write(tab+"			expect("+methodName+"."+variables.get(i)+").toEqual();");
			               
		                }
		               
		                output.write("\r\n");
		                //suggestions to cover if conditions
		                if(ifConditions.size()>0)
		                {
		                	output.write("\r\n");
		                	output.write(tab+"			//repeat required steps for coverage of following 'if' conditions :");
			                
			                for(int i=0;i<ifConditions.size();i++)
			                {
			                	output.write("\r\n");
			                	output.write(tab+"			// "+(i+1)+" :  ("+ifConditions.get(i)+")");
				                
			                }
		                }


	                    
	                    output.write("\r\n");
	                    output.write(tab+"		});");
	                    output.write("\r\n");
	                    output.write("\r\n");
	                    


		              System.out.println("written tests for methods individually!");
		    			
	        		}
//                    output.write(tab+"		});");
//                    output.write("\r\n");
//                    output.write("\r\n");
        			

	    			
	    		}
	    		 
	    		 
	    		
 
	    		  
	    		 
	    		else {
	    			output.write("\r\n");
	                output.write(line); 
	                
	    		}
	    		
	    		
	    	}//while
	    	br.close();
	    	
			
		}//gen()



}



