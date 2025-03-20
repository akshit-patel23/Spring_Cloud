package io.javabrains.ratings_data_service.models;

public class Rating {
    private String movieId;
    private  int Rating;

    public Rating(String movieId, int rating) {
        this.movieId = movieId;
        Rating = rating;
    }

    public String getMovieId() {
        return movieId;
    }

    public void setMovieId(String movieId) {
        this.movieId = movieId;
    }

    public int getRating() {
        return Rating;
    }

    public void setRating(int rating) {
        Rating = rating;
    }
}
