package com.ncp.moeego.image.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ncp.moeego.image.bean.ImageDTO;
import com.ncp.moeego.image.service.ImageService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class ImageController {

	
	private final ImageService imageService;
	
	
	@GetMapping("/image/list")
	public ResponseEntity<List<ImageDTO>> getAllImages() {
        // 서비스에서 이미지 목록 가져오기
        List<ImageDTO> images = imageService.getAllImages();

        // 이미지 목록을 바로 응답으로 반환
        return ResponseEntity.ok(images);
    }
	
	@PutMapping("/image/profileUpload")
	public ResponseEntity<String> uploadProfileImage(@RequestPart("image") MultipartFile image, @RequestParam("memberNo") Long memberNo) {
	    try {
	        // 업로드 파일이 비어 있는지 확인
	        if (image.isEmpty()) {
	            throw new IllegalArgumentException("업로드된 파일이 비어 있습니다.");
	        }

	        boolean result = imageService.profileUpload(image, memberNo);

	        if (result) {
	            return ResponseEntity.ok("게시글이 성공적으로 작성되었습니다.");
	        } else {
	            return ResponseEntity.badRequest().body("게시글 작성 중 오류가 발생했습니다.");
	        }
	        // 모든 예외 GlobalExceptionHandler로
	    } catch (Exception e) {
	        throw e;
	    }
	}
	
}
