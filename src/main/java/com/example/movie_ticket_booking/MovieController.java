package com.example.movie_ticket_booking;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class MovieController {
    @Autowired
    MovieRepo movieRepo;
    @PostMapping("/movies")
    public ResponseEntity<Movie> addMovie(@RequestBody Movie movie){
        movieRepo.save(movie);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
    @GetMapping("/movies/{id}")
    public ResponseEntity<Movie> getMovie(@PathVariable(value="id") Long id){
        return new ResponseEntity<>(movieRepo.findById(id).get(),HttpStatus.OK);
    }

    @GetMapping("/movies")
    public ResponseEntity<List<Movie>> getAllMovies() {
        List<Movie> movies = movieRepo.findAll();
        return new ResponseEntity<>(movies, HttpStatus.OK);
    }

    @PutMapping("/movies/{id}")
    public ResponseEntity<Movie> updateMovie(@PathVariable Long id, @RequestBody Movie updatedMovie) {
        if (!movieRepo.existsById(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        updatedMovie.setId(id);
        return new ResponseEntity<>(movieRepo.save(updatedMovie), HttpStatus.OK);
    }

    @DeleteMapping("/movies/{id}")
    public ResponseEntity<Void> deleteMovie(@PathVariable Long id) {
        if (movieRepo.existsById(id)) {
            movieRepo.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    @PutMapping("/movies/{id}/bookSeat")
    public ResponseEntity<Movie> bookSeat(@PathVariable Long id, @RequestBody BookSeats request) {
        Optional<Movie> movie = movieRepo.findById(id);
        int seatsToBook = request.getSeats();

        if (seatsToBook <= 0) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        if (movie.get().getAvailableSeats() >= seatsToBook) {
            movie.get().setAvailableSeats(movie.get().getAvailableSeats() - seatsToBook);
            movieRepo.save(movie.get());
            return new ResponseEntity<>(movie.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST); // Not enough seats available
        }
    }

}
