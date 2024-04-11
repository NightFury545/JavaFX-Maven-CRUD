package org.nightfury.persistence.repository.mapper.mapperImpl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.UUID;
import org.nightfury.persistence.entity.entityImpl.Movie;
import org.nightfury.persistence.repository.mapper.RowMapper;

public class MovieMapper implements RowMapper<Movie> {

    public Movie mapRow(ResultSet rs) throws SQLException {
        return Movie.builder()
            .uuid(UUID.fromString(rs.getString("uuid")))
            .name(rs.getString("name"))
            .director(rs.getString("director"))
            .releaseDate(rs.getObject("releaseDate", LocalDate.class))
            .genre(rs.getString("genre"))
            .description(rs.getString("description"))
            .durationMinutes(rs.getInt("durationMinutes"))
            .rating(rs.getDouble("rating"))
            .build();
    }


}
