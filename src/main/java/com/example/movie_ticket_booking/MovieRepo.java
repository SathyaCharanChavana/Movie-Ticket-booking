package com.example.movie_ticket_booking;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieRepo extends JpaRepository<Movie,Long> {
//    public Map<Long,Movie> movies= new HashMap<>();

}
