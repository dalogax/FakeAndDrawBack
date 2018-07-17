package com.fakeanddraw.domain.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.fakeanddraw.domain.model.Player;

@Repository
public class PlayerRepository {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Transactional(readOnly = true)
	public List<Player> findPlayersByGame(Integer gameId) {
		return jdbcTemplate.query(
				"SELECT P.PLAYER_ID, P.SESSION_ID, P.USERNAME FROM PLAYER P "
						+ "INNER JOIN GAME_PLAYER GP ON GP.PLAYER_ID = P.PLAYER_ID " + "WHERE GP.GAME_ID = ? ",
				new Object[] { gameId }, new PlayerRowMapper());
	}

	public Player create(final Player player) {
		final String sql = "INSERT INTO PLAYER (SESSION_ID,USERNAME) VALUES(?,?)";

		KeyHolder holder = new GeneratedKeyHolder();
		jdbcTemplate.update(new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
				PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				ps.setString(1, player.getSessionId());
				ps.setString(2, player.getUserName());
				return ps;
			}
		}, holder);

		int newPlayerId = holder.getKey().intValue();
		player.setPlayerId(newPlayerId);
		return player;
	}

	class PlayerRowMapper implements RowMapper<Player> {
		@Override
		public Player mapRow(ResultSet rs, int rowNum) throws SQLException {
			Player player = new Player();
			player.setPlayerId(rs.getInt("PLAYER_ID"));
			player.setSessionId(rs.getString("SESSION_ID"));
			player.setUserName(rs.getString("USERNAME"));
			return player;
		}
	}
}