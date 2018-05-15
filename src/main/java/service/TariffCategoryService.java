package service;

import communication.RegistrationMovement;
import domain.TariffCategory;
import dto.CategoryDto;
import dto.VehicleDto;
import exceptions.CategoryException;
import exceptions.CommunicationException;
import exceptions.TariffCategoryException;
import interfaces.dao.ITariffCategoryDao;
import interfaces.service.ITariffCategoryService;
import io.sentry.Sentry;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;
import java.io.IOException;
import java.util.List;

@Stateless
@LocalBean
public class TariffCategoryService implements ITariffCategoryService {

	@EJB
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
	public void checkAndCreateTariffCategory(TariffCategory tariffCategory) throws TariffCategoryException, IOException, CategoryException, CommunicationException {

		if(tariffCategory.getName().equals("")){
			throw new TariffCategoryException("name cannot be empty");
		}

		if (tariffCategory.getTariff() <= 0){
			throw new TariffCategoryException("Please use a tariff above 0");
		}

		//Uppercase the given name
		tariffCategory.setName(tariffCategory.getName().toUpperCase());

		RegistrationMovement rm = RegistrationMovement.getInstance();
		boolean existsAtRegistration = (rm.getCategory(tariffCategory.getName()) != null);
		boolean existsAtAdministration = (tariffCategoryDao.getTariffCategory(tariffCategory.getName()) != null);

		//Create either a tariff category, category, or both.
		createCategory(existsAtRegistration, existsAtAdministration, tariffCategory);
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

	/**
	 * Creates either a tariff category at the administration system,
	 * A category at the registration system, or both.
	 * Depending on the existence of these categories.
	 * @param existsAtRegistration
	 * @param existsAtAdministration
	 * @param tariffCategory
	 * @throws TariffCategoryException
	 * @throws CategoryException
	 */
	private void createCategory(boolean existsAtRegistration, boolean existsAtAdministration, TariffCategory tariffCategory) throws TariffCategoryException, CategoryException {
		if (existsAtAdministration && existsAtRegistration){
			StringBuilder builder = new StringBuilder();
			builder.append("TariffCategory & Category: ");
			builder.append(tariffCategory.getName());
			builder.append(", already exist.");
			throw new TariffCategoryException(builder.toString());
		}

		if (existsAtAdministration && !existsAtRegistration){
			try{
				RegistrationMovement rm = RegistrationMovement.getInstance();
				rm.createCategory(new CategoryDto(tariffCategory.getName(), tariffCategory.getDescription()));
			}
			catch (Exception e){
				StringBuilder builder = new StringBuilder();
				builder.append("Tried adding category: ");
				builder.append(tariffCategory.getName());
				builder.append(" to the registration system, but something went wrong.");
				builder.append(" Please try again later, or contact a system administrator.");
				Sentry.capture(builder.toString());
				throw new CategoryException(builder.toString());
			}
		}

		if(!existsAtAdministration && existsAtRegistration){
			try{
				tariffCategoryDao.createTariffCategory(tariffCategory);
			}
			catch (Exception e){
				StringBuilder builder = new StringBuilder();
				builder.append("Tried adding tariff category: ");
				builder.append(tariffCategory.getName());
				builder.append(" to the administration system, but something went wrong.");
				builder.append(" Please try again later, or contact a system administrator.");
				Sentry.capture(builder.toString());
				throw new TariffCategoryException(builder.toString());
			}
		}

		if(!existsAtAdministration && !existsAtRegistration){
			try{
				tariffCategoryDao.createTariffCategory(tariffCategory);
				RegistrationMovement rm = RegistrationMovement.getInstance();
				rm.createCategory(new CategoryDto(tariffCategory.getName(), tariffCategory.getDescription()));
			}
			catch (Exception e){
				StringBuilder builder = new StringBuilder();
				builder.append("Tried adding tariff category & category: ");
				builder.append(tariffCategory.getName());
				builder.append(" to the administration and registration system, but something went wrong.");
				builder.append(" Please try again later, or contact a system administrator.");
				Sentry.capture(builder.toString());
				throw new TariffCategoryException(builder.toString());
			}
		}
	}
}
