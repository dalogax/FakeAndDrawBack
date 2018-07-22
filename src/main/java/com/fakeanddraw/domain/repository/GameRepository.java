package com.fakeanddraw.domain.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
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
import com.fakeanddraw.domain.model.Player;

@Repository
public class GameRepository {

  @Autowired
  private JdbcTemplate jdbcTemplate;

  @Transactional(readOnly = true)
  public List<Game> findAll() {
    return jdbcTemplate.query("SELECT GAME_ID, SESSION_ID, ROOM_CODE FROM GAME",
        new GameRowMapper());
  }

  @Transactional(readOnly = true)
  public Optional<Game> findByCode(String roomCode) {
    return Optional.of(jdbcTemplate.queryForObject(
        "SELECT GAME_ID, SESSION_ID, ROOM_CODE FROM GAME " + "WHERE ROOM_CODE = ?",
        new Object[] {roomCode}, new GameRowMapper()));
  }

  public Game create(final Game game) {
    final String sql =
        "INSERT INTO GAME (SESSION_ID,ROOM_CODE,CREATED_DATE) VALUES(?,?,CURRENT TIMESTAMP)";

    KeyHolder holder = new GeneratedKeyHolder();
    jdbcTemplate.update(new PreparedStatementCreator() {
      @Override
      public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
        PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        ps.setString(1, game.getSessionId());
        ps.setString(2, game.getRoomCode());
        return ps;
      }
    }, holder);

    int newGameId = holder.getKey().intValue();
    game.setGameId(newGameId);
    return game;
  }

  public void addPlayerToGame(Game game, Player newPlayer) {
    final String sql = "INSERT INTO GAME_PLAYER (GAME_ID,PLAYER_ID) VALUES(?,?)";

    jdbcTemplate.update(new PreparedStatementCreator() {
      @Override
      public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1, game.getGameId());
        ps.setInt(2, newPlayer.getPlayerId());
        return ps;
      }
    });
  }

  class GameRowMapper implements RowMapper<Game> {
    @Override
    public Game mapRow(ResultSet rs, int rowNum) throws SQLException {
      Game game = new Game();
      game.setGameId(rs.getInt("GAME_ID"));
      game.setSessionId(rs.getString("SESSION_ID"));
      game.setRoomCode(rs.getString("ROOM_CODE"));
      return game;
    }
  }
}
