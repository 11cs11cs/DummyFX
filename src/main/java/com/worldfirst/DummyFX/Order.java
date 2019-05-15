/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.worldfirst.DummyFX;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Order {
    private Long id;
    private int userId;
    private Type orderType;
    private Currency currency;
    private double price;
    private int amount;
    private Status status;
    
    public static enum Type{
        BID,ASK
    }
    
    public static enum Currency{
        GBP,USD
    }
    
    public static enum Status{
        LIVE,CANCELLED
    }
}
