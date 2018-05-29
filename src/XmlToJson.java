


import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;


public class XmlToJson 
{
	public static int PRETTY_PRINT_INDENT_FACTOR = 4;
	
    public String convert(StringBuilder sb)
    {
    	
    	String TEST_XML_STRING =sb.toString();
    	
    	String jsonPrettyPrintString = null;
    	
    	try 
    	{
            JSONObject xmlJSONObj = XML.toJSONObject(TEST_XML_STRING);
            jsonPrettyPrintString= xmlJSONObj.toString(PRETTY_PRINT_INDENT_FACTOR);
            
        } catch (JSONException je) 
        {
            
        }
		return jsonPrettyPrintString;
    }

}
