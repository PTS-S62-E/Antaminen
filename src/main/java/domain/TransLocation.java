package domain;

import com.pts62.common.europe.ITransLocation;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;

/**
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 * | Created by juleskreutzer
 * | Date: 20-03-18
 * |
 * | Project Info:
 * | Project Name: RekeningAdministratie
 * | Project Package Name: domain
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 */
public class TransLocation implements ITransLocation, Serializable {

    private Double lat;
    private Double lon;
    private String dateTime;
    private String serialNumber;
    private String countryCode;

    public TransLocation() { }

    public TransLocation(Double lat, Double lon, String dateTime, String serialNumber, String countryCode) {
        this.lat = lat;
        this.lon = lon;
        this.dateTime = dateTime;
        this.serialNumber = serialNumber;
        this.countryCode = countryCode;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    @Override
    public Double getLat() {
        return this.lat;
    }

    @Override
    public Double getLon() {
        return this.lon;
    }

    @NotNull
    @Override
    public String getTime() {
        return this.dateTime;
    }

    @NotNull
    @Override
    public String getSerialnumber() {
        return this.serialNumber;
    }

    @NotNull
    @Override
    public String getCountrycode() {
        return this.countryCode;
    }

    public void setLon(Double lon) {
        this.lon = lon;
    }

    public String getDateTime() {
        return dateTime;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public String getCountryCode() {
        return countryCode;
    }
}
