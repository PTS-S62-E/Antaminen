package dto;
import domain.Ownership;
import domain.TariffCategory;

import java.io.Serializable;
import java.util.List;

public class OwnershipWithVehicleDto implements Serializable{

	private List<Ownership> ownership;
	private VehicleDto vehicleDto;

	public TariffCategory getTariffCategory() {
		return tariffCategory;
	}

	public void setTariffCategory(TariffCategory tariffCategory) {
		this.tariffCategory = tariffCategory;
	}

	private TariffCategory tariffCategory;

	public List<Ownership> getOwnership() {
		return ownership;
	}

	public void setOwnership(List<Ownership> ownership) {
		this.ownership = ownership;
	}

	public VehicleDto getVehicleDto() {
		return vehicleDto;
	}

	public void setVehicleDto(VehicleDto vehicleDto) {
		this.vehicleDto = vehicleDto;
	}

	public OwnershipWithVehicleDto(){}

	public OwnershipWithVehicleDto(List<Ownership> ownership, VehicleDto vehicleDto, TariffCategory tariffCategory) {
		this.tariffCategory = tariffCategory;
		this.ownership = ownership;
		this.vehicleDto = vehicleDto;
	}
}
