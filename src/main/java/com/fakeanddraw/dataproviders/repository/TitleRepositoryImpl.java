package com.fakeanddraw.dataproviders.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.fakeanddraw.domain.model.MasterTitle;
import com.fakeanddraw.domain.repository.TitleRepository;

@Repository
public class TitleRepositoryImpl implements TitleRepository {

  @Autowired
  private JdbcTemplate jdbcTemplate;

  @Override
  @Transactional(readOnly = true)
  public List<MasterTitle> getMasterTitles(int numTitles) {
    /*
     * TODO make it random
     */
    return jdbcTemplate.query("SELECT MASTER_TITLE_ID, DESCRIPTION FROM MASTER_TITLE LIMIT ?",
        new Object[] {numTitles}, new MasterTitleRowMapper());
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
