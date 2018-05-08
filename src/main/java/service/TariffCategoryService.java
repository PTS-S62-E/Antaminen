package service;

import communication.RegistrationMovement;
import domain.TariffCategory;
import dto.VehicleDto;
import exceptions.CommunicationException;
import exceptions.TariffCategoryException;
import interfaces.dao.ITariffCategoryDao;
import interfaces.service.ITariffCategoryService;
import io.sentry.Sentry;
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

	@Override
	public TariffCategory getTariffCategoryByVehicleId(long vehicleId) throws CommunicationException, IOException, TariffCategoryException {
		RegistrationMovement rm = RegistrationMovement.getInstance();
		VehicleDto vehicleDto = rm.getVehicleById(vehicleId);
		String categoryName = vehicleDto.getCategory();
		TariffCategory tariffCategory = tariffCategoryDao.getTariffCategory(categoryName);

		if (tariffCategory == null){
			StringBuilder builder = new StringBuilder();
			builder.append("couldn't find a TariffCategory with name: ");
			builder.append(categoryName);
			builder.append(". There's a discrepancy between the registration and administration category tables. Please contact an administrator.");
			Sentry.capture(builder.toString());
			throw new TariffCategoryException(builder.toString());
		}

		return tariffCategory;
	}
}
