package dto;

import java.io.Serializable;
import java.util.List;

public class ForeignVehicleDto implements Serializable {

    private long id;
 	private String licensePlate;
 	private String brand;
 	private String type;
 	private String category;
 	private String hardwareSn;

 	private String countryCode;
 	private List<JourneyDto> journeys;

 	public ForeignVehicleDto() { }

    public ForeignVehicleDto(VehicleDto vehicle, List<JourneyDto> journeys){
        this.id = vehicle.getId();
        this.licensePlate = vehicle.getLicensePlate();
        this.brand = vehicle.getBrand();
        this.type = vehicle.getType();
        this.category = vehicle.getCategory();
        this.hardwareSn = vehicle.getHardwareSn();
        this.journeys = journeys;
        this.countryCode = vehicle.getCountryCode();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getHardwareSn() {
        return hardwareSn;
    }

    public void setHardwareSn(String hardwareSn) {
        this.hardwareSn = hardwareSn;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public List<JourneyDto> getJourneys() {
        return journeys;
    }

    public void setJourneys(List<JourneyDto> journeys) {
        this.journeys = journeys;
    }
}
