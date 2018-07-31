package com.fakeanddraw.core.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Drawing {

  Integer drawingId;
  Match match;
  String image;
  
  public Drawing (Match match){
    super();
    this.match = match;
  }
}