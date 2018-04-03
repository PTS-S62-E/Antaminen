package interfaces.service;

import domain.Owner;
import exceptions.AccountException;

import javax.ejb.LocalBean;
import javax.ejb.Remote;
import javax.ejb.Stateless;

public interface IAccountService {

    Owner login(String email, String password) throws AccountException;

    String generateJWT(String email);
}
