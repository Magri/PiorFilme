package br.magri.texoit.api.resource;

import br.magri.texoit.api.dto.MovieDTO;
import br.magri.texoit.api.dto.WinningProducerDTO;
import br.magri.texoit.api.dto.WorstMovieDTO;
import br.magri.texoit.service.MovieService;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/movies")
public class MovieController {

    private MovieService movieService;
    private ModelMapper modelMapper;

    public MovieController(MovieService movieService, ModelMapper modelMapper) {
        this.movieService = movieService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("{id}")
    public MovieDTO get(@PathVariable Long id) {
        return movieService
                .getById(id)
                .map( movie -> modelMapper.map(movie, MovieDTO.class)).get();
    }

    @GetMapping
    public List<MovieDTO> findAll() {
        return movieService
                .findAll().stream().map(movie -> modelMapper.map(movie, MovieDTO.class)).collect(Collectors.toList());
    }

    @GetMapping("min")
    public WorstMovieDTO findMin() {
        WorstMovieDTO worstMovieDTO = new WorstMovieDTO();

        List<WinningProducerDTO> minWPDTO = movieService
                .findMin().stream().map(movie -> modelMapper.map(movie, WinningProducerDTO.class)).collect(Collectors.toList());

        List<WinningProducerDTO> maxWPDTO = movieService
                .findMax().stream().map(movie -> modelMapper.map(movie, WinningProducerDTO.class)).collect(Collectors.toList());

        worstMovieDTO.setMin(minWPDTO);
        worstMovieDTO.setMax(maxWPDTO);

        return worstMovieDTO;
    }
}
