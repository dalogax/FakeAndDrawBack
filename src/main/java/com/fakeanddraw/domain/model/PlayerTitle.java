package com.fakeanddraw.domain.model;

import lombok.NoArgsConstructor;

/*
 * Links a player with the title he gives to the drawing made by another player
 */
@NoArgsConstructor
public class PlayerTitle extends Title{
  
  public PlayerTitle(Drawing drawing, Player player, String title){
    this.description = title;
    this.drawing = drawing;
    this.player = player;
  } 
}