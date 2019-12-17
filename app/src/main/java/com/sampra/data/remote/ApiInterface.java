package com.sampra.data.remote;
import com.sampra.data.model.ContactFormModel;
import com.sampra.data.model.about_us.AboutUsModel;
import com.sampra.data.model.contactUs.ContactUsModel;
import com.sampra.data.model.notification.DeviceTokenModel;
import com.sampra.data.model.profile_update.UpdateProfileModel;

import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;


public interface ApiInterface {
    @GET("about_us")
    Call<AboutUsModel> getAboutUsDetails();

    @GET("contact_us")
    Call<ContactUsModel> getContactUsDetails();

    @Multipart
    @POST("contact_form")
    Call<ContactFormModel> getContactFormDetails(@Part MultipartBody.Part file, @Part("name") RequestBody name, @Part("email") RequestBody email, @Part("phone_number") RequestBody phone_number,
                                                @Part("message") RequestBody message, @Part("query_type") RequestBody query_type);

    @Multipart
    @POST("add_chatuser")
    Call<UpdateProfileModel> getProfileUpdate(@Part MultipartBody.Part file, @Part("name") RequestBody name, @Part("email") RequestBody email, @Part("contact_number") RequestBody phone_number);

    @Multipart
    @POST("add_chatuser")
    Call<UpdateProfileModel> getProfileUpdateWithoutImage(@Part("name") RequestBody name, @Part("email") RequestBody email, @Part("contact_number") RequestBody phone_number);

    @Multipart
    @POST("push-notification/device-token")
    Call<DeviceTokenModel> getDeviceToken(@Part("token") RequestBody token);

    @Multipart
    @POST("push-notification/update/device-token")
    Call<DeviceTokenModel> getUpdateDeviceToken(@Part("token") RequestBody token,@Part("status") RequestBody status,@Part("user_id") RequestBody user_id);
}
