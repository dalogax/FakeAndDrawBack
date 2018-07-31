package com.fakeanddraw.dataproviders.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.fakeanddraw.core.domain.Drawing;
import com.fakeanddraw.core.domain.Game;
import com.fakeanddraw.core.domain.GameFactory;
import com.fakeanddraw.core.domain.MasterTitle;
import com.fakeanddraw.core.domain.Match;
import com.fakeanddraw.core.domain.MatchFactory;
import com.fakeanddraw.core.domain.MatchStatus;
import com.fakeanddraw.core.domain.Player;
import com.fakeanddraw.core.domain.Title;

@Repository
public class TitleRepository {

  @Autowired
  private JdbcTemplate jdbcTemplate;

  @Autowired
  private GameFactory gameFactory;

  @Autowired
  private MatchFactory matchFactory;

  @Transactional(readOnly = true)
  public List<MasterTitle> getMasterTitles(int numTitles) {
    return jdbcTemplate.query("SELECT MASTER_TITLE_ID, DESCRIPTION FROM MASTER_TITLE "
        + "ORDER BY RAND() " + "FETCH FIRST ? ROWS ONLY", new Object[] {numTitles},
        new MasterTitleRowMapper());
  }

  public void create(final Title title) {
    final String sql =
        "INSERT INTO TITLE (DRAWING_ID, PLAYER_ID, DESCRIPTION, IS_ORIGINAL) VALUES(?, ?, ?, ?)";

    jdbcTemplate.update(new PreparedStatementCreator() {
      @Override
      public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1, title.getDrawing().getDrawingId());
        ps.setInt(2, title.getPlayer().getPlayerId());
        ps.setString(3, title.getDescription());
        ps.setBoolean(4, title.getIsOriginal());
        return ps;
      }
    });
  }

  @Transactional(readOnly = true)
  public Optional<Title> findCurrentTitleByPlayerSessionId(String playerSessionId) {
    try {
      return Optional.of(jdbcTemplate.queryForObject(
          "SELECT T.DESCRIPTION, T.IS_ORIGINAL, D.DRAWING_ID, D.IMAGE, M.MATCH_ID, M.STATUS, M.CREATED_DATE, M.JOIN_TIMEOUT, M.DRAW_TIMEOUT,"
              + " G.GAME_ID, G.SESSION_ID AS GAME_SESSION_ID, G.GAME_CODE, P.PLAYER_ID, P.SESSION_ID AS PLAYER_SESSION_ID, P.USERNAME "
              + " FROM TITLE T" + " INNER JOIN DRAWING D ON T.DRAWING_ID = D.DRAWING_ID"
              + " INNER JOIN `MATCH` M ON D.MATCH_ID = M.MATCH_ID"
              + " INNER JOIN GAME G ON M.GAME_ID = G.GAME_ID"
              + " INNER JOIN MATCH_PLAYER MP ON M.MATCH_ID = MP.MATCH_ID AND MP.PLAYER_ID = T.PLAYER_ID"
              + " INNER JOIN PLAYER P ON MP.PLAYER_ID = P.PLAYER_ID" + " WHERE P.SESSION_ID = ?"
              + " ORDER BY M.CREATED_DATE DESC FETCH FIRST ROW ONLY",
          new Object[] {playerSessionId}, new TitleRowMapper()));
    } catch (EmptyResultDataAccessException e) {
      return Optional.empty();
    }
  }

  class TitleRowMapper implements RowMapper<Title> {
    @Override
    public Title mapRow(ResultSet rs, int rowNum) throws SQLException {
      Match match = matchFactory.createEmptyMatch();
      match.setMatchId(rs.getInt("MATCH_ID"));
      Game game = gameFactory.createEmptyGame();
      game.setGameId(rs.getInt("GAME_ID"));
      game.setSessionId(rs.getString("GAME_SESSION_ID"));
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
      Player player = new Player();
      player.setPlayerId(rs.getInt("PLAYER_ID"));
      player.setSessionId(rs.getString("PLAYER_SESSION_ID"));
      player.setUserName(rs.getString("USERNAME"));
      Title title = new Title();
      title.setDrawing(drawing);
      title.setPlayer(player);
      title.setDescription(rs.getString("DESCRIPTION"));
      title.setIsOriginal(rs.getBoolean("IS_ORIGINAL"));
      return title;
    }
  }

  class MasterTitleRowMapper implements RowMapper<MasterTitle> {
    @Override
    public MasterTitle mapRow(ResultSet rs, int rowNum) throws SQLException {
      MasterTitle masterTitle = new MasterTitle();
      masterTitle.setMasterTitleId(rs.getInt("MASTER_TITLE_ID"));
      masterTitle.setDescription(rs.getString("DESCRIPTION"));
      return masterTitle;
    }
  }
}
