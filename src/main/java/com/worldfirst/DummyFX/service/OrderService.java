/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.worldfirst.DummyFX.service;

import com.worldfirst.DummyFX.Order;
import java.util.List;

/*
*  Operations in FX platform
*/
public interface OrderService {
    /*
    * inital the data
    * @return List<Order>
    */
    public List<Order> inital();
    /*
    * Register an order
    * @param Order
    * @return Order
    */
    public Order register(Order order,List<Order> orders);
    /*
    * Cancel an order
    * @param List<Order>
    * @return Order
    */
    public Order cancel(Long id,List<Order> orders);
    /*
    * Return summary regarding live not matched orders.
    * @param List<Order>
    * @return List<Order>
    */
    public List<Order> notMatchedOrders(List<Order> orders);
    /*
    * Return a summary of matched trades.
    * @param List<Order>
    * @return List<Order[]>
    */
    public List<Order[]> matchedOrders(List<Order> orders);
}
