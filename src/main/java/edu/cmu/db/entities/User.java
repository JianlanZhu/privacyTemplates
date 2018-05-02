package edu.cmu.db.entities;

import javax.persistence.*;
import java.security.Principal;
import java.time.Instant;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "user")
@NamedQueries({
        @NamedQuery(name = "edu.cmu.db.entities.User.findAll",
                query = "select u from User u"),
        @NamedQuery(name = "edu.cmu.db.entities.User.findByUsername",
                query = "select u from User u where u.userName = :userName")
})
public class User implements Principal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userID;

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

    @Column(name = "salt")
    private String salt;

    @Column(name = "accountEnabled")
    private boolean accountEnabled;

    @Column(name = "userCreatedDate")
    private Instant userCreatedDate;

    @OneToMany//(fetch = FetchType.EAGER)
    @JoinColumn(name = "createdByID")
    private List<Request> requests;

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    // TODO might want to change to multiple flags, so multiple roles per user are possible
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

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public List<Request> getRequests() {
        return requests;
    }

    public void setRequests(List<Request> requests) {
        this.requests = requests;
    }

    @Override
    public String getName() {
        return userName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return userID == user.userID &&
                accountEnabled == user.accountEnabled &&
                Objects.equals(userType, user.userType) &&
                Objects.equals(lastName, user.lastName) &&
                Objects.equals(firstName, user.firstName) &&
                Objects.equals(userName, user.userName) &&
                Objects.equals(userCreatedDate, user.userCreatedDate);
    }

    @Override
    public int hashCode() {

        return Objects.hash(userID, userType, lastName, firstName, userName, accountEnabled, userCreatedDate);
    }
}
