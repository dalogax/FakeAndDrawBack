package com.fakeanddraw.domain.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.fakeanddraw.domain.model.Game;
import com.fakeanddraw.domain.model.Match;
import com.fakeanddraw.domain.model.MatchStatus;
import com.fakeanddraw.domain.model.Player;

@Repository
public class MatchRepository {

  @Autowired
  private JdbcTemplate jdbcTemplate;

  @Transactional(readOnly = true)
  public Optional<Match> findLastMatchByGameCode(String gameCode) {

    return Optional.of(jdbcTemplate.queryForObject(
        "SELECT M.MATCH_ID, M.STATUS, M.CREATED_DATE, G.GAME_ID, G.SESSION_ID, G.GAME_CODE FROM `MATCH` M"
            + " INNER JOIN GAME G ON M.GAME_ID = G.GAME_ID WHERE G.GAME_CODE = ?"
            + " ORDER BY M.CREATED_DATE DESC FETCH FIRST ROW ONLY",
        new Object[] {gameCode}, new MatchRowMapper()));
  }


  public Match create(final Match match) {
    final String sql =
        "INSERT INTO `MATCH` (GAME_ID,STATUS,CREATED_DATE) VALUES(?,?,CURRENT TIMESTAMP)";

    KeyHolder holder = new GeneratedKeyHolder();
    jdbcTemplate.update(new PreparedStatementCreator() {
      @Override
      public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
        PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        ps.setInt(1, match.getGame().getGameId());
        ps.setString(2, match.getStatus().name());
        return ps;
      }
    }, holder);

    int newMatchId = holder.getKey().intValue();
    match.setMatchId(newMatchId);
    return match;
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
      Match match = new Match();
      match.setMatchId(rs.getInt("MATCH_ID"));
      Game game = new Game();
      game.setGameId(rs.getInt("GAME_ID"));
      game.setSessionId(rs.getString("SESSION_ID"));
      game.setGameCode(rs.getString("GAME_CODE"));
      match.setGame(game);
      match.setMatchId(rs.getInt("MATCH_ID"));
      match.setStatus(MatchStatus.findByName(rs.getString("STATUS")));
      return match;
    }
  }
}
