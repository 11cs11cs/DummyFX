/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.worldfirst.DummyFX.service;

import com.worldfirst.DummyFX.Order;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

/**
 * @link OrderService implementation.
 */

@Service
public class OrderServiceImpl implements OrderService{
    @Autowired
    private Environment env;

    @Override
    public List<Order> inital() {
        List<Order> orders = new ArrayList<>();
        double price = Double.parseDouble(env.getProperty("price"));
        orders.add(new Order(new Long(1),1,Order.Type.BID,Order.Currency.GBP,price,5000,Order.Status.LIVE));
        orders.add(new Order(new Long(2),1,Order.Type.BID,Order.Currency.GBP,price,6000,Order.Status.LIVE));
        orders.add(new Order(new Long(3),2,Order.Type.BID,Order.Currency.USD,price,7000,Order.Status.LIVE));
        orders.add(new Order(new Long(4),2,Order.Type.BID,Order.Currency.USD,price,8000,Order.Status.LIVE));
        orders.add(new Order(new Long(5),3,Order.Type.BID,Order.Currency.GBP,price,9000,Order.Status.LIVE));
        orders.add(new Order(new Long(6),4,Order.Type.ASK,Order.Currency.GBP,price,5000,Order.Status.LIVE));
        orders.add(new Order(new Long(7),4,Order.Type.ASK,Order.Currency.GBP,price,6000,Order.Status.LIVE));
        orders.add(new Order(new Long(8),5,Order.Type.ASK,Order.Currency.USD,price,7000,Order.Status.LIVE));
        orders.add(new Order(new Long(9),5,Order.Type.ASK,Order.Currency.USD,price,1000,Order.Status.LIVE));
        orders.add(new Order(new Long(10),6,Order.Type.ASK,Order.Currency.GBP,price,2000,Order.Status.LIVE));
        
        return orders;
    }
    
    @Override
    public Order register(Order order,List<Order> orders){
        Long id = orders.stream().map(o -> o.getId()).max(Long::compareTo).get();
        double price = Double.parseDouble(env.getProperty("price"));
        order.setId(id+1);
        order.setPrice(price);
        orders.add(order);
        return order;
    }

    @Override
    public Order cancel(Long id,List<Order> orders) {
        Order matchingObject = orders.stream().
                                filter(p -> p.getId().equals(id)).findAny().orElse(null);
        matchingObject.setStatus(Order.Status.CANCELLED);
        return matchingObject;
    }

    @Override
    public List<Order> notMatchedOrders(List<Order> orders) {
        return liveNotMatch(new ArrayList<>(orders));
    }

    @Override
    public List<Order[]> matchedOrders(List<Order> orders) {
        return getMatch(new ArrayList<>(orders),new ArrayList<>());
    }
    /*
    * If find matched pair, add they into the pair list, then recursive the process until no more pairing orders can be found.
    * return the pair list.
    */
    private List<Order[]> getMatch(List<Order> orders,List<Order[]> pairs){
        if(orders.size()>1){
            for(int i=0;i<orders.size();i++){
                for(int j=0;j<orders.size();j++){
                    Order a = orders.get(i);
                    Order b = orders.get(j);
                    if(condition(a,b)){
                        Order[] pair = {a,b};
                        pairs.add(pair);
                        orders.remove(a);
                        orders.remove(b);
                        getMatch(orders,pairs);
                    }
                }
            }
        }
        return pairs;
    }
    /*
    * If find matched pair, remove them from the orginal list, then recursive the process until no more pairing orders can be found.
    * return the orginal list.
    */
    private List<Order> liveNotMatch(List<Order> orders){
        if(orders.size()>1){
            for(int i=0;i<orders.size();i++){
                for(int j=0;j<orders.size();j++){
                    Order a = orders.get(i);
                    Order b = orders.get(j);
                    if(condition(a,b)){
                        orders.remove(a);
                        orders.remove(b);
                        liveNotMatch(orders);
                    }else{
                        if(!a.getStatus().equals(Order.Status.LIVE))
                            orders.remove(a);
                    }
                }
            }
        }
        return orders;
    }
    /*
    * Matched orders condition:
    * 1. Order A, Order B are both LIVE.
    * 2. User Id not same (not the same user)
    * 3. Order Type not same (if user1 BIT, then user2 ASK, and vice versa)
    * 4. Currency is same
    * 5. Amount is same
    */
    private boolean condition(Order a,Order b){
        return a.getUserId() != b.getUserId() 
                && !a.getOrderType().equals(b.getOrderType())
                && a.getCurrency().equals(b.getCurrency())
                && a.getAmount() == b.getAmount()
                && a.getStatus().equals(Order.Status.LIVE)
                && b.getStatus().equals(Order.Status.LIVE);
    }
}
