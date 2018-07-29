package com.fakeanddraw.domain.usecase;

import org.springframework.stereotype.Component;

@Component
public class DrawingSubmit implements UseCase<DrawingSubmitRequest> {
  @Override
  public void execute(DrawingSubmitRequest request) {
    // TODO Get game id
    // TODO Get stage image title
    // TODO Add image to user, game and title
    // TODO Send message to master: an user has submitted an image
    // TODO Check if all images have been submitted. If yes, close this round
    // sending the proper message to master
  }
}
