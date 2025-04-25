package com.example.simpleecommerceapp.service;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.simpleecommerceapp.entity.Product;
import com.example.simpleecommerceapp.repository.ProductRepo;

@Service
public class ProductService {
	@Autowired
	private ProductRepo productrepo;
	public List<Product> GetAllProduct(){
		return productrepo.findAll();
	}
	public Product getProduct(Long Id) {
		return productrepo.findById(Id).orElseThrow(()->new RuntimeException("Product with id"+Id+"Not Found"));
	}
	public void updateProduct(Product product) {
		productrepo.findById(product.getId()).orElseThrow(()->new RuntimeException("Product with id"+product.getId()+"Not Found"));
		productrepo.save(product);
	}
	public void deleteProduct(Long Id) {
		productrepo.findById(Id).orElseThrow(()->new RuntimeException("Product with id"+Id+"Not Found"));
		productrepo.deleteById(Id);
	}
	public void CreateProduct(Product product) {
		productrepo.save(product);
	}
	public Product findproductbyname(String name) {
		return productrepo.findByName(name);
	}
}
