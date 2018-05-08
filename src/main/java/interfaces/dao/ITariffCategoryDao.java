package interfaces.dao;

import domain.TariffCategory;

import java.util.List;

public interface ITariffCategoryDao {
	TariffCategory getTariffCategory(String name);
	List<TariffCategory> getTariffCategories();
	void createTariffCategory(TariffCategory tariffCategory);
}
