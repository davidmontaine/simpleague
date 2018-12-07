package com.simpleague.league;

import com.simpleague.user.User;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@Entity
@Table(name = "league", schema = "simpleague")
@NamedQueries({
    @NamedQuery(name = "League.FindByName", query = "SELECT l FROM League l WHERE l.name = :name"),
    @NamedQuery(name = "League.FindByUserEmail", query = "SELECT l FROM League l WHERE l.user.credentials.email = :userEmail"),
    @NamedQuery(name = "League.FindByUserId", query = "SELECT l FROM League l WHERE l.user.id = :userId")
})
@XmlRootElement
@XmlType(name = "")
public class League implements Serializable {
    private static final long serialVersionUID = 1L;            
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    
    @Column(name = "name")    
    private String name;    

    @ManyToOne
    private User user;
    
    @Version
    @Column(name = "version")
    private int version;
    
    @Column(name = "created_date", insertable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)    
    private Date createdDate;
    
    @Column(name = "modified_date", insertable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date modifiedDate;
    
    public Date getCreatedDate() {
        return createdDate;
    }
    
    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(Date modifiedDate) {
        this.modifiedDate = modifiedDate;
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }            

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }                
    
    public int getVersion() {
        return version;
    }
}
