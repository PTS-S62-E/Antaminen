package dao;

import domain.TariffCategory;
import interfaces.dao.ITariffCategoryDao;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

public class TariffCategoryDao implements ITariffCategoryDao {

	@Inject
	EntityManager em;

	@Override
	public TariffCategory getTariffCategory(String name) {
		return em.find(TariffCategory.class, name);
}

	@Override
	public List<TariffCategory> getTariffCategories() {
		TypedQuery<TariffCategory> query =
				em.createNamedQuery("TariffCategory.getAll", TariffCategory.class);
		return query.getResultList();
	}

	@Override
	public void createTariffCategory(TariffCategory tariffCategory) {
		em.persist(tariffCategory);
	}
}
