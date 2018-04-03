package domain;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "Account")
@NamedQueries({
        @NamedQuery(name = "Account.login", query = "SELECT a FROM Account a WHERE a.email = :email AND a.password = :password"),
        @NamedQuery(name = "Account.updatePassword", query = "UPDATE Account a SET a.password = :password WHERE a.email = :email"),
        @NamedQuery(name = "Account.findAccountByEmail", query = "SELECT a FROM Account a WHERE a.email = :email")
})
public class Account implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "password")
    private String password;

    @OneToOne
    @JoinColumn(name = "owner_id")
    private Owner owner;

    public Account() { }

    public Account(String email, String password, Owner owner) {
        this.email = email;
        this.password = password;
        this.owner = owner;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Owner getOwner() {
        return owner;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }
}
