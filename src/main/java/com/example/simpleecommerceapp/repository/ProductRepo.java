package com.example.simpleecommerceapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.simpleecommerceapp.entity.Product;

@Repository
public interface ProductRepo extends JpaRepository<Product,Long>{
	public Product findByName(String name);
}
