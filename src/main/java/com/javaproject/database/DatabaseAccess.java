package com.javaproject.database;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Repository;

import com.javaproject.beans.BoardBase;
import com.javaproject.beans.Review;

import lombok.AllArgsConstructor;
import lombok.Data;

@Repository
// @AllArgsConstructor
public class DatabaseAccess {

    // autowired using AllArgsConstructor
    @Autowired
    private NamedParameterJdbcTemplate jdbc;

    public List<String> getAuthorities() {

        MapSqlParameterSource namedParameters = new MapSqlParameterSource();

        String query = "SELECT DISTINCT authority FROM authorities";

        List<String> authorities = jdbc.queryForList(query, namedParameters, String.class);

        return authorities;
    }

    public List<BoardBase> getBoardBases() {

        String query = "SELECT * FROM boardbases";

        BeanPropertyRowMapper boardbaseMapper = new BeanPropertyRowMapper<>(BoardBase.class);

        List<BoardBase> boardbases = jdbc.query(query, boardbaseMapper);
        return boardbases;
    }

    public BoardBase getBoardBase(Long id) {
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();

        String query = "SELECT * FROM boardbases WHERE id = :id";
        namedParameters.addValue("id", id);
        BeanPropertyRowMapper boardbaseMapper = new BeanPropertyRowMapper<>(BoardBase.class);
        List<BoardBase> boardbases = jdbc.query(query, namedParameters, boardbaseMapper);
        if (boardbases.isEmpty()) {
            return null;
        } else {
            return boardbases.get(0);
        }
    }

    public List<Review> getReviews(Long id) {
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();

        String query = "SELECT * FROM reviews WHERE gameId = :id";
        namedParameters.addValue("id", id);
        BeanPropertyRowMapper reviewMapper = new BeanPropertyRowMapper<>(Review.class);
        List<Review> reviews = jdbc.query(query, namedParameters, reviewMapper);
        if (reviews.isEmpty()) {
            return null;
        } else {
            return reviews;
        }
    }

    public Long addBoardBase(BoardBase boardbase) {
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        String query = "INSERT INTO boardbases (name, level, minPlayers, maxPlayers, gameType) VALUES (:name, :level, :minPlayers, :maxPlayers, :gameType)";
        namedParameters
                .addValue("name", boardbase.getName())
                .addValue("level", boardbase.getLevel())
                .addValue("minPlayers", boardbase.getMinPlayers())
                .addValue("maxPlayers", boardbase.getMaxPlayers())
                .addValue("gameType", boardbase.getGameType());
        KeyHolder generatedKey = new GeneratedKeyHolder();
        int returnValue = jdbc.update(query, namedParameters, generatedKey);
        Long boardBaseId = (Long) generatedKey.getKey();
        return (returnValue > 0) ? boardBaseId : 0;
    }

    public int addReview(Review review) {
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        String query = "INSERT INTO reviews (gameId, text) VALUES (:gameId, :text)";
        namedParameters.addValue("gameId", review.getGameId())
                .addValue("text", review.getText());

        return jdbc.update(query, namedParameters);
    }

    public int deleteReview(Long id) {
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        String query = "DELETE FROM reviews WHERE id = :id";
        namedParameters.addValue("id", id);
        return jdbc.update(query, namedParameters);
    }

    public Review getReview(Long id) {
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();

        String query = "SELECT * FROM reviews WHERE id = :id";
        namedParameters.addValue("id", id);
        BeanPropertyRowMapper reviewMapper = new BeanPropertyRowMapper<>(Review.class);
        List<Review> reviews = jdbc.query(query, namedParameters, reviewMapper);
        if (reviews.isEmpty()) {
            return null;
        } else {
            return reviews.get(0);
        }
    }

    public int editReview(Review review) {
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();

        String query = "UPDATE reviews SET text = :text "
                + "WHERE id = :id";

        namedParameters
                .addValue("text", review.getText())
                .addValue("id", review.getId());
        return jdbc.update(query, namedParameters);
    }
}
