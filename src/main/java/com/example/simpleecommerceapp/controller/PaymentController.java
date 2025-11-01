package com.example.simpleecommerceapp.controller;

import com.example.simpleecommerceapp.service.OrderService;
import com.razorpay.*;

import jakarta.servlet.http.HttpSession;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Map;

@Controller
@RequestMapping("/payment")
public class PaymentController {
	
	@Autowired
	private OrderService orderService;
	

    @Value("${razorpay.key.id}")
    private String keyId;

    @Value("${razorpay.key.secret}")
    private String keySecret;

    @PostMapping("/create-order")
    @ResponseBody
    public String createOrder(@RequestBody Map<String, Object> data) throws RazorpayException {
        RazorpayClient client = new RazorpayClient(keyId, keySecret);

        int amount = (int) Double.parseDouble(data.get("amount").toString()) * 100; // convert to paise

        JSONObject orderRequest = new JSONObject();
        orderRequest.put("amount", amount);
        orderRequest.put("currency", "INR");
        orderRequest.put("receipt", "txn_" + System.currentTimeMillis());

        Order order = client.orders.create(orderRequest);
        return order.toString();
    }

    @PostMapping("/success")
    public String paymentSuccess(@RequestParam("razorpay_payment_id") String paymentId,
                                 @RequestParam("razorpay_order_id") String orderId,
                                 @RequestParam("razorpay_signature") String signature,
                                 HttpSession session,
                                 RedirectAttributes redirectAttributes) {

        com.example.simpleecommerceapp.entity.Order order = (com.example.simpleecommerceapp.entity.Order) session.getAttribute("pendingOrder");
        if (order != null) {
            orderService.CreateOrder(order);
            session.removeAttribute("pendingOrder");
            redirectAttributes.addFlashAttribute("successMessage", "✅ Order placed successfully with Online Payment!");
        }

        return "redirect:/user/cart";
    }
}
