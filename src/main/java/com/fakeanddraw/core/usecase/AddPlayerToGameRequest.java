package com.fakeanddraw.core.usecase;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AddPlayerToGameRequest {

  String gameCode;
  String playerSessionId;
  String userName;
}
