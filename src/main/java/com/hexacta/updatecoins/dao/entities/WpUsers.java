package com.hexacta.updatecoins.dao.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "wp_users")
public class WpUsers {

    @Id
    @Column(name = "ID")
    Long id;

    @Column(name = "display_name")
    String displayName;

    @Column(name = "user_email")
    String userEmail;

    public Long getId() {
        return id;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    @Override
    public String toString() {
//String.format("id: %d, displayName: %s, userEmai: %s\n", id, displayName, userEmail);
        return String.format("%d %30s %30s\n", id, displayName, userEmail);
    }
}
