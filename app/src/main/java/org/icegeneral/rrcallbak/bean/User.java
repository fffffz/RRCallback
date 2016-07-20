package org.icegeneral.rrcallbak.bean;

/**
 * Created by iceGeneral on 16/7/20.
 */

public class User {

    private String username;
    private String nickname;
    private boolean isMale;


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public boolean isMale() {
        return isMale;
    }

    public void setMale(boolean male) {
        isMale = male;
    }
}
