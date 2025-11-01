package com.example.simpleecommerceapp.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.example.simpleecommerceapp.entity.User;

@Service
public class TempUserService {
	private Map<String, User> tempUsers = new HashMap<>();
	private Map<String, String> emailOtpMap = new HashMap<>();

	public void saveTempUser(String email, User user) {
		tempUsers.put(email, user);
	}

	public User getTempUser(String email) {
		return tempUsers.get(email);
	}

	public void saveOtp(String email, String otp) {
		emailOtpMap.put(email, otp);
	}

	public boolean verifyOtp(String email, String enteredOtp) {
		return emailOtpMap.containsKey(email) && emailOtpMap.get(email).equals(enteredOtp);
	}

	public void clear(String email) {
		tempUsers.remove(email);
		emailOtpMap.remove(email);
	}
}


