package com.ncp.moeego.image.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.ncp.moeego.image.bean.Image;
import com.ncp.moeego.image.bean.ImageDTO;
import com.ncp.moeego.image.repository.ImageRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService{
	
	private final ImageRepository imageRepository;
	
	
	@Override
	public List<ImageDTO> getAllImages() {
	    
	    List<Image> images = imageRepository.findAll();
	    
	    // 엔티티를 DTO로 변환
	    return images.stream()
	        .map(image -> new ImageDTO(
	            image.getImageNo(),
	            image.getReviewNo() != null ? image.getReviewNo().getReviewNo() : null,
	            image.getProArticleNo() != null ? image.getProArticleNo().getProArticleNo() : null,
	            image.getArticleNo() != null ? image.getArticleNo().getArticleNo() : null,
	            image.getMemberNo().getMemberNo(),
	            image.getImageName(),
	            image.getImageUuidName()
	        ))
	        .collect(Collectors.toList());
	}

}