package fr.ybonnel.simpleweb4j.experiments;

import fr.ybonnel.simpleweb4j.exception.HttpErrorException;
import fr.ybonnel.simpleweb4j.handlers.Response;
import fr.ybonnel.simpleweb4j.handlers.Route;
import fr.ybonnel.simpleweb4j.handlers.RouteParameters;
import fr.ybonnel.simpleweb4j.model.SimpleEntityManager;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import static fr.ybonnel.simpleweb4j.SimpleWeb4j.*;

/**
 * Main class.
 */
public class Experiments {

    /**
     * Start the server.
     * @param port http port to listen.
     * @param waitStop true to wait the stop.
     */
    public static void startServer(int port, boolean waitStop) {
        // Set the http port.
        setPort(port);
        // Set the path to static resources.
        setPublicResourcesPath("/fr/ybonnel/simpleweb4j/experiments/public");

        resource(new BeerRessource("/beer"));

        // Start the server.
        start(waitStop);
    }

    /**
     * Get the port.
     * <ul>
     *     <li>Heroku : System.getenv("PORT")</li>
     *     <li>Cloudbees : System.getProperty("app.port")</li>
     *     <li>default : 9999</li>
     * </ul>
     * @return port to use
     */
    private static int getPort() {
        // Heroku
        String herokuPort = System.getenv("PORT");
        if (herokuPort != null) {
            return Integer.parseInt(herokuPort);
        }

        // Cloudbees
        String cloudbeesPort = System.getProperty("app.port");
        if (cloudbeesPort != null) {
            return Integer.parseInt(cloudbeesPort);
        }

        // Default port;
        return 9999;
    }

    public static void main(String[] args) {

        SimpleEntityManager.openSession().beginTransaction();

        for (Beer beerToDelete : Beer.simpleEntityManager.getAll()) {
            Beer.simpleEntityManager.delete(beerToDelete.getId());
        }

        Beer beer = new Beer();
        beer.setName("Castel");

        Beer.simpleEntityManager.save(beer);
        SimpleEntityManager.getCurrentSession().getTransaction().commit();
        SimpleEntityManager.closeSession();

        // For main, we want to wait the stop.
        startServer(getPort(), true);
    }
}
