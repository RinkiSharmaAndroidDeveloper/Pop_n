package com.popn.PopModels;

import java.io.Serializable;

/**
 * Created by Vs1 on 3/27/2018.
 */

public class IdentityInformationModel implements Serializable {
    String IdentityId,IdentityName,IdentityUserName,IdentityUserAge,IdentityCity,IdentityUserId,IdentityColor,IdentityUserImage;

    public IdentityInformationModel(String identityId, String identityName, String identityUserName, String identityUserAge, String identityCity, String identityUserId, String identityColor, String identityUserImage) {
        IdentityId = identityId;
        IdentityName = identityName;
        IdentityUserName = identityUserName;
        IdentityUserAge = identityUserAge;
        IdentityCity = identityCity;
        IdentityUserId = identityUserId;
        IdentityColor = identityColor;
        IdentityUserImage = identityUserImage;
    }
    public IdentityInformationModel() {

    }

    public String getIdentityId() {
        return IdentityId;
    }

    public void setIdentityId(String identityId) {
        IdentityId = identityId;
    }

    public String getIdentityName() {
        return IdentityName;
    }

    public void setIdentityName(String identityName) {
        IdentityName = identityName;
    }

    public String getIdentityUserName() {
        return IdentityUserName;
    }

    public void setIdentityUserName(String identityUserName) {
        IdentityUserName = identityUserName;
    }

    public String getIdentityUserAge() {
        return IdentityUserAge;
    }

    public void setIdentityUserAge(String identityUserAge) {
        IdentityUserAge = identityUserAge;
    }

    public String getIdentityCity() {
        return IdentityCity;
    }

    public void setIdentityCity(String identityCity) {
        IdentityCity = identityCity;
    }

    public String getIdentityUserId() {
        return IdentityUserId;
    }

    public void setIdentityUserId(String identityUserId) {
        IdentityUserId = identityUserId;
    }

    public String getIdentityColor() {
        return IdentityColor;
    }

    public void setIdentityColor(String identityColor) {
        IdentityColor = identityColor;
    }

    public String getIdentityUserImage() {
        return IdentityUserImage;
    }

    public void setIdentityUserImage(String identityUserImage) {
        IdentityUserImage = identityUserImage;
    }
}
