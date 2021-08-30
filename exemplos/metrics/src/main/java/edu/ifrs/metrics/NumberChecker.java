package edu.ifrs.metrics;

import org.eclipse.microprofile.metrics.MetricUnits;
import org.eclipse.microprofile.metrics.annotation.Counted;
import org.eclipse.microprofile.metrics.annotation.Gauge;
import org.eclipse.microprofile.metrics.annotation.Timed;
import org.jboss.resteasy.annotations.jaxrs.PathParam;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/")
public class NumberChecker {

    private long highestPar = 1;

    @GET
    @Path("/{number}")
    @Produces(MediaType.TEXT_PLAIN)
    @Counted(name = "performedChecks", description = "How many primality checks have been performed.")
    @Timed(name = "checksTimer", description = "A measure of how long it takes to perform the primality test.", unit = MetricUnits.MILLISECONDS)
    public String checkParImpar(@PathParam long number) {
        if (number < 1) {
            return "Apenas numeros naturais";
        }

        if (number % 2 == 1) {
            return number + " é impar";
        }

        if (number > highestPar) {
            highestPar = number;
        }
        return number + " é par";
    }

    //o numero ultimo numero par mais alto registrado
    @Gauge(name = "highestPar", unit = MetricUnits.NONE, description = "Highest number so far.")
    public Long highestPar() {
        return highestPar;
    }

}