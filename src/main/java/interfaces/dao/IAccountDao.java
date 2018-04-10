package interfaces.dao;

import domain.Account;
import domain.Owner;
import exceptions.AccountException;

import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;

public interface IAccountDao {

    /**
     * Create a new login for an owner
     * @param email email address used for login
     * @param password password used for login
     * @param owner ID of the owner that needs to be connected to the login
     * @return bool is success, false (probably exception) when not success
     * @throws AccountException
     */
    boolean create(String email, String password, Owner owner) throws AccountException;

    /**
     * Check if the email and password combo is correct, return owner object used in app
     * @param email
     * @param password
     * @return
     * @throws AccountException
     */
    Owner login(String email, String password) throws AccountException;

    /**
     * Update the password for an account
     * @param email current email
     * @param oldPassword current password
     * @param newPassword new password
     * @return boolean is success
     * @throws AccountException thrown when account is not found, or email/password combo is incorrect
     */
    boolean updatePassword(String email, String oldPassword, String newPassword) throws AccountException;

    /**
     * Create a new account
     * @param account
     * @return whether success or not
     * @throws AccountException
     */
    boolean createAccount(Account account) throws AccountException;

    /**
     * Find an account based on the email address
     * @param email
     * @return
     * @throws AccountException
     */
    Account findAccountByEmailAddress(String email) throws AccountException;
}
