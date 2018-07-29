package com.fakeanddraw.domain.repository;

import java.util.List;
import com.fakeanddraw.domain.model.Player;

public interface PlayerRepository {

  List<Player> findPlayersByMatch(Integer matchId);

  Player create(Player player);
}
