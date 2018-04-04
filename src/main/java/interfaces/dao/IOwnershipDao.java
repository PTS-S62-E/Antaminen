package interfaces.dao;

import domain.Ownership;
import exceptions.OwnershipException;

public interface IOwnershipDao {

    void create(Ownership ownership) throws OwnershipException;
}
