package domain;

import com.rekeningrijden.europe.interfaces.ITransLocation;
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

    private long id;
    private Double lat;
    private Double lon;
    private String dateTime;
    private String serialnumber;
    private String countryCode;

    public TransLocation() { }

    public TransLocation(Double lat, Double lon, String dateTime, String serialNumber, String countryCode) {
        this.lat = lat;
        this.lon = lon;
        this.dateTime = dateTime;
        this.serialnumber = serialNumber;
        this.countryCode = countryCode;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public void setSerialnumber(String serialNumber) {
        this.serialnumber = serialNumber;
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
    public String getTime() {
        return this.dateTime;
    }

    @NotNull
    @Override
    public String getSerialNumber() {
        return this.serialnumber;
    }

    @NotNull
    @Override
    public String getCountryCode() {
        return this.countryCode;
    }

    public void setLon(Double lon) {
        this.lon = lon;
    }

    public String getDateTime() {
        return dateTime;
    }
}
