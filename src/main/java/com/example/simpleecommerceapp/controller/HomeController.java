package com.example.simpleecommerceapp.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.simpleecommerceapp.service.ProductService;

import jakarta.servlet.http.HttpServletResponse;

@Controller
public class HomeController {
	@Autowired
	private ProductService productservice;
	
	@GetMapping({"/", "/home"})
	public String home(HttpServletResponse response) throws IOException {
//		response.sendRedirect("http://localhost:3000/login");
		return "Index";
	}
	@GetMapping("/products")
	public String productspage(Model model) {
		model.addAttribute("productlist", productservice.GetAllProduct());
		return "products";
		}
	@GetMapping("/contactUs")
	public String contactpage() {
		return "contactus";
		}
	@GetMapping("/aboutus")
	public String aboutuspage() {
		return "aboutuspage";
		}
	@GetMapping("/login")
	public String loginpage(HttpServletResponse response) throws IOException {
//		response.sendRedirect("http://localhost:3000/login");
		return "login";
		
		}
	@GetMapping("/admin/log")
	public String adminPage() {
		return "adminLogin";
	}
	@GetMapping("/admin/register")
	public String adminRegister() {
		return "adminRegister";
	}
	@GetMapping("/register")
	public String register() {
		return "register";
	}
}
