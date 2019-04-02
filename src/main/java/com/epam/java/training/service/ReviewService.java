package com.epam.java.training.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.epam.java.training.model.Review;
import com.epam.java.training.repository.ReviewRepository;

@Service
public class ReviewService implements IReviewService{
	
	@Autowired
	private ReviewRepository<Review> reviewRepository;
	
	@Transactional
	public List<Review> getProductReviews(int productId){
		return reviewRepository.findByProductId(productId);
	}
	@Transactional
	public boolean createReviewProduct(Review review) {
		return reviewRepository.save(review) != null;
	}
	@Transactional
	public boolean updateProductReview(Review review) {
		return reviewRepository.save(review) != null;
	}
	@Transactional
	public void deleteProductReview(Review review) {
		reviewRepository.delete(review);
		
	}

}
