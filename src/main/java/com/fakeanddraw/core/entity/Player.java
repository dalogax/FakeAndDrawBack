package com.fakeanddraw.core.entity;

import lombok.Data;

@Data
public class Player extends Client{
    String name;
    
    public Player(String clientId, String name){
        super(clientId);
        this.name=name;
    }
}