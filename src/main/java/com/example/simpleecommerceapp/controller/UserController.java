package com.example.simpleecommerceapp.controller;

import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.simpleecommerceapp.entity.User;
import com.example.simpleecommerceapp.repository.UserRepo;
import com.example.simpleecommerceapp.service.EmailService;
import com.example.simpleecommerceapp.service.TempUserService;
import com.example.simpleecommerceapp.service.UserService;

@Controller
public class UserController {
	
	@Autowired
	private TempUserService tempUserService;

	@Autowired
	private EmailService emailService;
	
	@Autowired
	private UserService userservice;
	
	@Autowired
	private UserRepo userrepo;
	
	@PostMapping("/add/user")
	public String addUser(@ModelAttribute User user, Model model) {
		if (userservice.emailExists(user.getEmail())) {
	        model.addAttribute("error", "Email already registered");
	        return "register";
	    }
	    String otp = String.valueOf(new Random().nextInt(900000) + 100000);

	    emailService.sendOtpEmail(user.getEmail(), otp);

	    tempUserService.saveTempUser(user.getEmail(), user);
	    tempUserService.saveOtp(user.getEmail(), otp);

	    model.addAttribute("email", user.getEmail());
	    return "verify-otp"; 
	}
	@PostMapping("/verify-otp")
	public String verifyOtp(@RequestParam String email, @RequestParam String otp, Model model) {
	    if (tempUserService.verifyOtp(email, otp)) {
	        User user = tempUserService.getTempUser(email);
	        userservice.CreateUser(user);
	        tempUserService.clear(email);

	        model.addAttribute("message", "Registration successful!");
	        emailService.SuccesfulregisteredEmail(email);
	        return "login"; 
	    } else {
	        model.addAttribute("email", email);
	        model.addAttribute("error", "Invalid OTP. Try again.");
	        return "verify-otp";
	    }
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
