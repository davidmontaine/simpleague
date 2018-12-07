package com.simpleague.user;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@Entity
@Table(name = "user", schema = "simpleague")
@NamedQueries({
    @NamedQuery(name = "User.FindByEmail", query = "SELECT u FROM User u WHERE u.credentials.email = :email"),
    @NamedQuery(name = "User.FindByUuid", query = "SELECT u FROM User u WHERE u.uuid = :uuid")        
})
@XmlRootElement
@XmlType(name = "")
public class User implements Serializable {
    private static final long serialVersionUID = 1L;            
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    
    @Column(name = "name")    
    private String name;    

    @Embedded
    private Credentials credentials = new Credentials();
    
    @Column(name = "group_name", insertable = false, updatable = false)    
    private String groupName;

    @Column(name = "uuid")
    private String uuid = UUID.randomUUID().toString(); 
    
    @Column(name = "verified", insertable = false)
    private String verified;    
    
    @Column(name = "email_count", insertable = false)    
    private Integer emailCount;    
    
    @Column(name = "password_count", insertable = false)    
    private Integer passwordCount;    

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

    public Credentials getCredentials() {
        return credentials;
    }

    public void setCredentials(Credentials credentials) {
        this.credentials = credentials;
    }
    
    public Integer getEmailCount() {
        return emailCount;
    }

    public void setEmailCount(Integer emailCount) {
        this.emailCount = emailCount;
    }    

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
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

    public Integer getPasswordCount() {
        return passwordCount;
    }

    public void setPasswordCount(Integer passwordCount) {
        this.passwordCount = passwordCount;
    }        

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }    

    public String getVerified() {
        return verified;
    }

    public void setVerified(String verified) {
        this.verified = verified;
    }        
    
    public int getVersion() {
        return version;
    }
    
    public void resetEmailRelated() {
        verified = "N";
        emailCount = 0;
        passwordCount = 0;
        uuid = UUID.randomUUID().toString();
    }        
}
