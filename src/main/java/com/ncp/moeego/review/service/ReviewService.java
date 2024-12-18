package com.ncp.moeego.review.service;

import org.springframework.data.domain.Page;

import com.ncp.moeego.review.bean.ReviewDTO;

public interface ReviewService {

	boolean writeReview(ReviewDTO reviewDTO);

	Page<ReviewDTO> getReviewListByPage(int pg, int pageSize);

}