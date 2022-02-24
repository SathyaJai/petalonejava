package com.petsalone.util;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.Timestamp;

import javax.imageio.ImageIO;

import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.petsalone.config.FileStorageProperties;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class FileStorageService {
	
@Value("${file.upload.dir}")
private String filePath;


Path fileStorageLocation;
@Autowired
UtilClass utilClass;
	
	public String storeFile(MultipartFile file) {
		if(file.getSize()>0) {
			log.info("upload image ");
			String fileName = StringUtils.cleanPath(file.getOriginalFilename());
			String filePaths = null;
			
			try {
			    	
			    	 
			    	 fileName = utilClass.getCurrentTimeStamp().getTime()+"_"+fileName;
			    	 filePaths = filePath + fileName; 
			    	 
			    	 File files = new File(fileName);
			    	 long bytes = files.length();

			            long kilobytes = (bytes / 1024);
			            long megabytes = (kilobytes / 1024);
			            log.info(" file size ->"+ megabytes);
			    	if(megabytes < 1 ) {
			    		Files.copy(file.getInputStream(),Paths.get(filePaths),StandardCopyOption.REPLACE_EXISTING);
			    		return fileName;
			    	}else {
			    		return "Please upload less than 1 MB size photo";
			    	}
					
					
				} catch (Exception e) {
					log.error("Exception Occurred : ",e);
					
				} 
				log.info("missing dog image : file path is " + filePaths);	 
				return "Error";
			
		} else {
			
			return "Error";
		}
	}

	
/**
 *  fetch image from disk 
 * @param fileName
 * @return
 */
  public Resource loadFileAsResource(String fileName) {
	        try {
	        	
	        	Path fileStorageLocation =Paths.get(filePath)
	                    .toAbsolutePath().normalize();
	            Path filePath1 = fileStorageLocation.resolve(fileName).normalize();
	            Resource resource = new UrlResource(filePath1.toUri());
	            if(resource.exists()) {
	                return resource;
	            } else {
	            	System.out.println(" file not found");
	               // throw new MyFileNotFoundException("File not found " + fileName);
	            }
	        } catch (MalformedURLException ex) {
	          //  throw new MyFileNotFoundException("File not found " + fileName, ex);
	        }
	        return null;
	    }
  
  public String storeImage(String base64, String fileName) throws IOException {
	  byte[] base64Val=convertToImg(base64); 
	  fileName =fileName+"_"+utilClass.getCurrentTimeStamp().getTime()+".png";
      writeByteToImageFile(base64Val, filePath+fileName);
	return fileName;
	  
  }
  
  public static byte[] convertToImg(String base64) throws IOException  
  {  
       return Base64.decodeBase64(base64);  
  }  
  public static void writeByteToImageFile(byte[] imgBytes, String imgFileName) throws IOException  
  {  
       File imgFile = new File(imgFileName);  
       BufferedImage img = ImageIO.read(new ByteArrayInputStream(imgBytes));  
       ImageIO.write(img, "png", imgFile);  
  }  
	
}
