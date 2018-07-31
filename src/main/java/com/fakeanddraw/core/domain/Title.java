package com.fakeanddraw.core.domain;

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
