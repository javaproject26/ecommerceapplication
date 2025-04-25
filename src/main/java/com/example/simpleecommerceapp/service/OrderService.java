package com.example.simpleecommerceapp.service;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.simpleecommerceapp.entity.Order;
import com.example.simpleecommerceapp.entity.User;
import com.example.simpleecommerceapp.repository.OrderRepo;

@Service
public class OrderService {
	@Autowired
	private OrderRepo orderrepo;
	public List<Order> GetAllOrder(){
		return orderrepo.findAll();
	}
	public Order getOrder(Long Id) {
		return orderrepo.findById(Id).orElseThrow(()->new RuntimeException("Order with id"+Id+"Not Found"));
	}
	public void updateOrder(Order order,Long Id) {
		orderrepo.findById(Id).orElseThrow(()->new RuntimeException("Order with id"+order.getId()+"Not Found"));
		orderrepo.save(order);
	}
	public void deleteOrder(Long Id) {
		orderrepo.findById(Id).orElseThrow(()->new RuntimeException("Order with id"+Id+"Not Found"));
		orderrepo.deleteById(Id);
	}
	public void CreateOrder(Order order) {
		orderrepo.save(order);
	}
	public List<Order> findordersbyuser(User user){
		return orderrepo.findByUser(user);
	}
	public List<Order> findordersbyuserid(Long userId){
		return orderrepo.findByUserId(userId);
	}
}
