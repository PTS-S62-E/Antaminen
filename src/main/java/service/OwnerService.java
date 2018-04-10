package service;

import dao.OwnerDao;
import domain.Owner;
import domain.Ownership;
import exceptions.OwnerException;
import interfaces.service.IOwnerService;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

@Stateless
@LocalBean
public class OwnerService implements IOwnerService {

    public OwnerService() { }

    @EJB
    private OwnerDao ownerDao;

    @Override
    public void addOwnership(Owner owner, Ownership newOwnership) throws OwnerException {
        if(owner == null) { throw new OwnerException("Please provide an owner"); }
        if(newOwnership == null) { throw new OwnerException("Please provide a new ownership"); }

        ownerDao.addOwnership(owner, newOwnership);
    }
}
