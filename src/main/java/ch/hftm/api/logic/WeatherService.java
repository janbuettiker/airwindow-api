package ch.hftm.api.logic;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.eclipse.microprofile.rest.client.inject.RestClient;

import ch.hftm.api.models.Window;
import ch.hftm.api.models.enums.StateType;

@ApplicationScoped
public class WeatherService {

    @Inject
    @RestClient
    WeatherApi weatherApi;

    private final String API_KEY = "b6dc63b8d3dd444caed124552220208";
    private final String CITY = "Zurich";

    /**
     * For now the only function in this service.
     * Will close the window if the weather in Zurich has anything to do with rain.
     * 
     * We only update the state if the window has the weatherAware trigger set to
     * true.
     * 
     * @param w - Window that we want to update the status with.
     */
    public void updateWindowStatus(Window w) {

        if (weatherApi.getWeatherByCity(API_KEY, CITY).current.condition.text.contains("rain")
                && Boolean.TRUE.equals(w.weatherAware)) {

            w.desiredState = StateType.CLOSED;
        }
    }

}
