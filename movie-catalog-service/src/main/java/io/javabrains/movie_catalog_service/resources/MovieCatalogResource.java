package io.javabrains.movie_catalog_service.resources;

import io.javabrains.movie_catalog_service.model.CatalogItem;
import io.javabrains.movie_catalog_service.model.Movie;
import io.javabrains.movie_catalog_service.model.Rating;
import io.javabrains.movie_catalog_service.model.UserRating;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import javax.xml.catalog.Catalog;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/catalog")
public class MovieCatalogResource {

    @Autowired
    private  RestTemplate restTemplate;

    @Autowired
    private WebClient.Builder webClientBuilder;
    @GetMapping("/{userId}")
    public List<CatalogItem> getCatalog(@PathVariable("userId") String userId){

        WebClient.Builder builder=WebClient.builder();


        UserRating ratings = restTemplate.getForObject("http://ratings-data-service/ratingsdata/users/"+userId, UserRating.class);
        return ratings.getUserRating().stream().map(rating -> {
            Movie movie=restTemplate.getForObject("http://movie-info-service/movies/"+rating.getMovieId() , Movie.class);

//            Movie movie= webClientBuilder.build()
//                    .get()
//                    .uri("http://localhost:8081/movies/"+rating.getMovieId())
//                    .retrieve()
//                    .bodyToMono(Movie.class)
//                    .block();
            return new CatalogItem(movie.getName(), "DESC", rating.getRating());
        })
                .collect(Collectors.toList());


    }
}
