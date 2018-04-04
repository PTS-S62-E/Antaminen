package dao;

import domain.Account;
import domain.Owner;
import exceptions.AccountException;
import interfaces.dao.IAccountDao;
import io.sentry.Sentry;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Named;
import javax.persistence.*;

@Stateless
@LocalBean
public class AccountDao implements IAccountDao {

    @PersistenceContext(unitName = "administratieUnit")
    EntityManager em;

    public AccountDao() { }

    @Override
    public boolean create(String email, String password, Owner owner) throws AccountException {
        Account account = new Account(email, password, owner);

        throw new NotImplementedException();
    }

    @Override
    public Owner login(String email, String password) throws AccountException {
        Query q = em.createNamedQuery("Account.login");
        q.setParameter("email", email);
        q.setParameter("password", password);

        try {
            return ((Account) q.getSingleResult()).getOwner();
        } catch (NoResultException nre) {
            throw new AccountException("Authentication failed");
        }
    }

    @Override
    public boolean updatePassword(String email, String oldPassword, String newPassword) throws AccountException {
        Query q = em.createNamedQuery("Account.findAccountByEmail");
        q.setParameter("email", email);

        try {
            Account a = (Account) q.getSingleResult();

            if(!a.getPassword().equals(oldPassword)) {
                throw new AccountException("Password is incorrect");
            } else {
                a.setPassword(newPassword);
                em.merge(a);

                return true;
            }
        } catch (NoResultException nre) {
            throw new AccountException("Authentication failed");
        }
    }

    @Override
    public boolean createAccount(Account account) throws AccountException {
        if(account == null) { throw new AccountException("Please provide an account"); }
        if(account.getEmail().isEmpty()) { throw new AccountException("Please provide an email address"); }
        if(account.getPassword().isEmpty()) { throw new AccountException("Please provide a password"); }
        if(account.getOwner() == null) { throw new AccountException("Please provide an owner object"); }

        try {
            em.persist(account.getOwner());
            em.persist(account);
            return true;
        } catch(RollbackException re) {
            throw new AccountException(re.getMessage());
        } catch(Exception e) {
            Sentry.capture(e);
            throw new AccountException("Entity already exists. Check server log.");
        }
    }
}
