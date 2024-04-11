package org.nightfury.business_logic.service.serviceImpl;

import com.github.javafaker.Faker;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.control.ProgressBar;
import org.nightfury.business_logic.service.GenericService;
import org.nightfury.persistence.entity.entityImpl.Movie;
import org.nightfury.persistence.repository.exception.AlreadyExistsException;
import org.nightfury.persistence.repository.repositoryImpl.MovieRepositoryImpl;

public class MovieServiceImpl extends GenericService<Movie> {

    public MovieServiceImpl() throws SQLException {
        super(new MovieRepositoryImpl());
    }

    public List<Movie> downloadDataFromDataBase(ProgressBar progressBar)
        throws SQLException, InterruptedException {
        List<Movie> movieList = new ArrayList<>();
        MovieRepositoryImpl movieRepository = (MovieRepositoryImpl) super.genericRepository;
        double totalRows = movieRepository.getRowCount();
        for (double i = 0; i < totalRows; i++) {
            movieList.add(super.findByID((int) i));
            progressBar.progressProperty().setValue((i + 1) / totalRows);
            Thread.sleep(25);
        }
        return movieList;
    }

    public void loadDataIntoDataBase(int amountOfRows) throws SQLException, AlreadyExistsException {
        Faker faker = new Faker();
        for (int i = 0; i < amountOfRows; i++) {
            Movie movie = Movie.builder().uuid(UUID.randomUUID()).name(faker.book().title())
                .director(faker.name().fullName())
                .releaseDate(LocalDate.now().minusYears(faker.number().numberBetween(1, 20)))
                .genre(faker.music().genre()).description(faker.lorem().sentence())
                .durationMinutes(faker.number().numberBetween(60, 240))
                .rating(faker.number().randomDouble(1, 1, 10)).build();
            super.save(movie);
        }

    }
}
