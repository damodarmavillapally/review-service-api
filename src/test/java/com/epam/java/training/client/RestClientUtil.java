package com.epam.java.training.client;

import java.net.URI;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.epam.java.training.model.Review; 
public class RestClientUtil {
    
    public void getProductReviews() { 
	HttpHeaders headers = new HttpHeaders();
	headers.setContentType(MediaType.APPLICATION_JSON);
        RestTemplate restTemplate = new RestTemplate();
        //headers.set("API_KEY", "12345");
	String url = "http://localhost:8080/{productId}/reviews";
        HttpEntity<String> requestEntity = new HttpEntity<String>(headers);
        ResponseEntity<Review[]> responseEntity = restTemplate.exchange(url, HttpMethod.GET, requestEntity, Review[].class,1);
        Review[] reviews = responseEntity.getBody();
        for(Review review : reviews) {
        	System.out.println("Id:"+review.getId()+", Review Description:"+review.getDescription()
            +", Rating:"+review.getRating() +"Product Id: "+review.getProductId());   
        }
    }
    
    public void createReviewProduct() {
    	HttpHeaders headers = new HttpHeaders();
    	headers.setContentType(MediaType.APPLICATION_JSON);
    	headers.set("API_KEY", "12345");
        RestTemplate restTemplate = new RestTemplate();
		String url = "http://localhost:8080//{productId}/reviews";
		Review review = new Review();
		review.setDescription("V Useful Product");
		review.setRating(3);
        HttpEntity<Review> requestEntity = new HttpEntity<Review>(review, headers);
        URI uri = restTemplate.postForLocation(url, requestEntity,2);
        System.out.println(uri.getPath());    	
    }
    
    public void updateProductReview() {
    	
    	HttpHeaders headers = new HttpHeaders();
    	headers.setContentType(MediaType.APPLICATION_JSON);
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:8080/{productId}/reviews/{reviewId}";
        Review review = new Review();
		review.setDescription("Very Useful Product");
		review.setRating(4);
        HttpEntity<Review> requestEntity = new HttpEntity<Review>(review, headers);
        restTemplate.put(url, requestEntity,2,5);
    }
    
    public void deleteProductReview() {
    	HttpHeaders headers = new HttpHeaders();
    	headers.setContentType(MediaType.APPLICATION_JSON);
        RestTemplate restTemplate = new RestTemplate();
	String url = "http://localhost:8080//{productId}/reviews/{reviewId}";
        HttpEntity<Review> requestEntity = new HttpEntity<Review>(headers);
        restTemplate.exchange(url, HttpMethod.DELETE, requestEntity, Void.class, 1,4);        
    }
   
    public static void main(String args[]) {
    	RestClientUtil util = new RestClientUtil();
    	//util.getProductReviews();
    	util.createReviewProduct();
    }    
} 