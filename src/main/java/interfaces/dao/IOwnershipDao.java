package interfaces.dao;

import domain.Owner;
import domain.Ownership;
import exceptions.OwnershipException;

import java.util.ArrayList;

public interface IOwnershipDao {

    /**
     * Create a new Ownership for an owner
     * @param ownership object to be created
     * @throws OwnershipException
     */
    void create(Ownership ownership) throws OwnershipException;

    /**
     * Find all ownerships for a vehicle based on the vehicleId
     * @param vehicleId ID of the vehicle
     * @return returns a list of all found ownerships
     * @throws OwnershipException
     */
    ArrayList<Ownership> findOwnershipByVehicleId(long vehicleId) throws OwnershipException;
}
