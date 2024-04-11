package org.nightfury.persistence.repository.repositoryImpl;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import org.nightfury.persistence.entity.entityImpl.Movie;
import org.nightfury.persistence.repository.GenericRepository;
import org.nightfury.persistence.repository.mapper.mapperImpl.MovieMapper;

public class MovieRepositoryImpl extends GenericRepository<Movie> {


    public MovieRepositoryImpl() throws SQLException {
        super(DriverManager.getConnection("jdbc:sqlite:db/movieService.db"), "movies", new MovieMapper());
    }

    @Override
    protected List<String> tableAttributes() {
        return List.of(
            "uuid", "name", "director", "releaseDate", "genre", "description", "durationMinutes", "rating");
    }

    public int getRowCount() {
        String sql = "SELECT COUNT(uuid) AS kilkist FROM movies";
        try {
            Statement statement = super.connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            return resultSet.getInt("kilkist");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected List<Object> tableValues(Movie entity) {
        return List.of(entity.getUuid(), entity.getName(), entity.getDirector(),
            entity.getReleaseDate(), entity.getGenre(), entity.getDescription(),
            entity.getDurationMinutes(), entity.getRating());

    }
}
