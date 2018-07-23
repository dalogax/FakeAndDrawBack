package com.fakeanddraw.domain.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import com.fakeanddraw.domain.model.Game;

@Repository
public class GameRepository {

  @Autowired
  private JdbcTemplate jdbcTemplate;

  public Game create(final Game game) {
    final String sql =
        "INSERT INTO GAME (SESSION_ID,GAME_CODE,CREATED_DATE) VALUES(?,?,CURRENT TIMESTAMP)";

    KeyHolder holder = new GeneratedKeyHolder();
    jdbcTemplate.update(new PreparedStatementCreator() {
      @Override
      public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
        PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        ps.setString(1, game.getSessionId());
        ps.setString(2, game.getGameCode());
        return ps;
      }
    }, holder);

    int newGameId = holder.getKey().intValue();
    game.setGameId(newGameId);
    return game;
  }

  class GameRowMapper implements RowMapper<Game> {
    @Override
    public Game mapRow(ResultSet rs, int rowNum) throws SQLException {
      Game game = new Game();
      game.setGameId(rs.getInt("GAME_ID"));
      game.setSessionId(rs.getString("SESSION_ID"));
      game.setGameCode(rs.getString("ROOM_CODE"));
      return game;
    }
  }
}
