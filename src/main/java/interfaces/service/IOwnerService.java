package interfaces.service;

import domain.Owner;
import domain.Ownership;
import exceptions.OwnerException;

public interface IOwnerService {

    void addOwnership(Owner owner, Ownership newOwnership) throws OwnerException;
}
