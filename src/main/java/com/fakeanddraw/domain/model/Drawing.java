package com.fakeanddraw.domain.model;

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
}