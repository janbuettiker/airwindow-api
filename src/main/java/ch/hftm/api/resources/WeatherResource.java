package ch.hftm.api.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

import org.eclipse.microprofile.rest.client.inject.RestClient;

import ch.hftm.api.logic.WeatherService;
import ch.hftm.api.models.weather.Root;
import io.smallrye.common.annotation.Blocking;

@Path("/weather")
public class WeatherResource {

    private final String API_KEY = "b6dc63b8d3dd444caed124552220208";
    private final String AQI = "no";

    @RestClient
    WeatherService weatherService;

    @GET
    @Blocking
    public Root getWeather() {
        return weatherService.getWeatherByCity(API_KEY, "Zurich", AQI);
    }

}
