package br.magri.texoit.model.repository;

import br.magri.texoit.model.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MovieRepository extends JpaRepository<Movie, Long> {

    List<Movie> findByWinner(String winner);
}
