package interfaces.dao;

import domain.Owner;
import exceptions.OwnerException;

import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;


public interface IOwnerDao {

    Owner getOwnerById(long id) throws OwnerException;
}
