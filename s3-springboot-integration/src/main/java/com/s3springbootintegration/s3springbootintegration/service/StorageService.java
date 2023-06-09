package com.s3springbootintegration.s3springbootintegration.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.amazonaws.services.s3.model.CreateBucketRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.util.IOUtils;

import lombok.extern.slf4j.Slf4j;


@Service
public class StorageService {

	@Autowired
    private AmazonS3 s3Client;
	
	@Value("${application.bucket.name}")
	private String bucketName;
	
	
	 private String createBucket;
	
	// create a bucket
	public String createBucket(String bucketName) {
		this.createBucket=bucketName;
	    if (!s3Client.doesBucketExistV2(bucketName)) {
	        s3Client.createBucket(new CreateBucketRequest(bucketName));
	        return "Bucket Created successfully...";
	    }
	    else {
	    	return "Bucket name "+bucketName+" already exists or verify the credentials." ;
	    }
	}
	
	
	//uploading a file
	public String uploadFile(MultipartFile file) {
        File fileObj = convertMultiPartFileToFile(file);
        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
        s3Client.putObject(new PutObjectRequest(bucketName, fileName, fileObj));
        fileObj.delete();
        return "File uploaded : " + fileName;
    }
	
	//convert MultiPartFile to a File
	private File convertMultiPartFileToFile(MultipartFile file) {
	    File convertedFile = new File(file.getOriginalFilename());
	    try (FileOutputStream fos = new FileOutputStream(convertedFile)) {
	        fos.write(file.getBytes());
	    } catch (IOException e) {
        	e.printStackTrace();
        	System.out.println("Error converting multipartFile to file");
        }
	    return convertedFile;
	}
	
	//downloading a file
	 public byte[] downloadFile(String fileName) {
	        S3Object s3Object = s3Client.getObject(bucketName, fileName);
	        S3ObjectInputStream inputStream = s3Object.getObjectContent();
	        try {
	            byte[] content = IOUtils.toByteArray(inputStream);
	            return content;
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	        return null;
	    }
	 
	 
	 // deleting a file
	 public String deleteFile(String fileName) {
		 try {
	        s3Client.deleteObject(bucketName, fileName);
		 }
		 catch(AmazonS3Exception e) {
			System.err.println(e.getErrorMessage()); 
		 }
	        return fileName + " removed ...";
	    }
	
	 //delete a bucket
	 public void deleteBucket(String bucket) {
		 try {
		 s3Client.deleteBucket(bucket);
		 }
		 catch(AmazonS3Exception e) {
			 System.err.println(e.getErrorMessage());
		 }
	 }
	 
}
