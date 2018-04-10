package service;

import dao.AccountDao;
import domain.Account;
import domain.Owner;
import exceptions.AccountException;
import interfaces.service.IAccountService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.crypto.MacProvider;
import org.apache.commons.lang3.time.DateUtils;
import util.KeyGenerator;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;
import java.security.Key;
import java.util.Date;

@Stateless
@LocalBean
public class AccountService implements IAccountService {

    @EJB
    AccountDao accountDao;

    public AccountService() { }

    @Override
    public Owner login(String email, String password) throws AccountException {
        if(email.isEmpty()) { throw new AccountException("Please provide an email address."); }
        if(password.isEmpty()) { throw new AccountException("Please provide a password"); }

        return accountDao.login(email, password);
    }

    @Override
    public String generateJWT(String email) {
        Key key = KeyGenerator.getInstance().getKey();
        Date date = new Date();
        String jwtToken = Jwts.builder()
                .setSubject(email)
                .setIssuedAt(date)
                .setExpiration(DateUtils.addMinutes(date, 30))
                .signWith(SignatureAlgorithm.HS512, key)
                .compact();
        return jwtToken;
    }

    @Override
    public void createAccount(Account account) throws AccountException {
        if(account == null) { throw new AccountException("Please provide an account"); }

        accountDao.createAccount(account);
    }

    @Override
    public Account findByEmailAddress(String email) throws AccountException {
        if(email.isEmpty()) { throw new AccountException("Please provide an email address"); }

        return accountDao.findAccountByEmailAddress(email);
    }
}
