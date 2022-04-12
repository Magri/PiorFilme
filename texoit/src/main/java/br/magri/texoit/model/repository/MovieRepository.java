package br.magri.texoit.model.repository;

import br.magri.texoit.model.entity.Movie;
import br.magri.texoit.model.view.WinningProducer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MovieRepository extends JpaRepository<Movie, Long> {

    @Query(nativeQuery = true, value =
            "select * from (\n" +
            "  select producers as producer, followingWin - YEAR as \"INTERVAL\", year as previousWin, followingWin\n" +
            "  from (\n" +
            "    SELECT  LEAD(year) OVER (PARTITION BY producers ORDER BY year) as followingWin, * FROM MOVIE where Winner = 'yes'\n" +
            "  ) as sub \n" +
            ") as filtro where  \"INTERVAL\" in (\n" +
            "                                     select min(followingWin - YEAR)\n" +
            "                                     from (\n" +
            "                                       SELECT  LEAD(year) OVER (PARTITION BY producers ORDER BY year) as followingWin, YEAR FROM MOVIE where Winner = 'yes'\n" +
            "                                     ) as sub\n" +
            "                                   )")
    List<WinningProducer> findMin();

    @Query(nativeQuery = true, value =
            "select * from (\n" +
            "  select producers as producer, followingWin - YEAR as \"INTERVAL\", year as previousWin, followingWin\n" +
            "  from (\n" +
            "    SELECT  LEAD(year) OVER (PARTITION BY producers ORDER BY year) as followingWin, * FROM MOVIE where Winner = 'yes'\n" +
            "  ) as sub \n" +
            ") as filtro where  \"INTERVAL\" in (\n" +
            "                                     select max(followingWin - YEAR)\n" +
            "                                     from (\n" +
            "                                       SELECT  LEAD(year) OVER (PARTITION BY producers ORDER BY year) as followingWin, YEAR FROM MOVIE where Winner = 'yes'\n" +
            "                                     ) as sub\n" +
            "                                   )")
    List<WinningProducer> findMax();
}
