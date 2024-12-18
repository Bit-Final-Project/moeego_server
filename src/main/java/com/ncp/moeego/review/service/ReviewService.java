package com.ncp.moeego.review.service;

import com.ncp.moeego.review.bean.ItemReviewResponse;
import org.springframework.data.domain.Page;

import com.ncp.moeego.review.bean.ReviewDTO;

public interface ReviewService {

	boolean writeReview(ReviewDTO reviewDTO);

	Page<ReviewDTO> getReviewListByPage(int pg, int pageSize);

	boolean deleteReview(Long reviewNo);

	Page<ReviewDTO> getMyReviews(Long member_no, int pg, int pageSize);

    Page<ItemReviewResponse> getReviewsByItemNo(Long proItemNo, int pg);
}
