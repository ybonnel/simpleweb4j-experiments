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
     * Object return by route.
     */
    @Entity
    public static class Hello {
        @Id
        @GeneratedValue
        public Long id;

        public String value;

        public static SimpleEntityManager<Hello, Long> simpleEntityManager = new SimpleEntityManager<>(Hello.class);
    }

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


        // Insert datas
        SimpleEntityManager.openSession().beginTransaction();
        Hello hello = new Hello();
        hello.value = "Hello World!";
        Hello.simpleEntityManager.save(hello);
        SimpleEntityManager.getCurrentSession().getTransaction().commit();
        SimpleEntityManager.closeSession();

        // Declare the route "/hello" for GET method whith no param in request payload.
        get(new Route<Void, Hello>("/hello", Void.class) {
            @Override
            public Response<Hello> handle(Void param, RouteParameters routeParams) throws HttpErrorException {
                return new Response<>(Hello.simpleEntityManager.getAll().iterator().next());
            }
        });

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
        // For main, we want to wait the stop.
        startServer(getPort(), true);
    }
}
