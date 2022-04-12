package br.magri.texoit.service;

import br.magri.texoit.model.entity.Movie;
import br.magri.texoit.model.view.WinningProducer;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.Optional;

public interface MovieService {

    Optional<Movie> getById(Long id);

    List<Movie> findAll();

    List<WinningProducer> findMin();

    List<WinningProducer> findMax();

}
