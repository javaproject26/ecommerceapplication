package com.example.simpleecommerceapp.service;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.simpleecommerceapp.entity.User;
import com.example.simpleecommerceapp.repository.UserRepo;

@Service
public class UserService {
	@Autowired
	private UserRepo userrepo;
	
	public List<User> GetAllUser(){ 
		return userrepo.findAll();
	}
	public User getUser(Long Id) {
		return userrepo.findById(Id).orElseThrow(()->new RuntimeException("User with id"+Id+"Not Found"));
	}
	public void updateUser(User user,Long Id) {
		userrepo.findById(Id).orElseThrow(()->new RuntimeException("User with id"+user.getId()+"Not Found"));
		userrepo.save(user);
	}
	public void deleteUser(Long Id) {
		userrepo.findById(Id).orElseThrow(()->new RuntimeException("User with id"+Id+"Not Found"));
		userrepo.deleteById(Id);
	}
	public void CreateUser(User user) {
		userrepo.save(user);
	}
	public User findUserByEmail(String Email) {
		return userrepo.findByEmailIgnoreCase(Email);
	}
	public boolean verifycredentials(String Email,String Password) {
		User user=userrepo.findByEmailIgnoreCase(Email);
		if(user.getPassword().equals(Password)){
			return true;
		}
		return false;
	}
}
