package com.fakeanddraw.domain.usecase;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AddPlayerToGameRequest {

  String roomCode;
  String playerSessionId;
  String userName;
}
