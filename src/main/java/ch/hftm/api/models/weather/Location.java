package ch.hftm.api.models.weather;

/**
 * Location information from https://weatherapi.com
 */
public class Location {
    public String name;
    public String region;
    public String country;
    public double lat;
    public double lon;
    public String tz_id;
    public int localtime_epoch;
    public String localtime;
}
