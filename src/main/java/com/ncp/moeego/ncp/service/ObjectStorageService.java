package com.ncp.moeego.ncp.service;

import org.springframework.web.multipart.MultipartFile;

public interface ObjectStorageService {

	String uploadFile(String bucketName, String directoryPath, MultipartFile imageFile);

	void deleteFile(String profileImage, String bucketName, String directoryPath);

}
