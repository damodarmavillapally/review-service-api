package com.epam.java.training.service;

import java.util.List;

import com.epam.java.training.model.Review;

public interface IReviewService {
	
	List<Review> getProductReviews(int productId);
	boolean createReviewProduct(Review review);
	boolean updateProductReview(Review review);
	void deleteProductReview(Review review);

}
