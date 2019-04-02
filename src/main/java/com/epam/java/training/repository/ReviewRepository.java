package com.epam.java.training.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.epam.java.training.model.Review;

public interface ReviewRepository<P> extends CrudRepository<Review, Long>{
	
	List<Review> findByProductId(int productId);

}
