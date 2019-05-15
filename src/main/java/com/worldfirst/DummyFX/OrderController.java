/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.worldfirst.DummyFX;

import com.worldfirst.DummyFX.service.OrderService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orders")
public class OrderController {
    private OrderService service;
    List<Order> orders;
    
    @Autowired
    public OrderController(OrderService service){
        this.service=service;
        orders = service.inital();
    }
    
    @ModelAttribute(name="orders")
    public List<Order> orders(){
        return orders;
    }
    
    /* View all orders.
    * Example:
    * curl -X GET http://localhost:8080/orders
    */
    @GetMapping
    public List<Order> viewOrders(){
        return orders;
    }
    
    /* Register an order
    * Example:
    * curl -d '{"userId":2, "orderType":"BID","currency":"GBP","amount":4000,"status":"LIVE"}' -H "Content-Type: application/json" -X POST http://localhost:8080/orders/register
    */
    @PostMapping("/register")
    public Order register(@RequestBody Order order){
        return service.register(order,orders);
    }
    
    /* Cancel an order.
    * Example:
    * curl -X PATCH http://localhost:8080/orders/1
    */
    @PatchMapping("/{id}")
    public Order cancelOrder(@PathVariable("id") Long id){
        return service.cancel(id, orders);
    }
    
    /* Return a summary of matched trades.
    * Example:
    * curl -X GET http://localhost:8080/orders/matched
    */
    
    @GetMapping("/matched")
    public List<Order[]> matchedOrders(){
        return service.matchedOrders(orders);
    }
    
    
    /* Return summary regarding live not matched orders.
    * Example:
    * curl -X GET http://localhost:8080/orders/notmatched
    */
    @GetMapping("/notmatched")
    public List<Order> notMatchedOrders(){
        return service.notMatchedOrders(orders);
    }
    
}
