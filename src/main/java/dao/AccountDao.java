package dao;

import domain.Account;
import domain.Owner;
import exceptions.AccountException;
import interfaces.dao.IAccountDao;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
@LocalBean
public class AccountDao implements IAccountDao {

    @PersistenceContext(unitName = "administratieUnit")
    EntityManager em;

    public AccountDao() { }

    @Override
    public boolean create(String email, String password, Owner owner) throws AccountException {
        Account account = new Account(email, password, owner);

        return false;
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
}
