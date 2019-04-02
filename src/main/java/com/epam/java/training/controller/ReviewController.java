package com.epam.java.training.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.epam.java.training.model.Review;
import com.epam.java.training.service.IReviewService;

@RestController
public class ReviewController {
	@Autowired
	private IReviewService reviewService;

	@RequestMapping(value = "/{productId}/reviews", method = RequestMethod.GET)
	public ResponseEntity<List<Review>> getProductReviews(@PathVariable("productId") int productId, @RequestHeader(name = "API_KEY", required = true) String headerApiKey) {
		List<Review> listReviews =  null;
		HttpHeaders headers = new HttpHeaders();
		if (headerApiKey != null && headerApiKey.equals("12345")) {
			listReviews = reviewService.getProductReviews(productId);
			
		}else {
			return new ResponseEntity<List<Review>>(headers, HttpStatus.UNAUTHORIZED);
		}
		return new ResponseEntity<List<Review>>(listReviews, HttpStatus.OK);
	}

	@RequestMapping(value = "/{productId}/reviews", method = RequestMethod.POST)
	public ResponseEntity<Void> createReviewProduct(@PathVariable("productId") int productId,
			@RequestBody Review review, @RequestHeader(name = "API_KEY", required = true) String headerApiKey,
			UriComponentsBuilder builder) {
		HttpHeaders headers = new HttpHeaders();
		if (headerApiKey != null && headerApiKey.equals("12345")) {
			System.out.println("headerApiKey:::" + headerApiKey);
			Review reviewObj = new Review();
			reviewObj.setDescription(review.getDescription());
			reviewObj.setRating(review.getRating());
			reviewObj.setProductId(productId);
			boolean flag = reviewService.createReviewProduct(reviewObj);
			if (flag == false) {
				return new ResponseEntity<Void>(HttpStatus.CONFLICT);
			}

			headers.setLocation(builder.path("/reviews/{id}").buildAndExpand(reviewObj.getId()).toUri());

		} else {
			return new ResponseEntity<Void>(headers, HttpStatus.UNAUTHORIZED);
		}
		return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/{productId}/reviews/{reviewId}", method = RequestMethod.PUT)
	public ResponseEntity<Review> updateProductReview(@PathVariable("productId") int productId,
			@PathVariable("reviewId") int reviewId, @RequestBody Review review,
			@RequestHeader(name = "API_KEY", required = true) String headerApiKey) {
		HttpHeaders headers = new HttpHeaders();
		if (headerApiKey != null && headerApiKey.equals("12345")) {
			review.setId(reviewId);
			review.setProductId(productId);
			reviewService.updateProductReview(review);
		} else {
			return new ResponseEntity<Review>(headers, HttpStatus.UNAUTHORIZED);
		}
		return new ResponseEntity<Review>(review, HttpStatus.OK);
	}

	@RequestMapping(value = "/{productId}/reviews/{reviewId}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> deleteProductReview(@PathVariable("productId") int productId,
			@PathVariable("reviewId") int reviewId,
			@RequestHeader(name = "API_KEY", required = true) String headerApiKey) {
		HttpHeaders headers = new HttpHeaders();
		if (headerApiKey != null && headerApiKey.equals("12345")) {
			Review review = new Review();
			review.setId(reviewId);
			review.setProductId(productId);
			reviewService.deleteProductReview(review);
		} else {
			return new ResponseEntity<Void>(headers, HttpStatus.UNAUTHORIZED);
		}
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}

}
