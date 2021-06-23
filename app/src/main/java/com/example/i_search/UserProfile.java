package com.example.i_search;

public class UserProfile {
    public String userPAge;
    public String userPEmail;
    public String userPName;

    public UserProfile()
    {

    }

    public UserProfile(String userPAge, String userPEmail, String userPName) {
        this.userPAge = userPAge;
        this.userPEmail = userPEmail;
        this.userPName = userPName;
    }

    public String getUserPAge() {
        return userPAge;
    }

    public void setUserPAge(String userPAge) {
        this.userPAge = userPAge;
    }

    public String getUserPEmail() {
        return userPEmail;
    }

    public void setUserPEmail(String userPEmail) {
        this.userPEmail = userPEmail;
    }

    public String getUserPName() {
        return userPName;
    }

    public void setUserPName(String userPName) {
        this.userPName = userPName;
    }
}
