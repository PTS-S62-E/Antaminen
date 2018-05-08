package service;

import com.pts62.common.facade.VehicleFacade;
import communication.RegistrationMovement;
import domain.TariffCategory;
import exceptions.CommunicationException;
import exceptions.TariffCategoryException;
import interfaces.dao.ITariffCategoryDao;
import interfaces.service.ITariffCategoryService;
import javax.inject.Inject;
import java.io.IOException;
import java.util.List;

public class TariffCategoryService implements ITariffCategoryService {

	@Inject
	ITariffCategoryDao tariffCategoryDao;

	@Override
	public TariffCategory getTariffCategory(String name) throws TariffCategoryException {
		if (name == null || name.equals("")){
			throw new TariffCategoryException("name cannot be empty");
		}
		return tariffCategoryDao.getTariffCategory(name);
	}

	@Override
	public List<TariffCategory> getTariffCategories() {
		return tariffCategoryDao.getTariffCategories();
	}

	@Override
	public void createTariffCategory(TariffCategory tariffCategory) throws TariffCategoryException {
		if (tariffCategoryDao.getTariffCategory(tariffCategory.getName()) != null){
			StringBuilder builder = new StringBuilder();
			builder.append("TariffCategory: ");
			builder.append(tariffCategory.getName());
			builder.append(", already exists.");
			throw new TariffCategoryException(builder.toString());
		}

		if (tariffCategory.getTariff() <= 0){
			throw new TariffCategoryException("Please use a tariff above 0");
		}

		//Uppercase the given name
		tariffCategory.setName(tariffCategory.getName().toUpperCase());

		tariffCategoryDao.createTariffCategory(tariffCategory);
	}


	public TariffCategory getTariffCategoryByVehicleId(long vehicleId) throws CommunicationException, IOException {
		RegistrationMovement rm = RegistrationMovement.getInstance();
		VehicleFacade vehicle = rm.getVehicleById(vehicleId);

	}
}
