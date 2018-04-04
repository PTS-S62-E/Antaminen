package dao;

import domain.Ownership;
import exceptions.OwnershipException;
import interfaces.dao.IOwnerDao;
import interfaces.dao.IOwnershipDao;
import io.sentry.Sentry;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.RollbackException;

@Stateless
@LocalBean
public class OwnershipDao implements IOwnershipDao {

    @PersistenceContext(unitName = "administratieUnit")
    EntityManager em;

    public OwnershipDao() { }

    @Override
    public void create(Ownership ownership) throws OwnershipException {
        if(ownership == null) { throw new OwnershipException("Please provide an ownership"); }

        try {
            em.persist(ownership);
        } catch (Exception e) {
            Sentry.capture(e);
            throw new OwnershipException("Something went wrong");
        }

    }
}
