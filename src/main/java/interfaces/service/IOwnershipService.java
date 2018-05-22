package interfaces.service;

import domain.Account;
import domain.Ownership;
import dto.OwnershipWithVehicleDto;
import exceptions.CommunicationException;
import exceptions.OwnershipException;

import java.io.IOException;
import java.util.ArrayList;

public interface IOwnershipService {

    OwnershipWithVehicleDto findOwnershipByLicensePlate(String licensePlate) throws OwnershipException, CommunicationException, IOException;
    ArrayList<Ownership> findOwnershipByVehicleId(long vehicleId) throws OwnershipException;
    ArrayList<Ownership> getFatOwnerships(Account account) throws OwnershipException;
}
