package com.fakeanddraw.domain.repository;

import java.util.Optional;
import com.fakeanddraw.domain.model.Game;

public interface GameRepository {

  Optional<Game> findById(Integer gameId);

  Game create(Game game);
}
