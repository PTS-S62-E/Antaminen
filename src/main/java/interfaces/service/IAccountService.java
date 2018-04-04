package interfaces.service;

import domain.Account;
import domain.Owner;
import exceptions.AccountException;

import javax.ejb.LocalBean;
import javax.ejb.Remote;
import javax.ejb.Stateless;

public interface IAccountService {

    Owner login(String email, String password) throws AccountException;

    String generateJWT(String email);

    void createAccount(Account account) throws AccountException;

    Account findByEmailAddress(String email) throws AccountException;
}
