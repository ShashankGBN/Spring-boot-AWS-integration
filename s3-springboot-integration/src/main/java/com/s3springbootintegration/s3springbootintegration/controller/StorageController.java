package com.s3springbootintegration.s3springbootintegration.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.s3springbootintegration.s3springbootintegration.service.StorageService;

@RestController
@RequestMapping("/file")
public class StorageController {

	@Autowired
	private StorageService storageService;
	
	@PostMapping("/create/{bucketName}")
	public ResponseEntity<String> createBucket(@PathVariable String bucketName) {
        return new ResponseEntity<>(storageService.createBucket(bucketName), HttpStatus.CREATED);
	}
	
	@PostMapping("/upload")
	public ResponseEntity<String> uploadFile(@RequestParam(value = "file") MultipartFile multipartFile) {
        return new ResponseEntity<>(storageService.uploadFile(multipartFile), HttpStatus.OK);
	}
	
	
	@GetMapping("/download/{fileName}")
    public ResponseEntity<ByteArrayResource> downloadFile(@PathVariable String fileName) {
        byte[] data = storageService.downloadFile(fileName);
        ByteArrayResource resource = new ByteArrayResource(data);
        return ResponseEntity
                .ok()
                .contentLength(data.length)
                .header("Content-type", "application/octet-stream")
                .header("Content-disposition", "attachment; filename=\"" + fileName + "\"")
                .body(resource);
    }
	
	@DeleteMapping("/delete/{fileName}")
	public ResponseEntity<String> deleteFile(@PathVariable String fileName) {
		return new ResponseEntity<>(storageService.deleteFile(fileName), HttpStatus.OK);
	}
	
	@DeleteMapping("/deletebucket/{bucketName}")
	public void deleteBucket(@PathVariable String bucketName) {
		storageService.deleteBucket(bucketName);
	}
	
}
