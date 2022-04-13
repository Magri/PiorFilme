package br.magri.texoit.api.resource;

import br.magri.texoit.api.dto.MovieDTO;
import br.magri.texoit.api.dto.WinningProducerDTO;
import br.magri.texoit.api.dto.WorstMovieDTO;
import br.magri.texoit.model.entity.Movie;
import br.magri.texoit.service.MovieService;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
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

    @GetMapping("all")
    public List<MovieDTO> findAll() {
        return movieService
                .findAll().stream().map(movie -> modelMapper.map(movie, MovieDTO.class)).collect(Collectors.toList());
    }

    @GetMapping("findWinner")
    public List<MovieDTO> findWinnerMovie() {
        return movieService
                .findByWinner("yes").stream().map(movie -> modelMapper.map(movie, MovieDTO.class)).collect(Collectors.toList());
    }

    @GetMapping("old")
    public WorstMovieDTO findWinnerOld() {

        List<WinningProducerDTO> minWPDTO = movieService
                .findMin().stream().map(movie -> modelMapper.map(movie, WinningProducerDTO.class)).collect(Collectors.toList());

        List<WinningProducerDTO> maxWPDTO = movieService
                .findMax().stream().map(movie -> modelMapper.map(movie, WinningProducerDTO.class)).collect(Collectors.toList());

        WorstMovieDTO worstMovieDTO = new WorstMovieDTO();
        worstMovieDTO.setMin(minWPDTO);
        worstMovieDTO.setMax(maxWPDTO);

        return worstMovieDTO;
    }

    @GetMapping
    public WorstMovieDTO findWinner() {

        List<Movie> winnerMovie = movieService.findByWinner("yes");
        List<WinningProducerDTO> winningProducerDTO = new ArrayList<WinningProducerDTO>();

        for (Movie movie : winnerMovie) {
            List<String> producers = Arrays.asList(movie.getProducers().replaceAll(", and | and ", ", ").split(", "));

            for (String s : producers) {
                System.out.println(s);
            }
            for (Movie movie2 : winnerMovie) {
                if (movie.getId() != movie2.getId() && movie.getYear() <= movie2.getYear()) {
                    List<String> producers2 = Arrays.asList(movie2.getProducers().replaceAll(", and | and ", ", ").split(", "));

                    for (String producer : producers) {
                        if (producers2.contains(producer) ) {
                            //System.out.println("producer: " + producer + "\n   movie 1: " + movie.getTitle() + " " + movie.getYear() + " " + movie.getProducers() + "\n   movie 2 " + movie2.getTitle() + " " + movie2.getYear() + " " + movie2.getProducers());
                            winningProducerDTO.add(new WinningProducerDTO(producer, movie2.getYear() - movie.getYear(), movie.getYear(), movie2.getYear()));

                        }
                    }
                }
            }
        }

        List<WinningProducerDTO> minWPDTO = winningProducerDTO.stream()
                .collect(Collectors.groupingBy(WinningProducerDTO::getInterval)).entrySet().stream()
                .min(Map.Entry.comparingByKey()).get().getValue();

        List<WinningProducerDTO> maxWPDTO = winningProducerDTO.stream()
                .collect(Collectors.groupingBy(WinningProducerDTO::getInterval)).entrySet().stream()
                .max(Map.Entry.comparingByKey()).get().getValue();

        WorstMovieDTO worstMovieDTO = new WorstMovieDTO();
        worstMovieDTO.setMin(minWPDTO);
        worstMovieDTO.setMax(maxWPDTO);

        return worstMovieDTO;
    }
}
