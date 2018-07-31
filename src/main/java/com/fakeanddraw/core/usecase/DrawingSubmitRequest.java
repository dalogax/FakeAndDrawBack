package com.fakeanddraw.core.usecase;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DrawingSubmitRequest {
  private String sessionId;
  private String image;
}
