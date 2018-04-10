package service;

import dao.OwnershipDao;
import domain.Owner;
import domain.Ownership;
import exceptions.OwnerException;
import exceptions.OwnershipException;
import interfaces.dao.IOwnershipDao;
import interfaces.service.IOwnerService;
import interfaces.service.IOwnershipService;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import java.util.ArrayList;

@Stateless
@LocalBean
public class OwnershipService implements IOwnershipService {

    @EJB
    private OwnershipDao ownershipDao;

    @Override
    public ArrayList<Ownership> findOwnershipByVehicleId(long vehicleId) throws OwnershipException {
        if(vehicleId < 1) { throw new OwnershipException("Please provide a vehicleId"); }

        return ownershipDao.findOwnershipByVehicleId(vehicleId);
    }
}
