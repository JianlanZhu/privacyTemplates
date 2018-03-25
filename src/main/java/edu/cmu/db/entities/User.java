package edu.cmu.db.entities;

import javax.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "user")
@NamedQueries({
        @NamedQuery(name = "edu.cmu.db.entities.User.findAll",
                query = "select u from User u")
})
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long userID;

    @Column(name = "userType")
    private String userType;

    @Column(name = "lastName")
    private String lastName;

    @Column(name = "firstName")
    private String firstName;

    @Column(name = "userName", unique = true)
    private String userName;

    @Column(name = "password")
    private String password;

    @Column(name = "accountEnabled")
    private boolean accountEnabled;

    @Column(name = "userCreatedDate")
    private Instant userCreatedDate;

    public long getUserID() {
        return userID;
    }

    public void setUserID(long userID) {
        this.userID = userID;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isAccountEnabled() {
        return accountEnabled;
    }

    public void setAccountEnabled(boolean accountEnabled) {
        this.accountEnabled = accountEnabled;
    }

    public Instant getUserCreatedDate() {
        return userCreatedDate;
    }

    public void setUserCreatedDate(Instant userCreatedDate) {
        this.userCreatedDate = userCreatedDate;
    }
}
