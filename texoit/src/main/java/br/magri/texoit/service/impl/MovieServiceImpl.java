package br.magri.texoit.service.impl;

import br.magri.texoit.model.entity.Movie;
import br.magri.texoit.model.repository.MovieRepository;
import br.magri.texoit.model.view.WinningProducer;
import br.magri.texoit.service.MovieService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MovieServiceImpl implements MovieService {

    private MovieRepository repository;

    public MovieServiceImpl(MovieRepository repository) {
        this.repository = repository;
    }

    @Override
    public Optional<Movie> getById(Long id) {
        return repository.findById(id);
    }

    @Override
    public List<Movie> findAll() {
        return repository.findAll();
    }

    @Override
    public List<Movie> findByWinner(String winner) {
        return repository.findByWinner(winner);
    }

    @Override
    public List<WinningProducer> findMin() {
        return repository.findMin();
    }

    @Override
    public List<WinningProducer> findMax() {
        return repository.findMax();
    }
}
