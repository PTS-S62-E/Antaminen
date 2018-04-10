package dao;

import domain.Ownership;
import exceptions.OwnershipException;
import interfaces.dao.IOwnerDao;
import interfaces.dao.IOwnershipDao;
import io.sentry.Sentry;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.*;
import java.util.ArrayList;

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

    @Override
    public ArrayList<Ownership> findOwnershipByVehicleId(long vehicleId) throws OwnershipException {
        if(vehicleId < 1) { throw new OwnershipException("Please provide a vehicleId"); }


        Query q = em.createNamedQuery("Ownership.findByVehicleId");
        q.setParameter("id", vehicleId);

        try {
            ArrayList<Ownership> result =  (ArrayList<Ownership>) q.getResultList();
            return result;
        } catch (NoResultException nre) {
            throw new OwnershipException("No data for vehicleId");
        } catch (Exception e) {
            Sentry.capture(e);
            throw new OwnershipException("Something went wrong. See error log.");
        }
    }
}
