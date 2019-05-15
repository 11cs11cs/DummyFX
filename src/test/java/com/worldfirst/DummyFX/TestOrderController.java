/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.worldfirst.DummyFX;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(OrderController.class)
@ComponentScan({"com.worldfirst.DummyFX.service"})
public class TestOrderController {
    @Autowired
    private MockMvc mvc;
    
    @Test
    public void getOrdersAPI() throws Exception{
        mvc.perform(MockMvcRequestBuilders.get("/orders"))
                .andExpect(status().isOk());
    }
    
    @Test
    public void cancelOrderAPI() throws Exception{
        mvc.perform(MockMvcRequestBuilders.patch("/orders/{id}",new Long(1)))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(Order.Status.CANCELLED.toString()));
    }
    
    @Test
    public void registerOrderAPI() throws Exception{
        mvc.perform( MockMvcRequestBuilders.post("/orders/register")
            .content("{\"userId\":2, \"orderType\":\"BID\",\"currency\":\"GBP\",\"amount\":4000,\"status\":\"LIVE\"}")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists());
    }
    
    @Test
    public void matchedOrdersAPI() throws Exception{
        mvc.perform(MockMvcRequestBuilders.get("/orders/matched"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0][0].status").value(Order.Status.LIVE.toString()));
    }
    
    @Test
    public void liveNotMatchedOrdersAPI() throws Exception{
        mvc.perform(MockMvcRequestBuilders.get("/orders/notmatched"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].status").value(Order.Status.LIVE.toString()));
    }
    
}
