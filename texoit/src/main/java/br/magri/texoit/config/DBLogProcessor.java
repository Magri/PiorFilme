package br.magri.texoit.config;
 
import br.magri.texoit.model.entity.Movie;
import org.springframework.batch.item.ItemProcessor;

public class DBLogProcessor implements ItemProcessor<Movie, Movie>
{
    public Movie process(Movie movie) throws Exception
    {
        System.out.println("Inserting movie : " + movie.getTitle());
        return movie;
    }
}