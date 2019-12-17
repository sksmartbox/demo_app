
package com.sampra.data.model.contactUs;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ContactDetail {

    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("weburl")
    @Expose
    private String weburl;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("contact1")
    @Expose
    private String contact1;
    @SerializedName("contact2")
    @Expose
    private String contact2;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getWeburl() {
        return weburl;
    }

    public void setWeburl(String weburl) {
        this.weburl = weburl;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContact1() {
        return contact1;
    }

    public void setContact1(String contact1) {
        this.contact1 = contact1;
    }

    public String getContact2() {
        return contact2;
    }

    public void setContact2(String contact2) {
        this.contact2 = contact2;
    }

}
