/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.worldfirst.DummyFX;

import java.util.Arrays;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.junit.Assert.assertEquals;
import org.springframework.test.web.servlet.ResultActions;

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
        ResultActions action = mvc.perform(MockMvcRequestBuilders.patch("/orders/{id}",new Long(1)));
        MvcResult result = action.andExpect(status().isOk()).andReturn();
        if(result.getResponse().getContentLength() != 0){
            action.andExpect(MockMvcResultMatchers.jsonPath("$.status").value(Order.Status.CANCELLED.toString()));
        }
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
        MvcResult result = mvc.perform(MockMvcRequestBuilders.get("/orders/matched"))
                .andExpect(status().isOk())
                .andReturn();
        String content = result.getResponse().getContentAsString();
        if(result.getResponse().getContentLength() != 0){
            assertEquals(convertJSON(content,0).get("amount"), convertJSON(content,1).get("amount"));
        }
    }
    
    @Test
    public void liveNotMatchedOrdersAPI() throws Exception{
        mvc.perform(MockMvcRequestBuilders.get("/orders/notmatched"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].status").value(Order.Status.LIVE.toString()));
    }
    
    public JSONObject convertJSON(String content, int index) throws JSONException{
        String[] arr = Arrays.stream(content.substring(2, content.length() - 2).split("\\],\\[")).toArray(String[]::new);
        return new JSONArray("["+arr[0]+"]").getJSONObject(index);
    }
    
}
