package dao;

import domain.Owner;
import domain.Ownership;
import exceptions.OwnerException;
import exceptions.OwnershipException;
import interfaces.dao.IOwnerDao;
import io.sentry.Sentry;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Stateless
@LocalBean
public class OwnerDao implements IOwnerDao {

    @PersistenceContext(unitName = "administratieUnit")
    EntityManager em;

    @EJB
    private OwnershipDao ownershipDao;

    public OwnerDao() { }

    @Override
    public Owner getOwnerById(long id) throws OwnerException {
        return null;
    }

    @Override
    public void addOwnership(Owner owner, Ownership newOwnership) throws OwnerException {
        if(owner == null) { throw new OwnerException("Please provide an owner"); }
        if(newOwnership == null) { throw new OwnerException("Please provide the new ownership"); }

        try {
            ownershipDao.create(newOwnership);
            owner.setOwnership(newOwnership);

            em.merge(owner);
        } catch (OwnershipException e) {
            throw new OwnerException(e.getMessage());
        }
    }

    @Override
    public List<Owner> getAllOwners() throws OwnerException {
        try {
            Query q = em.createNamedQuery("owner.findAllOwners");

            return (List<Owner>) q.getResultList();
        } catch(Exception e) {
            Sentry.capture(e);
            throw new OwnerException(e.getMessage());
        }
    }
}
