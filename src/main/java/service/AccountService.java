package service;

import dao.AccountDao;
import domain.Owner;
import exceptions.AccountException;
import interfaces.service.IAccountService;

import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
public class AccountService implements IAccountService {

    @Inject
    AccountDao accountDao;

    @Override
    public Owner login(String email, String password) throws AccountException {
        if(email.isEmpty()) { throw new AccountException("Please provide an email address."); }
        if(password.isEmpty()) { throw new AccountException("Please provide a password"); }

        return accountDao.login(email, password);
    }
}
