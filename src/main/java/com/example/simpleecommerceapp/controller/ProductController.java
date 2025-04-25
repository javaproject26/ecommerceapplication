package com.example.simpleecommerceapp.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import com.example.simpleecommerceapp.entity.Product;
import com.example.simpleecommerceapp.service.OrderService;
import com.example.simpleecommerceapp.service.ProductService;

@Controller
public class ProductController {
	@Autowired
	private ProductService productservice;
	
	@Autowired
	private OrderService orderservice;
	
	@PostMapping("/add/product")
    public String addProduct(@RequestParam("name") String name,
                             @RequestParam("price") double price,
                             @RequestParam("description") String description,
                             @RequestParam("image") MultipartFile file,
                             @RequestParam("available")int available) {
        try {
            Product product = new Product();
            product.setName(name);
            product.setPrice(price);
            product.setDescription(description);
            product.setImage(file.getBytes());
            product.setAvailable(available);
            productservice.CreateProduct(product); 
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "addproduct"; // Redirect to product list page
    }
	@GetMapping("/productsdetails")
	public String productspage(Model model) {
		List<Product> productlist=productservice.GetAllProduct();
		model.addAttribute("products", productlist);
		return "products";
		}
	@GetMapping("/product/image/{id}")
	public ResponseEntity<byte[]> getProductImage(@PathVariable Long id) {
	    Product product = productservice.getProduct(id);
	    if (product != null && product.getImage() != null) {
	        HttpHeaders headers = new HttpHeaders();
	        headers.setContentType(MediaType.IMAGE_JPEG); // Adjust for JPEG if needed
	        return new ResponseEntity<>(product.getImage(), headers, HttpStatus.OK);
	    }
	    return ResponseEntity.notFound().build();
	}
	
	@GetMapping("/update/product/{id}")
	public String updateproduct(@PathVariable Long id,Model model) {
		model.addAttribute("product", productservice.getProduct(id));
		return "redirect:/productsdetails";
	}
	@PostMapping("/update/product")
	public String updateproduct(Product product) {
		Product existingProduct = productservice.getProduct(product.getId());

	    // Preserve the image if not updated
	    if (product.getImage() == null) {
	        product.setImage(existingProduct.getImage());
	    }
		productservice.updateProduct(product);
		return "redirect:/productsdetails";
	}
	
	@DeleteMapping("/delete/product/{id}")
	public String deleteproduct(@PathVariable Long id) {
		productservice.deleteProduct(id);
		return "redirect:/productsdetails";
	}
	@GetMapping("/add/product")
	public String addProduct() {
		return "addproduct";
	}
}
