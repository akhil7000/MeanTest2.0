
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;


public class JsonToXml {

	public static void main(String[] args) {
		
		
		//String jsonFilePath = "D:\\MEANtest\\sampleJson\\myjosn.json";
		String fileName = "myjosn";
		
		//working
		String jsonFilePath = System.getProperty("user.dir")+"\\testJsonFiles\\"+fileName+".json";
		String xmlFilePath = System.getProperty("user.dir")+"\\testJsonFiles\\"+fileName+".xml";
		
		/*//Testing
		String jsonFilePath = "D:\\MEANtest\\"+"todoCtrlJson"+".json";
		String xmlFilePath = "D:\\MEANtest\\"+"todoCtrlJson"+".xml";*/
		
		String jsonData = "";
		//StringBuffer sb = null;
		System.out.println(jsonFilePath);
		//System.out.println(new File(jsonFilePath).exists());
		JSONObject json = null;
		String xmlData = null;
		try { 
			jsonData = fileDataReader(jsonFilePath);
			//System.out.println(jsonDataFile);
			json = new JSONObject(jsonData);
			xmlData = XML.toString(json);
			xmlData = "<root>"+xmlData+"</root>";
			/*json = XML.toJSONObject(jsonFilePath);
			xml = XML.toString(json);*/
			System.out.println();
			//System.out.println(xmlData);
			fileDataWriter(xmlData,xmlFilePath);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
	}
	
	public void jsonToXmlConvertor()
	{
		String rootPath = System.getProperty("user.dir");
		File jsonFolder = new File(rootPath.concat("\\MEANTest_Json"));
		String jsonFilePath = "";
		String xmlFilePath = "";
		String xmlData = null;
		for(File tempFile : jsonFolder.listFiles())
		{
			jsonFilePath = tempFile.getAbsolutePath();
			xmlFilePath = tempFile.getAbsolutePath().replace("MEANTest_Json", "MEANTest_Xml").replace(".json", ".xml");
			System.out.println(jsonFilePath);
			System.out.println(xmlFilePath);
			try{
				xmlData = "<root>"+XML.toString(new JSONObject(fileDataReader(jsonFilePath)))+"</root>";
				xmlData = prettyFormat(xmlData, 2);
				fileDataWriter(xmlData,xmlFilePath);
			}catch (JSONException e) {
				e.printStackTrace();
			}
		}
		
		
	}
	
	public static String prettyFormat(String input, int indent) {
	    try {
	        Source xmlInput = new StreamSource(new StringReader(input));
	        StringWriter stringWriter = new StringWriter();
	        StreamResult xmlOutput = new StreamResult(stringWriter);
	        TransformerFactory transformerFactory = TransformerFactory.newInstance();
	        transformerFactory.setAttribute("indent-number", indent);
	        Transformer transformer = transformerFactory.newTransformer(); 
	        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
	        transformer.transform(xmlInput, xmlOutput);
	        return xmlOutput.getWriter().toString();
	    } catch (Exception e) {
	        throw new RuntimeException(e); // simple exception handling, please review it
	    }
	}
	public static String fileDataReader(String jsonFilePath) {
		StringBuffer sb = new StringBuffer();
		FileReader fr = null;
		BufferedReader br = null;
		String s;
		try {
			fr = new FileReader(jsonFilePath);
			br = new BufferedReader(fr);
			while ((s = br.readLine()) != null) {
				sb = sb.append(s);
			}
			fr.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return sb.substring(0, sb.length());
	}
	
	public static void fileDataWriter(String xmlData,String xmlFilePath)
	{
		FileWriter fw = null;
		BufferedWriter bw = null;
		try{
			fw = new FileWriter(xmlFilePath);
			bw = new BufferedWriter(fw);
			bw.write(xmlData);
			bw.close();
		}catch(Exception e)
		{
			e.printStackTrace();
		}
	}

}
