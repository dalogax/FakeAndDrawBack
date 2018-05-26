package com.fakeanddraw.model.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.fakeanddraw.model.entity.MasterTitle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class TitleRepository
{
    @Autowired
    private JdbcTemplate jdbcTemplate;
 
    @Transactional(readOnly=true)
    public List<MasterTitle> getMasterTitles(int numTitles) {
        return jdbcTemplate.query("SELECT ID, DESCRIPTION FROM MASTER_TITLE LIMIT ?", 
            new Object[]{numTitles},new MasterTitleRowMapper());
    }
}
 
class MasterTitleRowMapper implements RowMapper<MasterTitle>
{
    @Override
    public MasterTitle mapRow(ResultSet rs, int rowNum) throws SQLException {
        MasterTitle masterTitle = new MasterTitle();
        masterTitle.setId(rs.getInt("ID"));
        masterTitle.setDescription(rs.getString("DESCRIPTION"));
        return masterTitle;
    }
}