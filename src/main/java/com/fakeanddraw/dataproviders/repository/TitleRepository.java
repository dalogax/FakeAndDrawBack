package com.fakeanddraw.dataproviders.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.fakeanddraw.domain.model.MasterTitle;
import com.fakeanddraw.domain.model.PlayerDrawing;
import com.fakeanddraw.domain.model.Title;

@Repository
public class TitleRepository {

  @Autowired
  private JdbcTemplate jdbcTemplate;

  @Transactional(readOnly = true)
  public List<MasterTitle> getMasterTitles(int numTitles) {
    /*
     * TODO make it random
     */
    return jdbcTemplate.query("SELECT MASTER_TITLE_ID, DESCRIPTION FROM MASTER_TITLE LIMIT ?",
        new Object[] {numTitles}, new MasterTitleRowMapper());
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
        ps.setBoolean(4, title instanceof PlayerDrawing);
        return ps;
      }
    });
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