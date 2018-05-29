
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URISyntaxException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
 
/**
 * This utility extracts files and directories of a standard zip file to
 * a destination directory.
 * @author www.codejava.net
 *
 */
public class UnzipUtility {
    /**
     * Size of the buffer to read/write data
     */
    private static final int BUFFER_SIZE = 4096;
    /**
     * Extracts a zip file specified by the zipFilePath to a directory specified by
     * destDirectory (will be created if does not exists)
     * @param zipFilePath
     * @param destDirectory
     * @throws IOException
     */
    String rootPath=null;
    public static void unzip(String zipFilePath, String destDirectory) throws IOException {
    	
        File destDir = new File(destDirectory);
        if (!destDir.exists()) {
            destDir.mkdir();
        }
        ZipInputStream zipIn = new ZipInputStream(new FileInputStream(zipFilePath));
        ZipEntry entry = zipIn.getNextEntry();
        // iterates over entries in the zip file
        while (entry != null) {
            String filePath = destDirectory + File.separator + entry.getName();
            if (!entry.isDirectory()) {
                // if the entry is a file, extracts it
                extractFile(zipIn, filePath);
            } else {
                // if the entry is a directory, make the directory
                File dir = new File(filePath);
                dir.mkdirs();
            }
            zipIn.closeEntry();
            entry = zipIn.getNextEntry();
        }
        zipIn.close();
    }
    /**
     * Extracts a zip entry (file entry)
     * @param zipIn
     * @param filePath
     * @throws IOException
     */
    private static void extractFile(ZipInputStream zipIn, String filePath) throws IOException {
    	// check for file existence
    	File file = new File(filePath);
    	if(file.exists())
    	{
    		file.delete();
    		file.createNewFile();
    	}
    	else
    		file.createNewFile();
    	
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(filePath));
        byte[] bytesIn = new byte[BUFFER_SIZE];
        int read = 0;
        while ((read = zipIn.read(bytesIn)) != -1) {
            bos.write(bytesIn, 0, read);
        }
        bos.close();
    }
    
    public static void deleteDir(File file) {
	    File[] contents = file.listFiles();
	    if (contents != null) {
	        for (File f : contents) {
	            deleteDir(f);
	        }
	    }
	    file.delete();
	}
    
    public String ExportResource(String resourceName,String rootPath) throws Exception {
        InputStream stream = null;
        OutputStream resStreamOut = null;
       // String jarFolder;
        String zipDestFileName = "";
        try {
            //stream = UnzipUtility.class.getResourceAsStream(resourceName);//note that each / is a directory down in the "jar tree" been the jar the root of the tree
        	ClassLoader classLoader = getClass().getClassLoader();
        	stream = classLoader.getResourceAsStream(resourceName);
            if(stream == null) {
                throw new Exception("Cannot get resource \"" + resourceName + "\" from Jar file.");
            }

            int readBytes;
            byte[] buffer = new byte[4096];
           // jarFolder = new File(UnzipUtility.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath()).getParentFile().getPath().replace('\\', '/');
            //jarFolder = new File(rootPath);
            zipDestFileName = rootPath + "/" + resourceName;
           
            //delete library files after extraction
			File zipDestFile = new File(zipDestFileName);
			zipDestFile.delete();
			
            resStreamOut = new FileOutputStream(zipDestFileName);
            while ((readBytes = stream.read(buffer)) > 0) {
                resStreamOut.write(buffer, 0, readBytes);
            }
        } catch (Exception ex) {
        	ex.printStackTrace();
            throw ex;
        } finally {
            stream.close();
            resStreamOut.close();
        }

        return zipDestFileName;
    }
	
    public void unZipMethod(String rootPath)
    {
    	String originalZipFileName = "node_modules"; 
		String zipFileName = originalZipFileName+".mean"; 
		String zipFilePath = "";
		String destDirectory = "";
		try {
					
			destDirectory = rootPath.concat("\\MEANTest_modules");
			System.out.println("Destination folder:"+destDirectory);
		} catch (Exception e) {
			e.printStackTrace();
		}
		//delete destination folder before unzipping
		File destFolder = new File(destDirectory+"\\"+originalZipFileName);
		if(destFolder.exists())
			deleteDir(destFolder);
		
		//UnzipUtility unzipper = new UnzipUtility();
		try {
			System.out.println("Copy File started");
			zipFilePath = ExportResource(zipFileName,rootPath);
			//unzipper.copyZipToLocal();
			System.out.println("Copy File ended");
			System.out.println("File copied to : "+zipFilePath);
			System.out.println("UnZip File started");
			//unzipper.unzip(zipFilePath, destDirectory);
			unzip(zipFilePath, destDirectory);
			System.out.println("UnZip File ended");
			
			//delete library files after extraction
			File meanFile = new File(zipFilePath);
			if(meanFile.exists())
				meanFile.delete();
		} catch (Exception ex) {
			// some errors occurred
			ex.printStackTrace();
		}
    }
    
	/*public static void main(String[] args) {
		UnzipUtility unzipper = new UnzipUtility();
		unzipper.unZipMethod("");
	}*/
	
	
	
}