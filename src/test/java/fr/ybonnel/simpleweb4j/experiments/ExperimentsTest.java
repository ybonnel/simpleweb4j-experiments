package fr.ybonnel.simpleweb4j.experiments;

import com.github.kevinsawicki.http.HttpRequest;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import fr.ybonnel.simpleweb4j.model.SimpleEntityManager;
import fr.ybonnel.simpleweb4j.test.SimpleWeb4jTest;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static fr.ybonnel.simpleweb4j.SimpleWeb4j.stop;
import static org.fest.assertions.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

public class ExperimentsTest extends SimpleWeb4jTest {

    @Before
    public void setup() {

        SimpleEntityManager.openSession().beginTransaction();

        for (Beer beerToDelete : Beer.simpleEntityManager.getAll()) {
            Beer.simpleEntityManager.delete(beerToDelete.getId());
        }

        Beer beer = new Beer();
        beer.setName("Castel");

        Beer.simpleEntityManager.save(beer);
        SimpleEntityManager.getCurrentSession().getTransaction().commit();
        SimpleEntityManager.closeSession();
        Experiments.startServer(getPort(), false);
    }

    @After
    public void tearDown() {
        stop();
    }

    @Test
    public void testHelloWorldService() {

        List<Beer> beers = new Gson().fromJson(
                HttpRequest.get(defaultUrl() + "/beer").body(),
                new TypeToken<List<Beer>>() {}.getType());

        assertEquals(1, beers.size());
        Beer beerFromJson = beers.get(0);
        assertThat(beerFromJson.getName()).isEqualTo("Castel");
    }

}
