package ch.hftm.api.logic;

import javax.ws.rs.GET;
import javax.ws.rs.QueryParam;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import ch.hftm.api.models.weather.Weather;

@RegisterRestClient(configKey = "weatherapi")
public interface WeatherApi {

    @GET
    Weather getWeatherByCity(
            @QueryParam("key") String apiKey,
            @QueryParam("q") String city,
            @QueryParam("aqi") String aqi);

}
