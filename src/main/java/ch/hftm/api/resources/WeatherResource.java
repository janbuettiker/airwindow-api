package ch.hftm.api.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

import org.eclipse.microprofile.rest.client.inject.RestClient;

import ch.hftm.api.logic.WeatherApi;
import ch.hftm.api.models.weather.Weather;
import io.smallrye.common.annotation.Blocking;

/**
 * More or less only used for debugging
 * Gets the data of the city Zurich
 */
@Path("/weather")
public class WeatherResource {

    private final String API_KEY = "b6dc63b8d3dd444caed124552220208";
    private final String CITY = "Zurich";

    @RestClient
    WeatherApi weatherApi;

    /**
     * Gets weather from https://weatherapi.com
     * 
     * @return Weather
     */
    @GET
    @Blocking
    public Weather getWeather() {
        return weatherApi.getWeatherByCity(API_KEY, CITY);
    }

}
