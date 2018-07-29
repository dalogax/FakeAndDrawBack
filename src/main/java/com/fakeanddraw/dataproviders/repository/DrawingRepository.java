package com.fakeanddraw.dataproviders.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
import com.fakeanddraw.domain.model.Drawing;
import com.fakeanddraw.domain.model.Game;
import com.fakeanddraw.domain.model.GameFactory;
import com.fakeanddraw.domain.model.Match;
import com.fakeanddraw.domain.model.MatchFactory;
import com.fakeanddraw.domain.model.MatchStatus;
import javassist.NotFoundException;

@Repository
public class DrawingRepository {

  @Autowired
  private JdbcTemplate jdbcTemplate;

  @Autowired
  private GameFactory gameFactory;

  @Autowired
  private MatchFactory matchFactory;

  public Drawing create(final Drawing drawing) {
    final String sql = "INSERT INTO DRAWING (MATCH_ID, IMAGE) VALUES(?,?)";

    KeyHolder holder = new GeneratedKeyHolder();
    jdbcTemplate.update(new PreparedStatementCreator() {
      @Override
      public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
        PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        ps.setInt(1, drawing.getMatch().getMatchId());
        ps.setString(2, drawing.getImage());
        return ps;
      }
    }, holder);

    int newDrawingId = holder.getKey().intValue();
    drawing.setDrawingId(newDrawingId);
    return drawing;
  }

  @Transactional(readOnly = true)
  public Optional<Drawing> findDrawingById(Integer drawingId) {
    try {
      return Optional.of(jdbcTemplate.queryForObject(
          "SELECT D.DRAWING_ID, D.IMAGE, M.MATCH_ID, M.STATUS, M.CREATED_DATE, M.JOIN_TIMEOUT, M.DRAW_TIMEOUT,"
              + " G.GAME_ID, G.SESSION_ID, G.GAME_CODE " + " FROM DRAWING D"
              + " INNER JOIN `MATCH` M ON D.MATCH_ID = M.MATCH_ID"
              + " INNER JOIN GAME G ON M.GAME_ID = G.GAME_ID WHERE M.MATCH_ID = ?",
          new Object[] {drawingId}, new DrawingRowMapper()));
    } catch (EmptyResultDataAccessException e) {
      return Optional.empty();
    }
  }

  @Transactional(readOnly = true)
  public Optional<Drawing> findActiveDrawingByPlayerSessionIdAndMatchId(String playerSessionId,
      Integer matchId) {
    try {
      return Optional.of(jdbcTemplate.queryForObject(
          "SELECT D.DRAWING_ID, D.IMAGE, M.MATCH_ID, M.STATUS, M.CREATED_DATE, M.JOIN_TIMEOUT, M.DRAW_TIMEOUT,"
              + " G.GAME_ID, G.SESSION_ID, G.GAME_CODE " + " FROM DRAWING D"
              + " INNER JOIN `MATCH` M ON D.MATCH_ID = M.MATCH_ID"
              + " INNER JOIN GAME G ON M.GAME_ID = G.GAME_ID"
              + " INNER JOIN MATCH_PLAYER MP ON M.MATCH_ID = MP.MATCH_ID"
              + " INNER JOIN PLAYER P ON MP.PLAYER_ID = P.PLAYER_ID"
              + " INNER JOIN TITLE T ON MP.PLAYER_ID = T.PLAYER_ID AND T.DRAWING_ID = D.DRAWING_ID"
              + " WHERE M.MATCH_ID = ? AND P.SESSION_ID = ?",
          new Object[] {matchId, playerSessionId}, new DrawingRowMapper()));
    } catch (EmptyResultDataAccessException e) {
      return Optional.empty();
    }
  }

  public void update(final Drawing drawing) throws NotFoundException {
    final String sql = "UPDATE DRAWING SET IMAGE = ? WHERE DRAWING_ID = ?";

    int updatedRows = jdbcTemplate.update(new PreparedStatementCreator() {
      @Override
      public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setString(1, drawing.getImage());
        ps.setInt(2, drawing.getDrawingId());
        return ps;
      }
    });

    if (updatedRows == 0) {
      throw new NotFoundException("Match id not found");
    }
  }

  class DrawingRowMapper implements RowMapper<Drawing> {
    @Override
    public Drawing mapRow(ResultSet rs, int rowNum) throws SQLException {
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
      Drawing drawing = new Drawing();
      drawing.setDrawingId(rs.getInt("DRAWING_ID"));
      drawing.setMatch(match);
      drawing.setImage(rs.getString("IMAGE"));
      return drawing;
    }
  }
}
