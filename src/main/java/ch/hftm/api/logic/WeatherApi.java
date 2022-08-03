package ch.hftm.api.logic;

import javax.ws.rs.GET;
import javax.ws.rs.QueryParam;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import ch.hftm.api.models.weather.Weather;

@RegisterRestClient(configKey = "weatherapi")
public interface WeatherApi {

    /**
     * GET weather information from https://www.weatherapi.com
     * Parameters for the external API can be found here:
     * https://www.weatherapi.com/docs/
     * 
     * @param apiKey - API Key, bound to jan.buettiker@hftm.ch
     * @param city   - Get weather for this city, we can also use other ways to
     *               define the location but for this scope, we keep it with city
     */
    @GET
    Weather getWeatherByCity(
            @QueryParam("key") String apiKey,
            @QueryParam("q") String city);

}
