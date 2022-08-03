package ch.hftm.api.models.weather;

/**
 * Root of the weather data json that we request from
 * https://weatherapi.com
 */
public class Weather {
    public Location location;
    public Current current;
}
