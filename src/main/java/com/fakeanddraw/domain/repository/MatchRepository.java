package com.fakeanddraw.domain.repository;

import java.util.Optional;
import com.fakeanddraw.domain.model.Match;
import com.fakeanddraw.domain.model.Player;
import javassist.NotFoundException;

public interface MatchRepository {

  Optional<Match> findLastMatchByGameCode(String gameCode);

  Optional<Match> findMatchById(Integer matchId);

  Match create(final Match match);

  void update(final Match match) throws NotFoundException;

  void addPlayerToMatch(Match match, Player newPlayer);

}
