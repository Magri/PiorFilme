package br.magri.texoit.service;

import br.magri.texoit.model.entity.Movie;

import java.util.List;
import java.util.Optional;

public interface MovieService {

    Optional<Movie> getById(Long id);

    List<Movie> findAll();

    List<Movie> findByWinner(String winner);

}
