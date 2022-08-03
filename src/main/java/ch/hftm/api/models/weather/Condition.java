package ch.hftm.api.models.weather;

/**
 * Weather condition from https://weatherapi.com
 * This is actually the only thing of interest as of now.
 */
public class Condition {
    public String text;
    public String icon;
    public int code;
}
