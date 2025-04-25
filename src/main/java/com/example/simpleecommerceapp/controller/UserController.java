package com.example.simpleecommerceapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.simpleecommerceapp.entity.User;
import com.example.simpleecommerceapp.repository.UserRepo;
import com.example.simpleecommerceapp.service.UserService;

@Controller
public class UserController {
	
	@Autowired
	private UserService userservice;
	
	@Autowired
	private UserRepo userrepo;
	
	@PostMapping("/add/user")
	public String adduser(@ModelAttribute User user) {
		System.out.println(user.getName());
		userservice.CreateUser(user);
		return "register";
	}
	@GetMapping("/update/user/{id}")
	public String updateuser(@PathVariable Long id,Model model) {
		model.addAttribute("user", userservice.getUser(id));
		return "redirect:/users";
	}
	@PostMapping("/update/user")
	public String updateuser(User user) {
		userservice.CreateUser(user);
		return "redirect:/users";	
	}
	@DeleteMapping("delete/user/{id}")
	public String deleteuser(@PathVariable Long id) {
		userservice.deleteUser(id);
		return "redirect:/users";
	}
	@GetMapping("/users")
	public String userList(Model model) {
		List<User>userList= userrepo.findAll();
	  model.addAttribute("users", userList);
	  return "users";
	}
}
