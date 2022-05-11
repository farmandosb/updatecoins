package com.hexacta.updatecoins.dto;

public class UserDTO {
    private Long id;
    private String initialPoints;
    private String displayName;
    private String userEmail;

    public UserDTO() {

    }

    public UserDTO(Long id, String initialPoints, String metaValue, String displayName, String userEmail) {
        this.id = id;
        this.initialPoints = initialPoints;
        this.displayName = displayName;
        this.userEmail = userEmail;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getInitialPoints() {
        return initialPoints;
    }

    public void setInitialPoints(String initialPoints) {
        this.initialPoints = initialPoints;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    @Override
    public String toString() {
        return String.format("%4d %40s %30s %10s\n", id, displayName, userEmail, initialPoints);
    }
}
