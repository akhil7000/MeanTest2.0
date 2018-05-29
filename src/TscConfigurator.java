

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
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
public class TscConfigurator 
{
		
	
		File file;
		BufferedWriter output;
		FileWriter fw;
		FileReader fr;
		public TscConfigurator(String rootPath) {
			super();
			String path = rootPath+"\\tsc.bat";
			file= new File(path);
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

		
		public void generator() throws IOException
		{
			String line;
			
			InputStream stream = this.getClass().getResourceAsStream("/templates/tscTemplate.properties");
			InputStreamReader isr=new InputStreamReader(stream);
			BufferedReader br=new BufferedReader(isr);
			
			while((line=br.readLine())!=null)
	    	{
				
	    		
	    			output.write("\r\n");
	                output.write(line); 
	                
	    		
	    		
	    		
	    	}//while
			System.out.println("tsc written");
	    	br.close();
			
		}//gen()

		

}



