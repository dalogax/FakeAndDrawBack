package com.fakeanddraw.dataproviders.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Optional;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.fakeanddraw.domain.model.Game;
import com.fakeanddraw.domain.model.GameFactory;
import com.fakeanddraw.domain.model.Match;
import com.fakeanddraw.domain.model.MatchFactory;
import com.fakeanddraw.domain.model.MatchStatus;
import com.fakeanddraw.domain.model.Player;
import javassist.NotFoundException;

@Repository
public class MatchRepository {

  @Autowired
  private JdbcTemplate jdbcTemplate;

  @Autowired
  private GameFactory gameFactory;

  @Autowired
  private MatchFactory matchFactory;

  @Transactional(readOnly = true)
  public Optional<Match> findLastMatchByGameCode(String gameCode) {
    try {
      return Optional.of(jdbcTemplate.queryForObject(
          "SELECT M.MATCH_ID, M.STATUS, M.CREATED_DATE, M.JOIN_TIMEOUT, M.DRAW_TIMEOUT, G.GAME_ID, G.SESSION_ID, G.GAME_CODE FROM `MATCH` M"
              + " INNER JOIN GAME G ON M.GAME_ID = G.GAME_ID WHERE G.GAME_CODE = ?"
              + " ORDER BY M.CREATED_DATE DESC FETCH FIRST ROW ONLY",
          new Object[] {gameCode}, new MatchRowMapper()));
    } catch (EmptyResultDataAccessException e) {
      return Optional.empty();
    }
  }

  @Transactional(readOnly = true)
  public Optional<Match> findMatchById(Integer matchId) {
    try {
      return Optional.of(jdbcTemplate.queryForObject(
          "SELECT M.MATCH_ID, M.STATUS, M.CREATED_DATE, M.JOIN_TIMEOUT, M.DRAW_TIMEOUT, G.GAME_ID, G.SESSION_ID, G.GAME_CODE FROM `MATCH` M"
              + " INNER JOIN GAME G ON M.GAME_ID = G.GAME_ID WHERE M.MATCH_ID = ?",
          new Object[] {matchId}, new MatchRowMapper()));
    } catch (EmptyResultDataAccessException e) {
      return Optional.empty();
    }
  }


  public Match create(final Match match) {
    final String sql =
        "INSERT INTO `MATCH` (GAME_ID,STATUS,CREATED_DATE,JOIN_TIMEOUT,DRAW_TIMEOUT) VALUES(?,?,?,?,?)";

    KeyHolder holder = new GeneratedKeyHolder();
    jdbcTemplate.update(new PreparedStatementCreator() {
      @Override
      public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
        PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        ps.setInt(1, match.getGame().getGameId());
        ps.setString(2, match.getStatus().name());
        ps.setTimestamp(3, new Timestamp(match.getCreatedDate().getMillis()));
        ps.setTimestamp(4, new Timestamp(match.getJoinTimeout().getMillis()));
        ps.setTimestamp(5, new Timestamp(match.getDrawTimeout().getMillis()));
        return ps;
      }
    }, holder);

    int newMatchId = holder.getKey().intValue();
    match.setMatchId(newMatchId);
    return match;
  }

  public void update(final Match match) throws NotFoundException {
    final String sql = "UPDATE `MATCH` SET STATUS = ? WHERE MATCH_ID = ?";

    int updatedRows = jdbcTemplate.update(new PreparedStatementCreator() {
      @Override
      public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setString(1, match.getStatus().name());
        ps.setInt(2, match.getMatchId());
        return ps;
      }
    });

    if (updatedRows == 0) {
      throw new NotFoundException("Match id not found");
    }
  }

  public void addPlayerToMatch(Match match, Player newPlayer) {
    final String sql = "INSERT INTO MATCH_PLAYER (MATCH_ID,PLAYER_ID) VALUES(?,?)";

    jdbcTemplate.update(new PreparedStatementCreator() {
      @Override
      public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1, match.getMatchId());
        ps.setInt(2, newPlayer.getPlayerId());
        return ps;
      }
    });
  }

  class MatchRowMapper implements RowMapper<Match> {
    @Override
    public Match mapRow(ResultSet rs, int rowNum) throws SQLException {
      Match match = matchFactory.createEmptyMatch();
      match.setMatchId(rs.getInt("MATCH_ID"));
      Game game = gameFactory.createEmptyGame();
      game.setGameId(rs.getInt("GAME_ID"));
      game.setSessionId(rs.getString("SESSION_ID"));
      game.setGameCode(rs.getString("GAME_CODE"));
      match.setGame(game);
      match.setMatchId(rs.getInt("MATCH_ID"));
      match.setStatus(MatchStatus.findByName(rs.getString("STATUS")));
      match.setCreatedDate(new DateTime(rs.getTimestamp("CREATED_DATE").getTime()));
      match.setJoinTimeout(new DateTime(rs.getTimestamp("JOIN_TIMEOUT").getTime()));
      match.setDrawTimeout(new DateTime(rs.getTimestamp("DRAW_TIMEOUT").getTime()));
      return match;
    }
  }

  @Transactional(readOnly = true)
  public Optional<Match> findLastMatchByPlayerId(Integer playerId) {
    try {
      return Optional.of(jdbcTemplate.queryForObject(
          "SELECT M.MATCH_ID, M.STATUS, M.CREATED_DATE, M.JOIN_TIMEOUT, M.DRAW_TIMEOUT, G.GAME_ID, G.SESSION_ID, G.GAME_CODE FROM `MATCH` M"
              + " INNER JOIN GAME G ON M.GAME_ID = G.GAME_ID "
              + " INNER JOIN MATCH_PLAYER P ON M.MATCH_ID = P.MATCH_ID WHERE P.PLAYER_ID = ? "
              + " ORDER BY M.CREATED_DATE DESC FETCH FIRST ROW ONLY ",
          new Object[] {playerId}, new MatchRowMapper()));
    } catch (EmptyResultDataAccessException e) {
      return Optional.empty();
    }
  }
}
