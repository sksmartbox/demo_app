package com.sampra.data.model.about_us;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Result {

    @SerializedName("about_us_more_content")
    @Expose
    private String aboutUsMoreContent;

    public String getAboutUsMoreContent() {
        return aboutUsMoreContent;
    }

    public void setAboutUsMoreContent(String aboutUsMoreContent) {
        this.aboutUsMoreContent = aboutUsMoreContent;
    }

}
