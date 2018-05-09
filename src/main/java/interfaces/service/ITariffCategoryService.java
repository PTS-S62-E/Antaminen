package interfaces.service;

import domain.TariffCategory;
import exceptions.CategoryException;
import exceptions.CommunicationException;
import exceptions.TariffCategoryException;
import java.io.IOException;
import java.util.List;

public interface ITariffCategoryService {
	TariffCategory getTariffCategory(String name) throws TariffCategoryException;
	List<TariffCategory> getTariffCategories();
	void checkAndCreateTariffCategory(TariffCategory tariffCategory) throws TariffCategoryException, IOException, CategoryException, CommunicationException;
	TariffCategory getTariffCategoryByVehicleId(long vehicleId) throws CommunicationException, IOException, TariffCategoryException;
}
