package edu.cmu.db.entities;

import javax.persistence.*;

@Entity
@Table(name = "usertype")
@NamedQueries({
        @NamedQuery(name = "edu.cmu.db.entities.UserType.findAll",
                query = "select ut from usertype ut")
})
public class UserType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long userTypeID;

    @Column(name = "userTypeName")
    private String userTypeName;

    @Column(name = "userTypeDescription")
    private String userTypeDescription;

    public UserType() {
    }

    public UserType(String userTypeName, String userTypeDescription) {
        this.userTypeName = userTypeName;
        this.userTypeDescription = userTypeDescription;
    }

    public long getUserTypeID() {
        return userTypeID;
    }

    public void setUserTypeID(long userTypeID) {
        this.userTypeID = userTypeID;
    }

    public String getUserTypeName() {
        return userTypeName;
    }

    public void setUserTypeName(String userTypeName) {
        this.userTypeName = userTypeName;
    }

    public String getUserTypeDescription() {
        return userTypeDescription;
    }

    public void setUserTypeDescription(String userTypeDescription) {
        this.userTypeDescription = userTypeDescription;
    }
}
