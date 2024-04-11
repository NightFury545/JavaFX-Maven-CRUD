package org.nightfury.persistence.entity.entityImpl;

import java.time.LocalDate;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.nightfury.persistence.entity.GenericEntity;

@Data
@Builder
@AllArgsConstructor
public class Movie implements GenericEntity {
    private UUID uuid;
    private String name;
    private String director;
    private LocalDate releaseDate;
    private String genre;
    private String description;
    private Integer durationMinutes;
    private Double rating;
}
