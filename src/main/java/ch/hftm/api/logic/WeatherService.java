package ch.hftm.api.logic;

import javax.ws.rs.GET;
import javax.ws.rs.QueryParam;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import ch.hftm.api.models.weather.Root;

@RegisterRestClient(configKey = "weatherapi")
public interface WeatherService {

    @GET
    Root getWeatherByCity(
            @QueryParam("key") String apiKey,
            @QueryParam("q") String city,
            @QueryParam("aqi") String aqi);

}
