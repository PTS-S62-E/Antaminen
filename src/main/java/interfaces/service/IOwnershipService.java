package interfaces.service;

import domain.Ownership;
import exceptions.OwnershipException;

import java.util.ArrayList;

public interface IOwnershipService {

    ArrayList<Ownership> findOwnershipByVehicleId(long vehicleId) throws OwnershipException;
}
