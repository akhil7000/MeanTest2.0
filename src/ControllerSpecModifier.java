


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class ControllerSpecModifier 
{
	File file;
	BufferedWriter output;
	FileWriter fw;
	FileReader fr;
	BufferedReader br1;
	BufferedReader br2;
	BufferedReader br3;
	String specName;
	String s[];
	public ControllerSpecModifier(File file, String fileName) {
		super();
		specName=fileName.substring(0, fileName.length()-4);
		String path = file+"\\"+specName+"Spec.test.js";
		System.out.println("checking path : "+path);
		file= new File(path);
		file.getParentFile().mkdirs();
		try {
			file.createNewFile();
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

	
	public void remover(File file,ArrayList<Integer> flagsList,int methodFlag, int serviceFlag) throws IOException 
	{
		// TODO Auto-generated method stub
		String line;
		String path=file+"\\"+specName+"Spec.js";
		System.out.println("controller spec read from : "+path);
		fr=new FileReader(path);
		br1=new BufferedReader(fr);
		int lines = 0;
		
		//counting total no of lines in the intermediate spec generated
		while (br1.readLine() != null) 
		{
			
		    lines++;
		}
		
		br1.close();
		
		//reading the template line by line and storing it in an array line by line
		fr=new FileReader(path);
		br3=new BufferedReader(fr);
		s=new String[lines];
		for(int i=0;i<lines;i++)
		{
			s[i]=br3.readLine();
			
		}

		//finding the start and end of the describe block for the particular module
		int moduleStart = 0;
		int moduleEnd = 0;
		
		for(int z=0;z<s.length;z++)
		{
			
			if(s[z].contentEquals("//for module "))
			{
				System.out.println("module comment found");
				System.out.println(z);
				moduleStart=z;
				
			}
			else if(s[z].contentEquals("//end for module "))
			{
				System.out.println("module comment found");
				System.out.println(z);
				moduleEnd=z;
			}
			
			
		}
		
		br3.close();
		
		// removing the unwanted lines from the intermediate spec file
		fr=new FileReader(path);
		br2=new BufferedReader(fr);
		int[] returnedIndices=new int[60];
		
		for(int i=moduleStart;i<=moduleEnd;i++)
		{
			
			line=br2.readLine();
			//finding the indices of the commented lines for replacement and removal
			returnedIndices=findIndices(moduleStart,moduleEnd);
			
			
			if( i>=returnedIndices[6] && i<=returnedIndices[7]+1 || i>returnedIndices[12] && i<=returnedIndices[26]+1)
			{
				
				
			//aftereach,normal and all scenarios
			if(i>=returnedIndices[14] && i<=returnedIndices[15])
			{
				
				//timeout
				if(flagsList.get(0)==1)
				{
					
					output.write(line); 
	                output.write("\r\n");
				}	
			}
			else if(i>=returnedIndices[16] && i<=returnedIndices[17])
			{
				//location
				if(flagsList.get(1)==1)
				{
					output.write(line); 
	                output.write("\r\n");
				}
			}

			
			else if(i>=returnedIndices[12] && i<=returnedIndices[13])
			{
				//watch
				
				if(flagsList.get(2)==1)
				{
					output.write(line); 
	                output.write("\r\n");
				}
			}
			else if(i>=returnedIndices[18] && i<=returnedIndices[19])
			{
				//boradcast
			
				if(flagsList.get(3)==1)
				{
					output.write(line); 
	                output.write("\r\n");
				}
			}
			else if(i>=returnedIndices[20] && i<=returnedIndices[26]+1)
			{
				//emit
				//check once for +1
				
				if(flagsList.get(4)==1)
				{
					output.write(line); 
	                output.write("\r\n");
				}
			}
			else if(i>=returnedIndices[6] && i<=returnedIndices[7]+1)
			{
				//aftereach
				
				if(flagsList.get(7)==1)
				{
					System.out.println("aftereach written");
					output.write(line); 
	                output.write("\r\n");
				}
			}
			
			}//outer if
			else if(i>=returnedIndices[45] && i<=returnedIndices[46]+1)
			{
				
				//http wo promisde
						if(flagsList.get(7)==1 && flagsList.get(5)==2)
						{
							
							output.write(line); 
			                output.write("\r\n");
						}
			}
			else if(i>=returnedIndices[47] && i<=returnedIndices[48]+1)
			{
				
				//http wid promise
				if(flagsList.get(7)==1 && flagsList.get(5)==1)
				{
					
					output.write(line); 
	                output.write("\r\n");
				}
			}
			else if(i>=returnedIndices[1] && i<=returnedIndices[25]+1)
			{
				//if it uses $http
				
					if(flagsList.get(7)==1)
					{
						System.out.println("http initialized");
						output.write(line); 
		                output.write("\r\n");
					}
				
				
			}
			else if(i>=returnedIndices[2] && i<=returnedIndices[21]+1)
			{
				//mocking json data
				if(flagsList.get(7)==1 && flagsList.get(8)==1)
				{
					
					output.write(line); 
	                output.write("\r\n");
				}
			}
			else if(i>=returnedIndices[22] && i<=returnedIndices[23]+1)
			{
				//hardcoded data
				if(flagsList.get(7)==1 && flagsList.get(6)==1)
				{
					
					output.write(line); 
	                output.write("\r\n");
				}
			}
			else if(i>=returnedIndices[27] && i<=returnedIndices[28]+1)
			{
				//deps list declaration
				if(i==returnedIndices[28])
				{
					if(flagsList.get(0)!=0 || flagsList.get(1)!=0)
					{
						output.write(line); 
						output.write("\r\n");
					}
					
				}
			}//done
			else if(i>=returnedIndices[29] && i<=returnedIndices[30]+1)
			{
				if(i==returnedIndices[30])
				{
					//injecting $timeout
					if(flagsList.get(0)==1 )
					{
						output.write(line); 
		                output.write("\r\n");
					}
				}
				
			}//done
			else if(i>=returnedIndices[31] && i<=returnedIndices[32]+1)
			{
				if(i==returnedIndices[32])
				{
					//injecting $location
					if(flagsList.get(1)==1)
					{
						output.write(line); 
		                output.write("\r\n");
					}
				}
				
			}//done
			else if(i>=returnedIndices[34] && i<=returnedIndices[37]+1)
			{
				if(i==returnedIndices[37])
				{
					//httpbackend done
					if(flagsList.get(7)==1)
					{
						output.write(line); 
		                output.write("\r\n");
					}
				}
				
			}
			else if(i>=returnedIndices[35] && i<=returnedIndices[36]+1)
			{
				//njecting servicelist
				if(flagsList.get(9)==1)
				{
					output.write(line); 
	                output.write("\r\n");
				}
					
				
			}
			else if(i>=returnedIndices[39] && i<=returnedIndices[40]+1)
			{
				//mock json data
				if(flagsList.get(7)==1 && flagsList.get(8)==1)
				{
					output.write(line); 
	                output.write("\r\n");
				}
			}
			else if(i>=returnedIndices[38] && i<=returnedIndices[33]+1)
			{
				//raw data backend exp
				if(flagsList.get(7)==1 && flagsList.get(6)==1)
				{
					output.write(line); 
	                output.write("\r\n");
				}
			}
			else if(i>=returnedIndices[43] && i<=returnedIndices[44]+1)
			{
				//before each for rootscope emit
				if(flagsList.get(4)==1)
				{
					output.write(line); 
	                output.write("\r\n");
				}
			}
			else if(i>=returnedIndices[41] && i<=returnedIndices[42]+1)
			{
				//service
				if(flagsList.get(9)==1)
				{

					output.write(line); 
	                output.write("\r\n");
				}
				
			}
			else if(i>=returnedIndices[49] && i<=returnedIndices[50]+1 || i>=returnedIndices[51] && i<=returnedIndices[52]+1)
			{
				//routeparam
				if(i==returnedIndices[50] || i==returnedIndices[52])
				{
					if(flagsList.get(10)==1)
					{

						output.write(line); 
		                output.write("\r\n");
					}
				}
				
				
			}//done
			else if(i>=returnedIndices[53] && i<=returnedIndices[54]+1)
			{
				//routeparam
				if(i==returnedIndices[54])
				{
					if(flagsList.get(10)==1)
					{

						output.write(line); 
		                output.write("\r\n");
					}
				}
				
				
			}//done
			else if(i>=returnedIndices[57] && i<=returnedIndices[58]+1)
			{
				//routeparam
				
				if(methodFlag==1)
				{

					output.write(line); 
		            output.write("\r\n");
				}
				
			}
			else if(i>=returnedIndices[59] && i<=returnedIndices[60]+1)
			{
				//
				
				if(methodFlag==1)
				{

					output.write(line); 
		            output.write("\r\n");
				}
				
			}
			else if(i>=returnedIndices[0] && i<=returnedIndices[24]+1)
			{
				
					if(serviceFlag==1)
					{

						output.write(line); 
			            output.write("\r\n");
					}
				
				
				
			}
			else if(i>=returnedIndices[63] && i<=returnedIndices[64]+1)
			{
				
				
					if(serviceFlag==1)
					{

						output.write(line); 
			            output.write("\r\n");
					}
				
				
				
			}
			else
			{

				output.write(line); 
                output.write("\r\n");
			}
			
			//br2.mark(moduleStart);
			
			
		}
		
		
		
		br2.close();
		//return appendArrayStart;
	}
	
	public int[] findIndices(int appendArrayStart, int appendArrayEnd)
	{
		int[] indices =new int[70];
		for(int i=appendArrayStart;i<=appendArrayEnd;i++)
		{
			
			if(s[i].contains("	//if controller depends on services"))
				indices[0]=i;
			else if(s[i].contentEquals("	//if controller uses $http"))
				indices[1]=i;
			else if(s[i].contentEquals("	//if controller stores the json data"))
				indices[2]=i;
			else if(s[i].contentEquals("	//if controller has module"))
				indices[3]=i;
			else if(s[i].contentEquals("		//inititalizing controller scope done"))
				indices[4]=i;
			else if(s[i].contentEquals("		$location=_$location_;"))
				indices[5]=i;
			else if(s[i].contentEquals("	//start aftereach"))
				indices[6]=i;
			else if(s[i].contentEquals("	//end aftereach"))
				indices[7]=i;
			else if(s[i].contentEquals("	//for normal controllers"))
				indices[8]=i;
			else if(s[i].contentEquals("	//end normal controllers"))
				indices[9]=i;
			else if(s[i].contentEquals("	//for controllers with $http"))
				indices[10]=i;
			else if(s[i].contentEquals("	//end for controllers with $http"))
				indices[11]=i;
			else if(s[i].contentEquals("	//for controllers with $watch"))
				indices[12]=i;
			else if(s[i].contentEquals("	//end controllers with $watch"))
				indices[13]=i;
			else if(s[i].contentEquals("	//for controllers with $timeout"))
				indices[14]=i;
			else if(s[i].contentEquals("	//end controllers with $timeout"))
				indices[15]=i;
			else if(s[i].contentEquals("	//for controllers with $location"))
				indices[16]=i;
			else if(s[i].contentEquals("	//end controllers with $location"))
				indices[17]=i;
			else if(s[i].contentEquals("	//for controllers with $broadcast"))
				indices[18]=i;
			else if(s[i].contentEquals("	//end controllers with $broadcast"))
				indices[19]=i;
			else if(s[i].contentEquals("	//for controllers with $emit"))
				indices[20]=i;
			else if(s[i].contentEquals("	//mocking json data done"))
				indices[21]=i;
			else if(s[i].contentEquals("	//if controller stores the hardcoded data"))
				indices[22]=i;
			else if(s[i].contentEquals("	//hardcoding data done"))
				indices[23]=i;
			else if(s[i].contentEquals("	//end if controller depends on services"))
				indices[24]=i;
			else if(s[i].contentEquals("	//end if controller uses $http"))
				indices[25]=i;
			else if(s[i].contentEquals("	//end controllers with $emit"))
				indices[26]=i;
			else if(s[i].contentEquals("	//if controller depends on $timeout,$location"))
				indices[27]=i;
			else if(s[i].contentEquals("	//end if controller depends on $timeout,$location"))
				indices[28]=i;
			else if(s[i].contentEquals("		//injecting timeout"))
				indices[29]=i;
			else if(s[i].contentEquals("		//end injecting timeout"))
				indices[30]=i;
			else if(s[i].contentEquals("		//injecting location"))
				indices[31]=i;
			else if(s[i].contentEquals("		//end injecting location"))
				indices[32]=i;
			else if(s[i].contentEquals("		//end set up backend expectations in case of raw data"))
				indices[33]=i;
			else if(s[i].contentEquals("		//injecting $httpBackend"))
				indices[34]=i;
			else if(s[i].contentEquals("		//injecting servicelist"))
				indices[35]=i;
			else if(s[i].contentEquals("		//injecting servicelist done"))
				indices[36]=i;
			else if(s[i].contentEquals("		//end injecting $httpBackend"))
				indices[37]=i;
			else if(s[i].contentEquals("		//set up backend expectations in case of raw data"))
				indices[38]=i;
			else if(s[i].contentEquals("		//mock the json data"))
				indices[39]=i;
			else if(s[i].contentEquals("		//end mock json data"))
				indices[40]=i;
			else if(s[i].contentEquals("	//list of services"))
				indices[41]=i;
			else if(s[i].contentEquals("	//end list of services"))
				indices[42]=i;
			else if(s[i].contentEquals("	//beforeeach for rootScope"))
				indices[43]=i;
			else if(s[i].contentEquals("	//end beforeeach for rootScope"))
				indices[44]=i;
			else if(s[i].contentEquals("	//for method which calls http without promise"))
				indices[45]=i;
			else if(s[i].contentEquals("	//end for method which calls http without promise"))
				indices[46]=i;
			else if(s[i].contentEquals("	//for method which calls http with promise"))
				indices[47]=i;
			else if(s[i].contentEquals("	//end for method which calls http with promise"))
				indices[48]=i;
			else if(s[i].contentEquals("	//if controller uses $routeParams"))
				indices[49]=i;
			else if(s[i].contentEquals("	//end if controller uses $routeParams"))
				indices[50]=i;
			else if(s[i].contentEquals("		//init $routeParams"))
				indices[51]=i;
			else if(s[i].contentEquals("		//end init $routeParams"))
				indices[52]=i;
			else if(s[i].contentEquals("	//for controllers with $routeParams"))
				indices[53]=i;
			else if(s[i].contentEquals("	//end for controllers with $routeParams"))
				indices[54]=i;
			else if(s[i].contentEquals("		//test for individual scope method"))
				indices[55]=i;
			else if(s[i].contentEquals("		//end test for individual scope method"))
				indices[56]=i;
			else if(s[i].contentEquals("	//testing controller methods"))
				indices[57]=i;
			else if(s[i].contentEquals("	//end testing controller methods"))
				indices[58]=i;
			else if(s[i].contentEquals("	//testing individual methods"))
				indices[59]=i;
			else if(s[i].contentEquals("	//end testing individual methods"))
				indices[60]=i;
			else if(s[i].contentEquals("	//if controller depends on services"))
				indices[61]=i;
			else if(s[i].contentEquals("	//end if controller depends on services"))
				indices[62]=i;
			else if(s[i].contentEquals("		//injecting service"))
				indices[63]=i;
			else if(s[i].contentEquals("		//end injecting service"))
				indices[64]=i;
			
		}
		
		
		return indices;
		
	}

	public void deleteIntermediateSpec(File xmlFolder) 
	{
		File f=new File(xmlFolder+"\\"+specName+"Spec.js");
		if(f.exists())
		{
			f.delete();
		}
		
	}


}
