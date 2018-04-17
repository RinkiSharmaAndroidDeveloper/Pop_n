package com.popn.PopModels;

import java.io.Serializable;

/**
 * Created by Vs1 on 4/3/2018.
 */

public class SocialIdModel implements Serializable {
    String socialId,socialUrl;

    public SocialIdModel(String socialId, String socialUrl) {
        this.socialId = socialId;
        this.socialUrl = socialUrl;
    }

    public String getSocialId() {
        return socialId;
    }

    public void setSocialId(String socialId) {
        this.socialId = socialId;
    }

    public String getSocialUrl() {
        return socialUrl;
    }

    public void setSocialUrl(String socialUrl) {
        this.socialUrl = socialUrl;
    }
}
