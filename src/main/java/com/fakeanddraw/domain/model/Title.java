package com.fakeanddraw.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Title {

  Drawing drawing;
  Player player;
  String description;
  Boolean isOriginal;

}
