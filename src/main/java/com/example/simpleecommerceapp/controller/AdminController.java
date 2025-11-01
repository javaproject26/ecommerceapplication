package com.example.simpleecommerceapp.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.simpleecommerceapp.entity.Admin;
import com.example.simpleecommerceapp.entity.Order;
import com.example.simpleecommerceapp.entity.Product;
import com.example.simpleecommerceapp.entity.User;
import com.example.simpleecommerceapp.repository.ProductRepo;
import com.example.simpleecommerceapp.repository.UserRepo;
import com.example.simpleecommerceapp.service.AdminService;
import com.example.simpleecommerceapp.service.OrderService;
import com.example.simpleecommerceapp.service.ProductService;
import com.example.simpleecommerceapp.service.UserService;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Controller
public class AdminController {
	
	@Autowired
	private AdminService adminService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private OrderService orderService;
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private ProductRepo productrepo;
	
	@Autowired
	private UserRepo userrepo;
	
	@PostMapping("/admin/verify/credentials")
	public String verifyCredentials(@ModelAttribute("admin") Admin admin, Model model) {
		if (adminService.verifyCredentials(admin.getEmail(), admin.getPassword())) {
			model.addAttribute("admin", new Admin());
			model.addAttribute("user", new User());
			model.addAttribute("product", new Product());
			return "adminHome";
		}
	
		model.addAttribute("error", "Invalid email or password");
		return "adminLogin";
	}
	
	@GetMapping("/orders")
	public String adminHomePage(Model model) {
		model.addAttribute("orderList", orderService.GetAllOrder());
		return "orders";
	}

	
	@PostMapping("/add/admin")
	public String createAdmin(Admin admin) {
		adminService.createUser(admin);
		
		return "adminLogin";
	}
	
	@GetMapping("/update/admin/{id}")
	public String update(@PathVariable("id") Long id, Model model)
	{
		model.addAttribute("admin", adminService.getAdminById(id));
		return "redirect:/all/admin";
	}
	
	@PostMapping("/update/admin")
	public String updateAdmin(Admin admin) {
		adminService.updateAdmin(admin);
		
		return "redirect:/all/admin";
	}
	
	@GetMapping("/delete/admin/{id}")
	public String deleteAdmin(@PathVariable Long id) {
		adminService.deleteAdmin(id);
		return "redirect:/all/admin";
	}
	
	@GetMapping("/user/home")
	public String userHome(@ModelAttribute("userId") Long userId, 
			@ModelAttribute("error") String error, @ModelAttribute("messageSuccess") String messageSuccess, 
			Model model) {
		User user = userService.getUser(userId);
		model.addAttribute("ordersList", orderService.findordersbyuser(user));
		if (!error.isEmpty()) {
			model.addAttribute("error", error);
		}
		if (!messageSuccess.isEmpty()) {
			model.addAttribute("messageSuccess", messageSuccess);
		}

		return "BuyProductPage";
	}
	@GetMapping("/userhome")
	public String userhome(Model model){
		List<Product> productlist=productService.GetAllProduct();
		if (productlist.isEmpty()) {
	        System.out.println("No products found!");
	    } else {
	        for (Product product : productlist) {
	            System.out.println("Product: " + product.getName() + " - Price: " + product.getPrice());
	        }
	    }
		model.addAttribute("products", productlist);
		return "userhome";
	}
	@PostMapping("/user/login")
	public String userLogin(@ModelAttribute User user, HttpSession session, Model model) {
	    System.out.println(user.getEmail());

	    if (userService.verifycredentials(user.getEmail(), user.getPassword())) {
	        user = userService.findUserByEmail(user.getEmail());
	        System.out.println("User ID: " + user.getId());
	        session.setAttribute("userId", user.getId());
	        return "redirect:/userhome";
	    }

	    model.addAttribute("error", "Invalid email or password");
	    return "login";
	}

	@GetMapping("/user/login")
	public String Login() {
		return"userhome";
	}
	
	@PostMapping("/product/search")
	public String productSearch(String name, Long userId, Model model,HttpSession session,User user) {
		Product product = productService.findproductbyname(name);
		System.out.println(name);
		userId = (Long) session.getAttribute("userId");
		 user = userService.getUser(userId);
		 System.out.println(userId);
		if (product != null) {
			model.addAttribute("product", product);
			System.out.println(product.getName());
		} else {
			model.addAttribute("messageError", "Sorry, product was not found...");
		}
		
		return "productsearch";
	}
	
	@PostMapping("/place/order")
	public String processOrder(
	        @RequestParam Long productId,
	        @RequestParam int quantity,
	        @RequestParam String paymentMethod,
	        @RequestParam String address,
	        HttpSession session,
	        RedirectAttributes redirectAttributes,
	        Model model) {

	    Long userId = (Long) session.getAttribute("userId");
	    if (userId == null) {
	        return "redirect:/login";
	    }
	    Product product = productrepo.findById(productId)
	            .orElseThrow(() -> new RuntimeException("Product not found"));
	    User user = userService.getUser(userId);

	    Order order = new Order();
	    order.setProduct(product);
	    order.setUser(user);
	    order.setQuantity(quantity);
	    order.setPrice(product.getPrice());
	    order.setAmount(product.getPrice() * quantity);
	    order.setDate(new Date());
	    order.setAddress(address);

	    session.setAttribute("pendingOrder", order);

	    if (paymentMethod.equals("ONLINE")) {
	        return "redirect:/payment";
	    } else {
	        orderService.CreateOrder(order);
	        session.removeAttribute("pendingOrder");
	        redirectAttributes.addFlashAttribute("successMessage", "✅ Order placed successfully with Cash on Delivery!");
	        return "redirect:/user/cart";
	    }
	}
	@GetMapping("/all/admin")
	public String alladmin(Model model){
		List<Admin> admin=adminService.getAllAdmin();
		model.addAttribute("admins", admin);
		return "admins";
	}
}