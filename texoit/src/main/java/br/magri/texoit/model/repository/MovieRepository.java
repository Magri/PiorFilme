package br.magri.texoit.model.repository;

import br.magri.texoit.model.entity.Movie;
import br.magri.texoit.model.view.WinningProducer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MovieRepository extends JpaRepository<Movie, Long> {

    @Query(nativeQuery = true, value =
            "select distinct *\n" +
            "from (\n" +
            "           select MOVIE.producers as producer, abs(MOVIE.year -  MOVIE2.year) as \"INTERVAL\", MOVIE.year as followingWin, MOVIE2.year as previousWin from MOVIE\n" +
            "           inner join MOVIE as MOVIE2 on LOCATE(MOVIE.producers, MOVIE2.producers) and MOVIE.id <> MOVIE2.id\n" +
            "           where MOVIE.winner = 'yes' and MOVIE2.winner = 'yes'\n" +
            ") as filtro where  \"INTERVAL\" in (\n" +
            "                                                     select min(diferenca)\n" +
            "                                                     from (\n" +
            "                                                               select abs(MOVIE.year -  MOVIE2.year) as diferenca from MOVIE\n" +
            "                                                               inner join MOVIE as MOVIE2 on LOCATE(MOVIE.producers, MOVIE2.producers) and MOVIE.id <> MOVIE2.id\n" +
            "                                                               where MOVIE.winner = 'yes' and MOVIE2.winner = 'yes'\n" +
            "                                                              ) as sub\n" +
            "                                                     )")
    List<WinningProducer> findMin();

    @Query(nativeQuery = true, value =
            "select distinct *\n" +
            "from (\n" +
            "           select MOVIE.producers as producer, abs(MOVIE.year -  MOVIE2.year) as \"INTERVAL\", MOVIE.year as followingWin, MOVIE2.year as previousWin from MOVIE\n" +
            "           inner join MOVIE as MOVIE2 on LOCATE(MOVIE.producers, MOVIE2.producers) and MOVIE.id <> MOVIE2.id\n" +
            "           where MOVIE.winner = 'yes' and MOVIE2.winner = 'yes'\n" +
            ") as filtro where  \"INTERVAL\" in (\n" +
            "                                                     select max(diferenca)\n" +
            "                                                     from (\n" +
            "                                                               select abs(MOVIE.year -  MOVIE2.year) as diferenca from MOVIE\n" +
            "                                                               inner join MOVIE as MOVIE2 on LOCATE(MOVIE.producers, MOVIE2.producers) and MOVIE.id <> MOVIE2.id\n" +
            "                                                               where MOVIE.winner = 'yes' and MOVIE2.winner = 'yes'\n" +
            "                                                              ) as sub\n" +
            "                                                     )")
    List<WinningProducer> findMax();

    List<Movie> findByWinner(String winner);
}
