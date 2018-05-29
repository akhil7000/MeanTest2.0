
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


public class Executer
{
	public static JSONObject obj = new JSONObject();
	public static FileWriter fw;
	public static FileReader fr;
	public static BufferedWriter output;
	

	public void execute(String rootPath) throws FileNotFoundException, IOException, ParseException 
	{
		createProcess(rootPath);
		System.out.println("executing bat : "+"convertor.bat");
		//ProcessBuilder Msys = new ProcessBuilder("cmd.exe", "/C",batchPath+"\\"+outputFileName+".bat");
	    /*ProcessBuilder Msys = new ProcessBuilder("cmd.exe", "/C","\""+rootPath+"\\convertor.bat\"");
		//Process p = Runtime.getRuntime().exec("cmd.exe", "/C","\""+rootPath+"\\convertor.bat\"");
		Process p = Msys.start();

		try {
			p.waitFor();		
		} catch (InterruptedException e) {
			e.printStackTrace();
		}catch (Exception e) {
			e.printStackTrace();
		}*/
		
		ProcessBuilder Msys = new ProcessBuilder("cmd.exe", "/C","\""+rootPath+"\\convertor.bat\"");
		final Process p = Msys.start();
	
		try {
			new Thread() {
			    @Override
			    public void run() {
			        try {
			            for(int i=0;i<1;i++)
			            {
			            	System.out.println("Sleeping for "+i+" sec");
			            	sleep(1000);
			            }
			        } catch (InterruptedException e) {
			            e.printStackTrace();
			        }
			        System.out.println("Force kil Start");
			        p.destroy();
			        System.out.println("Force kill End");

			    }
			}.start();
			p.waitFor();
			printLines(" stdout:", p.getInputStream());
			printLines(" stderr:", p.getErrorStream());
			
		} catch (InterruptedException e) {
			e.printStackTrace();
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}
	private static void printLines(String name, InputStream ins)
			throws Exception {
		String line = null;
		BufferedReader in = new BufferedReader(new InputStreamReader(ins));
		try {
			while ((line = in.readLine()) != null) {
				System.out.println(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	private void createProcess(String rootPath) throws IOException 
	{
		
		// TODO Auto-generated method stub
		File file=new File(rootPath+"\\convertor.bat");
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
		//output.write("ECHO start!");
		output.write("\r\n");
		output.write("tsc --p tsconfigsample.json");
		output.write("\r\n");
		//output.write("ECHO done!");
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

	
}
