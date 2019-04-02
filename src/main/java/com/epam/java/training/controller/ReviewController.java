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

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@Api(tags={"Review Service API"})
public class ReviewController {
	@Autowired
	private IReviewService reviewService;

	@RequestMapping(value = "/{productId}/reviews", method = RequestMethod.GET)
	@ApiOperation(value="Product Reviews", notes="Provides the Product Review Information for a given productId", response=ResponseEntity.class)
	@ApiImplicitParams({
        @ApiImplicitParam(name = "id", value = "Product Id", required=true, dataType="Integer", paramType="path"),
        @ApiImplicitParam(name = "API_KEY", value = "api key", required=true, dataType="String", paramType="header")
	})
	@ApiResponses(value={
			@ApiResponse(code=200, message="Success"),
			@ApiResponse(code=401, message="Bad Request"),
			@ApiResponse(code=402, message="Unauthorised"),
			@ApiResponse(code=403, message="Forbidden"),
			@ApiResponse(code=404, message="No Product is found"),
			@ApiResponse(code=500, message="Internal Server Error")
	})
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
	@ApiOperation(value="Creates Product Review", notes="Creates the Product Review Information for a given productId", response=ResponseEntity.class)
	@ApiImplicitParams({
        @ApiImplicitParam(name = "id", value = "Product Id", required=true, dataType="Integer", paramType="path"),
        @ApiImplicitParam(name = "review", value = "review", required=true, dataType="Review", paramType="body"),
        @ApiImplicitParam(name = "API_KEY", value = "api key", required=true, dataType="String", paramType="header")
	})
	@ApiResponses(value={
			@ApiResponse(code=200, message="Success"),
			@ApiResponse(code=401, message="Bad Request"),
			@ApiResponse(code=402, message="Unauthorised"),
			@ApiResponse(code=403, message="Forbidden"),
			@ApiResponse(code=404, message="No Product is found"),
			@ApiResponse(code=500, message="Internal Server Error")
	})
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
	@ApiOperation(value="Updates Product Review", notes="Updates the Product Review Information for a given productId and reviewId", response=ResponseEntity.class)
	@ApiImplicitParams({
        @ApiImplicitParam(name = "id", value = "Product Id", required=true, dataType="Integer", paramType="path"),
        @ApiImplicitParam(name = "review id", value = "review id", required=true, dataType="Integer", paramType="path"),
        @ApiImplicitParam(name = "API_KEY", value = "api key", required=true, dataType="String", paramType="header")
	})
	@ApiResponses(value={
			@ApiResponse(code=200, message="Success"),
			@ApiResponse(code=401, message="Bad Request"),
			@ApiResponse(code=402, message="Unauthorised"),
			@ApiResponse(code=403, message="Forbidden"),
			@ApiResponse(code=404, message="No Product is found"),
			@ApiResponse(code=500, message="Internal Server Error")
	})
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
	@ApiOperation(value="Deletes Product Review", notes="Deletes the Product Review Information for a given productId and reviewId", response=ResponseEntity.class)
	@ApiImplicitParams({
        @ApiImplicitParam(name = "id", value = "Product Id", required=true, dataType="Integer", paramType="path"),
        @ApiImplicitParam(name = "review id", value = "review id", required=true, dataType="Integer", paramType="path"),
        @ApiImplicitParam(name = "API_KEY", value = "api key", required=true, dataType="String", paramType="header")
	})
	@ApiResponses(value={
			@ApiResponse(code=200, message="Success"),
			@ApiResponse(code=401, message="Bad Request"),
			@ApiResponse(code=402, message="Unauthorised"),
			@ApiResponse(code=403, message="Forbidden"),
			@ApiResponse(code=404, message="No Product is found"),
			@ApiResponse(code=500, message="Internal Server Error")
	})
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
