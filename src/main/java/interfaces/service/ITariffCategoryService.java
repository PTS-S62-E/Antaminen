package interfaces.service;

import domain.TariffCategory;
import exceptions.CommunicationException;
import exceptions.TariffCategoryException;
import java.io.IOException;
import java.util.List;

public interface ITariffCategoryService {
	TariffCategory getTariffCategory(String name) throws TariffCategoryException;
	List<TariffCategory> getTariffCategories();
	void createTariffCategory(TariffCategory tariffCategory) throws TariffCategoryException;
	TariffCategory getTariffCategoryByVehicleId(long vehicleId) throws CommunicationException, IOException, TariffCategoryException;
}
