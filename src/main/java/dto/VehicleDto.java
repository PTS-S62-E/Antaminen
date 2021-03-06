package dto;

import java.io.Serializable;

public class VehicleDto implements Serializable {

	private long id;
	private String countryCode;
	private String licensePlate;
	private String brand;
	private String type;
	private String category;
	private String serialNumber;

	public VehicleDto(){}

	public VehicleDto(long id, String countryCode, String licensePlate, String brand, String type, String category, String hardwareSn) {
		this.id = id;
		this.countryCode = countryCode;
		this.licensePlate = licensePlate;
		this.brand = brand;
		this.type = type;
		this.category = category;
		this.serialNumber = hardwareSn;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
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
		return serialNumber;
	}

	public void setHardwareSn(String hardwareSn) {
		this.serialNumber = hardwareSn;
	}

	public String getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}
}

