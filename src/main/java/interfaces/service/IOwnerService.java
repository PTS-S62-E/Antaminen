package interfaces.service;

import domain.Owner;
import domain.Ownership;
import exceptions.OwnerException;

import java.util.List;

public interface IOwnerService {

    void addOwnership(Owner owner, Ownership newOwnership) throws OwnerException;

    List<Owner> getAllOwners() throws OwnerException;
}
