package service;

import communication.RegistrationMovement;
import dao.OwnershipDao;
import dao.TariffCategoryDao;
import domain.Account;
import domain.Ownership;
import domain.TariffCategory;
import dto.OwnershipWithVehicleDto;
import dto.VehicleDto;
import exceptions.CommunicationException;
import exceptions.OwnershipException;
import interfaces.dao.ITariffCategoryDao;
import interfaces.service.IOwnershipService;
import io.sentry.Sentry;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Stateless
@LocalBean
public class OwnershipService implements IOwnershipService {

    @EJB
    private OwnershipDao ownershipDao;

    @Inject
    private ITariffCategoryDao tariffCategoryDao;

    @Override
    public ArrayList<Ownership> findOwnershipByVehicleId(long vehicleId) throws OwnershipException {
        if(vehicleId < 1) { throw new OwnershipException("Please provide a vehicleId"); }

        return ownershipDao.findOwnershipByVehicleId(vehicleId);
    }

    @Override
    public ArrayList<Ownership> getFatOwnerships(Account account) throws OwnershipException {
        if(account == null) { throw new OwnershipException("Please provide an account"); }

        List<Ownership> ownerships = account.getOwner().getOwnership();
        ArrayList<Ownership> fatOwnerships =  new ArrayList<>();
        try {
            for (Ownership ownership : ownerships) {
                VehicleDto vehicle = RegistrationMovement.getInstance().getVehicleById(ownership.getVehicleId());
                Ownership o = ownership;
                o.setVehicleDto(vehicle);
                fatOwnerships.add(o);
            }
        } catch (CommunicationException | IOException e) {
            Sentry.capture(e);
        }

        return fatOwnerships;
    }

    @Override
    public OwnershipWithVehicleDto findOwnershipByLicensePlate(String licensePlate) throws OwnershipException, CommunicationException, IOException {


        if(licensePlate == null || licensePlate.equals("")) { throw new OwnershipException("Please provide a licensePlate"); }

        RegistrationMovement registrationMovement = RegistrationMovement.getInstance();
        VehicleDto vehicleDto = registrationMovement.getVehicleByLicensePlate(licensePlate);
        List<Ownership> ownership = ownershipDao.findOwnershipByVehicleId(vehicleDto.getId());
        TariffCategory tariffCategory = tariffCategoryDao.getTariffCategory(vehicleDto.getCategory());

        return new OwnershipWithVehicleDto(ownership, vehicleDto, tariffCategory);
    }
}
