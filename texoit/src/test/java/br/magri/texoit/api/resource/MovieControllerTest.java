package br.magri.texoit.api.resource;

import br.magri.texoit.api.dto.WinningProducerDTO;
import br.magri.texoit.api.dto.WorstMovieDTO;
import br.magri.texoit.model.repository.MovieRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MovieControllerTest {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private MovieRepository repository;

    @Test
    public void findWinnerTest() {

        ResponseEntity<WorstMovieDTO> response = this.testRestTemplate
                .exchange("/api/movies", HttpMethod.GET, null, WorstMovieDTO.class);

        Assertions.assertEquals(response.getStatusCode(), HttpStatus.OK);

        WorstMovieDTO worstMovieDTO = response.getBody();
        WinningProducerDTO minWinningProducerDTO = worstMovieDTO.getMin().get(0);
        Assertions.assertEquals(minWinningProducerDTO.getProducer(), "Joel Silver");
        Assertions.assertEquals(minWinningProducerDTO.getInterval(), 1);
        Assertions.assertEquals(minWinningProducerDTO.getPreviousWin(), 1990);
        Assertions.assertEquals(minWinningProducerDTO.getFollowingWin(), 1991);

        WinningProducerDTO maxWinningProducerDTO = worstMovieDTO.getMax().get(0);
        Assertions.assertEquals(maxWinningProducerDTO.getProducer(), "Matthew Vaughn");
        Assertions.assertEquals(maxWinningProducerDTO.getInterval(), 13);
        Assertions.assertEquals(maxWinningProducerDTO.getPreviousWin(), 2015);
        Assertions.assertEquals(maxWinningProducerDTO.getFollowingWin(), 2002);

    }

}
