package interfaces.service;

import domain.Owner;
import exceptions.AccountException;

public interface IAccountService {

    Owner login(String email, String password) throws AccountException;
}
