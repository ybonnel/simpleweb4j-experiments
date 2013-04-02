package fr.ybonnel.simpleweb4j.experiments;


import fr.ybonnel.simpleweb4j.model.SimpleEntityManager;
import fr.ybonnel.simpleweb4j.test.SimpleWeb4jTest;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static fr.ybonnel.simpleweb4j.SimpleWeb4j.stop;
import static org.fest.assertions.Assertions.assertThat;

public class ExperimentsWebTest extends SimpleWeb4jTest {

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
        goTo("/");
    }

    @After
    public void tearDown() {
        stop();
    }

    @Test
    public void testIhm() {
        assertThat(find("tr td").getTexts()).contains("Castel");
    }

}
