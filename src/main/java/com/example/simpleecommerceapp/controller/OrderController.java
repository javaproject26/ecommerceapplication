package com.example.simpleecommerceapp.controller;

import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.simpleecommerceapp.entity.Order;
import com.example.simpleecommerceapp.entity.Product;
import com.example.simpleecommerceapp.entity.User;
import com.example.simpleecommerceapp.repository.OrderRepo;
import com.example.simpleecommerceapp.repository.ProductRepo;
import com.example.simpleecommerceapp.service.OrderService;
import com.example.simpleecommerceapp.service.ProductService;
import com.example.simpleecommerceapp.service.UserService;

import jakarta.servlet.http.HttpSession;

@Controller
public class OrderController {
	
	@Autowired
	private OrderService orderservice;
	
	@Autowired
	private UserService userservice;
	
	@Autowired
	private OrderRepo orderrepo;
	
	@Autowired
	private ProductRepo productrepo;
	
	@GetMapping("/place/order")
	public String placeOrder(@RequestParam Long productId,Long userId, @RequestParam int quantity, Model model,HttpSession session) {
	    Product product = productrepo.findById(productId)
	            .orElseThrow(() -> new RuntimeException("Product not found"));
	     userId = (Long) session.getAttribute("userId");
	    Order order = new Order();
	    order.setProduct(product);
	    order.setQuantity(quantity);
	    order.setPrice(product.getPrice());
	    order.setAmount(product.getPrice() * quantity);
	    System.out.println(order.getAmount());
	    order.setDate(new Date());
	    User user = userservice.getUser(userId);
		order.setUser(user);
	    String base64Image = "";
	    if (product.getImage() != null) {
	        base64Image = Base64.getEncoder().encodeToString(product.getImage());
	    }
	    model.addAttribute("order", order);
	    model.addAttribute("product", product);
	    model.addAttribute("base64Image", base64Image); 
	    return "cart"; 
	}
	@GetMapping("/user/cart")
	public String usercart(Long userId,HttpSession session,Model model) {
		userId= (Long) session.getAttribute("userId");
		List<Order> orders= orderrepo.findByUserId(userId);
		Map<Long, String> productImages = new HashMap<>();
	    for (Order order : orders) {
	        byte[] imageData = ((Order) order).getProduct().getImage();
	        if (imageData != null) {
	            String base64Image = Base64.getEncoder().encodeToString(imageData);
	            productImages.put(((Order) order).getId(), base64Image);
	        }
	    }
		model.addAttribute("orders", orders);
		model.addAttribute("productImages", productImages);
		return "usercart";
	}
}
