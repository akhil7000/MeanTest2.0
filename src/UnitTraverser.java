


import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.xml.sax.SAXException;
import org.dom4j.Node;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;




public class UnitTraverser 

{
	File xml;
	Document doc;
	SAXReader reader = null;
	int noOfModules= 1;
	String FileName= null,nameOfController=null,nameOfModule=null;
	String rootPath=System.getProperty("user.dir");
	File xmlFolder;
	String jsFilePath;
	File jsFolder = new File(rootPath+"/MeanTest");
	//element to store class body
	Element classBodyEle=null;
	//maps methods and their corresponding bodies
	HashMap<String,ArrayList<Element>> methodMappings=new HashMap<String,ArrayList<Element>>();
	
	ArrayList<String> component=new ArrayList<String>();
	ArrayList<String> service=new ArrayList<String>();
	ArrayList<String> ifConditions=new ArrayList<String>();

	//hashmap  for storing methodwise function dependencies
	
	//hashmap for storing method details
	HashMap<String,ArrayList<String>> methodDetails=new HashMap<String,ArrayList<String>>();
	ArrayList<String> details;
	//thisMethods  : service method list
	ArrayList<String> thisMethods=new ArrayList<String>();
	
	
	public UnitTraverser(String filePath) throws SAXException, IOException, ParserConfigurationException {
		
		
		xml=new File(filePath);
		FileName=xml.getName();
		System.out.println("file name :"+FileName);
		reader = new SAXReader();
		try {
			doc= reader.read(xml);
			
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@SuppressWarnings("unchecked")
	public String getComponentDetails() throws DocumentException, Exception 
	{
		String className = null,exportClassName=null,unitType=null;
		
		//get root element of the xml
	
		Element rootEle=doc.getRootElement();
		//Identify the unit
		
		List<Node> prop=rootEle.selectNodes(".//properties");
		//System.out.println(prop.size());
		for(int propsindex=0;propsindex<prop.size();propsindex++)
		{
			Element propEle=(Element) prop.get(propsindex);
			Node propKey=propEle.selectSingleNode("key");
			if(propKey!=null)
			{
				Element propKeyEle=(Element) propKey;
				Node propName=propKeyEle.selectSingleNode("name");
				if(propName!=null)
				{
					Element propNameEle=(Element) propName;
					if(propNameEle.getText().contentEquals("execute"))
					{
						//System.out.println("got execute");
						
						//get value
						Node propValue=propEle.selectSingleNode("value");
						Element propValueEle=(Element) propValue;
						Node propValueType=propValueEle.selectSingleNode("type");
						Element propValueTypeEle=(Element) propValueType;
						if(propValueTypeEle.getText().contentEquals("FunctionExpression"))
						{
							//System.out.println("got FunctionExpression");
							
							/* this might not be needed 
							//get first assignment expression and it's left value
							List<Node> exp=propValueEle.selectNodes(".//expression");
							
							if(exp.size()>=2)
							{
								Element expEle=(Element) exp.get(1);
								Node expType=expEle.selectSingleNode("type");
								if(expType!=null)
								{
									System.out.println("got FunctionExpression");
									Element expTypeEle=(Element) expType;
									if(expTypeEle.getText().contentEquals("AssignmentExpression"))
									{
										Node expLeft=expEle.selectSingleNode("left");
										Element expLeftEle=(Element) expLeft;
										Node expName=expLeftEle.selectSingleNode("name");
										if(expName!=null)
										{
											Element expNameEle=(Element) expName;
											className=expNameEle.getText();
											System.out.println("Class name  : "+className);
										}
										
									}
									
										*/
									//get exports keyword
									List<Node> callexp=propValueEle.selectNodes(".//expression");
									for(int callexpindex=0;callexpindex<callexp.size();callexpindex++)
									{
										Element callexpEle=(Element) callexp.get(callexpindex);
										Node callexpType=callexpEle.selectSingleNode("type");
										if(callexpType!=null)
										{
											
											Element callexpTypeEle=(Element) callexpType;
											if(callexpTypeEle.getText().contentEquals("CallExpression"))
											{
												//System.out.println("got CallExpression");
												Node callee=callexpEle.selectSingleNode("callee");
												if(callee!=null)
												{
													Element calleeEle=(Element) callee;
													Node calleeName=calleeEle.selectSingleNode("name");
													
													if(calleeName!=null)
													{
														System.out.println("Node value:"+calleeName.getStringValue());
														Element calleeNameEle=(Element) calleeName;
														
														System.out.println("got callee");
														if(calleeNameEle.getText().contentEquals("exports_1"))
														{
															System.out.println("inside callee");
															List<Node> args=callexpEle.selectNodes("arguments");
															Element argsEle=(Element) args.get(0);
															Node argsValue=argsEle.selectSingleNode("value");
															if(argsValue!=null)
															{
																Element argsValueEle=(Element) argsValue;
																exportClassName=argsValueEle.getText();
																System.out.println("class exported : "+exportClassName);
																if(exportClassName!=null)
																{
																	unitType=getUnitType(propValueEle,exportClassName);
																	classBodyEle=propValueEle;
																	//System.out.println("class body set to :"+propValueEle.getText());
																	
																	//System.out.println("The Element Value is :"+classBodyEle.getName());
																}
																// check the type of unit
																/*if(className!=null && className.contentEquals(exportClassName))
																{
																	unitType=getUnitType(propValueEle,className);
																	System.out
																			.println("Got :"+unitType+" Name : "+className);
																}*/
															}
															
															
															
														}
													}
													
												}
												
											}
												
											
											
										}
									}//for
									
								//}
								
							//}

							
							
							
						}
						
					}//	
				}
				
				
			}
			
		
		}
		
		
		System.out.println("comp list :"+component+"service list : "+service);
		return exportClassName+"__"+unitType;
		
	}//get all controller details

	@SuppressWarnings("unchecked")
	private String getUnitType(Element propValueEle, String className) 
	{
		String unitType = null;
		List<Node> exp=propValueEle.selectNodes(".//expression");
		for(int expindex=0;expindex<exp.size();expindex++)
		{
			Element expEle=(Element) exp.get(expindex);
			Node expType=expEle.selectSingleNode("type");
			if(expType!=null)
			{
				
				Element expTypeEle=(Element) expType;
				if(expTypeEle.getText().contentEquals("AssignmentExpression"))
				{
					//System.out.println("got CallExpression");
					Node left=expEle.selectSingleNode("left");
					if(left!=null)
					{
						Element leftEle=(Element) left;
						Node leftName=leftEle.selectSingleNode("name");
						if(leftName!=null)
						{
							Element leftNameEle=(Element) leftName;
							
							//System.out.println("got callee");
							if(leftNameEle.getText().contentEquals(className))
							{
								Node right=expEle.selectSingleNode("right");
								Element rightEle=(Element) right;
								Node rightType=rightEle.selectSingleNode("type");
								Element rightTypeEle=(Element) rightType;
								if(rightTypeEle.getText().contentEquals("CallExpression"))
								{
									Node rightCallee=rightEle.selectSingleNode("callee");
									Element rightCalleeEle=(Element) rightCallee;
									Node CalleeName=rightCalleeEle.selectSingleNode("name");
									if(CalleeName!=null)
									{
										Element CalleeNameEle=(Element) CalleeName;
										if(CalleeNameEle.getText().contentEquals("__decorate"))
										{
											//System.out.println("entered class body");
											List<Node> args=rightEle.selectNodes("arguments");
											Element argsEle=(Element) args.get(0);
											
											
											List<Node> callee=argsEle.selectNodes(".//callee");
											//System.out.println("CALLLEEEEE size"+callee.size());
											for(int calleeindex=0;calleeindex<callee.size();calleeindex++)
											{
												Element calleeEle=(Element) callee.get(calleeindex);
												Node calleeType=calleeEle.selectSingleNode("type");
												Element calleeTypeEle=(Element) calleeType;
												//System.out.println("check555555555555555555555");
												if(calleeTypeEle.getText().contentEquals("MemberExpression"))
												{
													Node obj=calleeEle.selectSingleNode("object");
													Element objEle=(Element) obj;
													Node objName=objEle.selectSingleNode("name");
													Element objNameEle=(Element) objName;
													
													Node prop=calleeEle.selectSingleNode("property");
													Element propEle=(Element) prop;
													Node propName=propEle.selectSingleNode("name");
													Element propNameEle=(Element) propName;
													
													if(objNameEle.getText().contentEquals("core_1"))
													{
														if(propNameEle.getText().contentEquals("Injectable"))
														{
															System.out.println("It's an injectable");
															unitType="injectable";
															service.add(className);
														}
														else if(propNameEle.getText().contentEquals("Component"))
														{
															System.out.println("It's a Component");
															unitType="component";
															component.add(className);
														}
													}
												}
												
												
											}
											
										}
									}
									
								}
								
								
								
							} // condiion
						}
						
					}
					
				}
					
				
				
			}
		}//for
		return unitType;
	}
	
	public void methodExtract(String className, Element element) throws IOException, InterruptedException
	 {
		
		
		 //get all assignment expre
		 //check 1  :  left -> obj.pop should be classname.prototype 
		 //check 2 : right -> type :  function Expre
		 //if both conditions satisfy -> left obj -> parent.property gives you method name
		 //pull the method body
		 //store function name and function body
		
		if(element!=null)
		{
			System.out.println("extracting details");
			List<Node> exp=element.selectNodes(".//expression");
			for(int expindex=0;expindex<exp.size();expindex++)
			{
				Element expEle=(Element) exp.get(expindex);
				Node expType=expEle.selectSingleNode("type");
				
				
				if(expType!=null)
				{
					
					Element expTypeEle=(Element) expType;
					if(expTypeEle.getText().contentEquals("AssignmentExpression"))
					{
						
						//get left side of the operator to check for classname.prototype
						Node left=expEle.selectSingleNode("left");
						Element leftOperandEle=(Element)left;						
							if(leftOperandEle!=null)
							{
								
								List<Node> Obj= leftOperandEle.selectNodes(".//object");
								for(int objIndex=0;objIndex<Obj.size();objIndex++)
								{
									//check for class name & prototype
									Element objectEle=(Element)Obj.get(objIndex);
									Node objName=objectEle.selectSingleNode("name");
									Element objNameEle=(Element)objName;
									if(objNameEle!=null)
									{
										System.out.println("Class name : "  +objNameEle.getText());
										
										Node parent=objectEle.getParent();
										Element parentEle=(Element)parent;
										if(parentEle!=null)
										{
											Node prop=parentEle.selectSingleNode("property");
											Element propEle=(Element)prop;
											if(propEle!=null)
											{
												Node propEleName=propEle.selectSingleNode("name");
												Element propNameEle=(Element)propEleName;
												if(propNameEle!=null)
												{
													System.out.println("property name : "  +propNameEle.getText());							
												}
												
												//check for class.prototype
												if(objNameEle.getText().equalsIgnoreCase(className) && propNameEle.getText().equalsIgnoreCase("prototype"))
												{
													Node objParent=objectEle.getParent().getParent();
													Element objParentEle=(Element)objParent;
													if(objParentEle!=null)
													{
													
														Node parentProp=objParentEle.selectSingleNode("property");
														Element parentPropEle=(Element)parentProp;
														if(parentPropEle!=null)
														{	
															Node propName=parentPropEle.selectSingleNode("name");
															Element nameEle=(Element)propName;
															System.out.println("method name : "  +nameEle.getText());
															System.out.println("Adding to methodlist");
															thisMethods.add(nameEle.getText());
															
															//get method name
															Node methodBody = null;
															Node right=expEle.selectSingleNode("right");
															
															Element rightEle=(Element)right;
															
															if(rightEle!=null){
																String type=((Element) rightEle.selectSingleNode("type")).getText();
																
																if(type.equalsIgnoreCase("FunctionExpression"))
																{
																	methodBody=rightEle.selectSingleNode("body");
																	Element methodBodyEle=(Element)methodBody;
																	
																	
																	if(methodBodyEle != null)
																	{
																		ArrayList<Element> bodyparam=new ArrayList<Element>();
																		bodyparam.add(rightEle);
																		bodyparam.add(methodBodyEle);
																		// put method name and it's body in a hashmap
																		methodMappings.put(nameEle.getText(),bodyparam);
																		
																		//getIfconditions
																		System.out.println("Elementbody:"+methodBodyEle);
																		getIfStatementDetails(methodBodyEle,nameEle.getText());
//																		details=new ArrayList<String>();
//																		details=getIfStatementDetails(methodBodyEle,nameEle.getText());
																		
																		//putting data into hashmap
																		methodDetails.put(nameEle.getText(),details);
//																		System.out.println(methodDetails.size()+"$$$$$$");
																	}
																	
																}
																
																
															}
														}
							
													}
												}
											}

										}
										
										
										
								
								
								
								
									}
							
												
							
								}
								
								
								

							}
		
				
					}//assignment expr
			
				}
			}
		}
		
		getDetails(); 
			//return component;
	 }

	@SuppressWarnings("unchecked")
	private void getDetails() throws IOException, InterruptedException
	{
		ArrayList<String> ifConditions=new ArrayList<String>();
		ArrayList<Element> result;
			
		System.out.println(methodMappings.size());
		Iterator methodIterator = methodMappings.entrySet().iterator();
		while (methodIterator.hasNext()) 
		{
			ArrayList<String> variables=new ArrayList<String>();
			ArrayList<String> params=new ArrayList<String>();
			Map.Entry mEntry = (Map.Entry) methodIterator.next();
			Object key=mEntry.getKey();
			String methodName=(String) key;
			result=methodMappings.get(key);
			System.out.println(result+"<-- result ");
			Element rightEle=result.get(0);
			Element bodyEle=result.get(1);
		
			
			//get params
			List<Node> methodParams=rightEle.selectNodes("params");
			
			for(int x=0;x<methodParams.size();x++)
			{
				Element methodParamsEle=(Element)methodParams.get(x);
				Node paramName=methodParamsEle.selectSingleNode("name");
				Element paramNameEle=(Element)paramName;
				params.add(paramNameEle.getText());
				System.out.println("Params name : " +paramNameEle.getText());
			}
			
			//get variables
			System.out.println("checking variables for "+methodName);
			List<Node> Obj= bodyEle.selectNodes(".//object");
			for(int objIndex=0;objIndex<Obj.size();objIndex++)
			{
				//check for class name & prototype
				Element objectEle=(Element)Obj.get(objIndex);
				Node objType=objectEle.selectSingleNode("type");
				Element objTypeEle=(Element)objType;
				if(objTypeEle!=null) 
				{
					if(objTypeEle.getText().contentEquals("ThisExpression"))
					{
						Node parent=objectEle.getParent();
						Element parentEle=(Element)parent;
						if(parentEle!=null)
						{
							Node prop=parentEle.selectSingleNode("property");
							Element propEle=(Element)prop;
							if(propEle!=null)
							{
								Node propName=propEle.selectSingleNode("name");
								Element propNameEle=(Element)propName;
								if(propNameEle!=null)
								{
									System.out.println("variable name : "  +propNameEle.getText());
									
									//check for multilevel this expression
									Node root=parentEle.getParent();
									Element rootEle=(Element)root;
									Node rootType=rootEle.selectSingleNode("type");
									Element rootTypeEle=(Element)rootType;
									if(rootTypeEle!=null)
									{
										if(rootTypeEle.getText().contentEquals("MemberExpression"))
										{
											System.out.println("parent name "+rootEle.getName());
											Node property=rootEle.selectSingleNode("property");
											Element propertyEle=(Element)property;
											if(propertyEle!=null)
											{
												Node propertyName=propertyEle.selectSingleNode("name");
												Element propertyNameEle=(Element)propertyName;
												if(propertyNameEle!=null)
												{
													String varName=propNameEle.getText()+"."+propertyNameEle.getText();
													if(!thisMethods.contains(propertyNameEle.getText()))
													{
														
														System.out.println("variable is : "+varName);
														if(!variables.contains(varName))
														{
															variables.add(varName);
														}
													}
													
												}
											}
										}
										else
										{
											String varName=propNameEle.getText();
											System.out.println("variable is : "+varName);
											if(!thisMethods.contains(varName))
											{
												if(!variables.contains(varName))
												{
													variables.add(varName);
												}
											}
											
										}
									}
									
										
									

								}
								
								//check for class.prototype
								
							}
						}
					}
					//System.out.println("Class name : "  +objNameEle.getText());
					
					
				}
			}
			System.out.println("variables for : "+methodName+"are  :"+variables);

			methodDetails.put(methodName,variables);
			methodDetails.get(methodName).add("MTEST_PAR");
			for(int m=0;m<params.size();m++)
			{
				methodDetails.get(methodName).add(params.get(m)); 
			}
		
			ArrayList<String> details;
			details=new ArrayList<String>();
			details=getIfStatementDetails(bodyEle,methodName);			
			methodDetails.get(methodName).add("MTEST_IFS");
			for(int m=0;m<details.size();m++)
			{
				methodDetails.get(methodName).add(details.get(m)); 
			}
						
			
		}
		
//		System.out.println("Full Method Details="+methodDetails);

		
		
		
	}
	

	public void callServiceGenerator(String serviceName, String filePath,HashMap<String, ArrayList<String>> methodDetails2) throws IOException
	{
		//get file name
		int start=filePath.lastIndexOf('\\');
		int end=filePath.lastIndexOf('.');
		String fileName=filePath.substring(start+1, end);
		
		//get path for .js file name
		getPath(fileName,jsFolder);
		
		System.out.println("JS File Path :"+jsFilePath);
		
		int firstMeanTestIndex=jsFilePath.lastIndexOf("MeanTest")+8;
		int lastMeanTestIndex=jsFilePath.length()-3;
		
		String tsFilePath=rootPath+jsFilePath.substring(firstMeanTestIndex,lastMeanTestIndex)+".ts";
		System.out.println(tsFilePath);
//		String path=filePath.substring(0,start);
		File f=new File(tsFilePath);
		serviceSpecGenerator sg=new serviceSpecGenerator(f,fileName);
		System.out.println("methods="+methodDetails2);
		sg.generator(serviceName,methodDetails2);
		sg.closeStreams();
	}

	public void callComponentGenerator(String componentName, String filePath) throws IOException
	{
		//get file name
		int start=filePath.lastIndexOf('\\');
		int end=filePath.lastIndexOf('.');
		String fileName=filePath.substring(start+1, end);
		
		//get path for .js file name
		getPath(fileName,jsFolder);
		
		System.out.println("JS File Path :"+jsFilePath);
		
		int firstMeanTestIndex=jsFilePath.lastIndexOf("MeanTest")+8;
		int lastMeanTestIndex=jsFilePath.length()-3;
		
		String tsFilePath=rootPath+jsFilePath.substring(firstMeanTestIndex,lastMeanTestIndex)+".ts";
		System.out.println(tsFilePath);
//		String path=filePath.substring(0,start);
		File f=new File(tsFilePath);
		serviceSpecGenerator sg=new serviceSpecGenerator(f,fileName);
//		sg.generator(componentName);
		sg.closeStreams();
	}
	public void getPath(String fileName,File folder) 
	{	int flag=0;
		System.out.println(folder);
		System.out.println(fileName);
		for (final File fileEntry : folder.listFiles()) 
	    {	
	        if (!(fileEntry.isDirectory())) 
	        {
	        	String jsFileName=fileEntry.getName();
	        	if(jsFileName.endsWith(".js"))
	        	{	
	        		
	        		if(jsFileName.equals(fileName+".js"))
	        		{	jsFilePath=fileEntry.getAbsolutePath();
	        		//if(!(FileName.equalsIgnoreCase("ESGeneratorTemplate.js")))
	        			System.out.println("File : "+jsFileName+" is at  : "+jsFilePath);
		        		//write to json
	        			
	        			
	        		}

		        		
	        		
	        		
	        	}
	        	
	           
	        } 
	        
	        else 
	        	getPath(fileName,fileEntry);
	    }

	}
	@SuppressWarnings("unused")
	public Element getBody() 
	{	
		
		return classBodyEle;
	}

	
	public ArrayList<String> getMethods() 
	{	
		
		return thisMethods;
	}

	public HashMap<String, ArrayList<String>> getMethodDetails() 
	{
		
		return methodDetails;
	}	
	
	@SuppressWarnings("unchecked")
	private ArrayList<String> getIfStatementDetails(Element element, String methodName) throws IOException, InterruptedException
	{
		// class that handles escodegen flow
		CodeGenerator codeGenObj=new CodeGenerator(rootPath);

		//list of all the condition strings

		//list of json for all ifs
		ArrayList<String> jsonStrings=new ArrayList<String>();
		
		//Element functionBodyEle=(Element) node;
		List<Node> body=element.selectNodes(".//body");
		for(int index=0;index<body.size();index++)
		{
			Element bodyEle=(Element) body.get(index);
			Node bodyType=bodyEle.selectSingleNode("type");
			if(bodyType!=null)
			{
				Element bodyTypeEle=(Element) bodyType;
				if(bodyTypeEle.getText().contentEquals("IfStatement"))
				{
					

					//get details and convert into input for code generator

					//get test element and traverse it's children
					Node parent=bodyTypeEle.getParent();
					Element parentEle=(Element) parent;
					Node test=parentEle.selectSingleNode("test");
					if(test!=null)
					{
						
						StringBuilder sb = new StringBuilder();
						StringWriter writer = new StringWriter();
						//FileOutputStream fos = new FileOutputStream("op.xml");
						OutputFormat format = OutputFormat.createPrettyPrint();
						XMLWriter xmlwriter  = new XMLWriter(writer, format);
						
						xmlwriter.write(test);
						//writer.flush();
						xmlwriter.flush();
						sb.append(writer.toString());
						
						//System.out.println("for method "+methodName+" sb :"+sb);
						
						writer.close();
						
						//pass this to xml to json convertor
						XmlToJson obj=new XmlToJson();
						String json=obj.convert(sb);

						//remove parent test tag from output
						String temp=json.replace("{\"test\": ","");
						String code=temp.substring(0, temp.length()-1);
						jsonStrings.add(code);
					}
					
				}
			}
			
		}

		//pass it to escodegen
		codeGenObj.createGenerator(jsonStrings,methodName);
		codeGenObj.closeStreams();
		codeGenObj.createBat(methodName);
		codeGenObj.closeStreams();
		codeGenObj.executeBatch(methodName);
		ifConditions=codeGenObj.getCondition(jsonStrings,methodName);
		System.out.println("ifConditions "+ifConditions);
		return ifConditions;

	}

	
}
	
