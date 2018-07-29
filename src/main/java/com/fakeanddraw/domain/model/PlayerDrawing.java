package com.fakeanddraw.domain.model;

import lombok.NoArgsConstructor;

/*
 * Links a player with the drawing he makes based on a given title
 */
@NoArgsConstructor
public class PlayerDrawing extends Title{
  
  public PlayerDrawing(Drawing drawing, Player player, String title){
    this.description = title;
    this.drawing = drawing;
    this.player = player;
  } 
}