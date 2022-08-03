package ch.hftm.api.logic;

import javax.enterprise.context.ApplicationScoped;

import org.eclipse.microprofile.rest.client.inject.RestClient;

import ch.hftm.api.models.Window;
import ch.hftm.api.models.enums.StateType;

@ApplicationScoped
public class WeatherService {

    @RestClient
    WeatherApi weatherApi;

    private final String API_KEY = "b6dc63b8d3dd444caed124552220208";
    private final String CITY = "Zurich";

    public void updateWindowStatus(Window w) {

        if (weatherApi.getWeatherByCity(API_KEY, CITY).current.condition.text.contains("rain")
                && Boolean.TRUE.equals(w.weatherAware)) {

            w.desiredState = StateType.CLOSED;
        }
    }

}
