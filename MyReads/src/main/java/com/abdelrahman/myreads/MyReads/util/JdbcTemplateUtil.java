package com.abdelrahman.myreads.MyReads.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

import java.util.List;

public class JdbcTemplateUtil {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

   /* List<Long> getBookIds(String pref){
        SqlParameterSource namedParameters = new MapSqlParameterSource().addValue("pref", pref+":*");

        String query = "SELECT id FROM book WHERE to_tsvector(title) @@ to_tsquery()";
        namedParameterJdbcTemplate.query(query, (RowCallbackHandler) namedParameters);

    }*/
}
