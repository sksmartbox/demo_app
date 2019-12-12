package com.sampra.ui.settings;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.sampra.R;
import com.sampra.data.model.profile_update.UpdateProfileModel;
import com.sampra.data.remote.ApiClient;
import com.sampra.data.remote.ApiInterface;
import com.sampra.ui.home.livechat.ChatScreenFragment;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import pub.devrel.easypermissions.EasyPermissions;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditProfile extends AppCompatActivity implements View.OnClickListener,EasyPermissions.PermissionCallbacks {

    private static final int CAMERA_REQUEST_CODE = 100;
    private static final int GALLERY_REQUEST_CODE = 101 ;
    private CircleImageView profile_image_update;
    private TextInputEditText name,phone,email;
    private Button update_submit_btn;
    private String user_name,user_phone,user_email;
    private ApiInterface apiService;
    private String picturePath;
    private Intent galleryData;
    SharedPreferences sharedPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        initView();
    }

    private void initView() {
        profile_image_update = findViewById(R.id.profile_image_update);
        sharedPref = this.getPreferences(Context.MODE_PRIVATE);
        name = findViewById(R.id.name);
        phone = findViewById(R.id.phone);
        email = findViewById(R.id.email);
        update_submit_btn = findViewById(R.id.update_submit_btn);
        profile_image_update.setOnClickListener(this);
        update_submit_btn.setOnClickListener(this);
//        name.setText(sharedPref.getString(R.string.profile_name),"");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.profile_image_update:
                updateImage();
                break;
            case R.id.update_submit_btn:
                updateProfile();
                break;
        }
    }
    private void updateProfile() {
        if(name.getText().toString().equals(""))
            name.setError("Enter Name");
        else
            user_name = name.getText().toString();

        if(phone.getText().toString().equals(""))
            phone.setError("Enter Telephone Number");
        else if(phone.getText().toString().length() < 10)
            phone.setError("Enter 10 digits phone number");
        else
            user_phone = phone.getText().toString();

        if(isEmailValid(email.getText().toString()) == true)
        {
            user_email = email.getText().toString();
//            if(selectedItemValue.compareTo("Query Type*") == 0)
//                Toast.makeText(getApplicationContext(),"Please select query type",Toast.LENGTH_LONG).show();
//            if(user_name != null && user_email != null && user_phone != null && user_msg != null && selectedItemValue.compareTo("Query Type*") != 0)
            hitAddChatUser();

        }
        else
            email.setError("Invalid Email");
    }

    private void hitAddChatUser() {
        File file;
        apiService = ApiClient.getClient().create(ApiInterface.class);
        if (picturePath != null)
        {
            file = new File(picturePath);
            RequestBody fileReqBody = RequestBody.create(MediaType.parse("image/*"), file);
            MultipartBody.Part part = MultipartBody.Part.createFormData("file", file.getName(), fileReqBody);

            RequestBody name = RequestBody.create(MediaType.parse("text/plain"), user_name);
            RequestBody email = RequestBody.create(MediaType.parse("text/plain"), user_email);
            RequestBody phone_number = RequestBody.create(MediaType.parse("text/plain"), user_phone);

            Call<UpdateProfileModel> call = apiService.getProfileUpdate(part,name,email,phone_number);
            call.enqueue(new Callback<UpdateProfileModel>() {
                @Override
                public void onResponse(Call<UpdateProfileModel> call, Response<UpdateProfileModel> response) {
                    Toast.makeText(getApplicationContext(),response.body().getMessage(), Toast.LENGTH_LONG).show();
                }

                @Override
                public void onFailure(Call<UpdateProfileModel> call, Throwable t) {

                }
            });
        }
        else
        {
            file = new File("");
//            RequestBody fileReqBody = RequestBody.create(MediaType.parse("image/*"), file);
//            MultipartBody.Part part = MultipartBody.Part.createFormData("file", file.getName(), fileReqBody);

            RequestBody name = RequestBody.create(MediaType.parse("text/plain"), user_name);
            RequestBody email = RequestBody.create(MediaType.parse("text/plain"), user_email);
            RequestBody phone_number = RequestBody.create(MediaType.parse("text/plain"), user_phone);

            Call<UpdateProfileModel> call = apiService.getProfileUpdateWithoutImage(name,email,phone_number);
            call.enqueue(new Callback<UpdateProfileModel>() {
                @Override
                public void onResponse(Call<UpdateProfileModel> call, Response<UpdateProfileModel> response) {
                    Toast.makeText(getApplicationContext(),response.body().getMessage(), Toast.LENGTH_LONG).show();

                }

                @Override
                public void onFailure(Call<UpdateProfileModel> call, Throwable t) {

                }
            });
        }


    }

    public boolean isEmailValid(String email)
    {
        String regExpn =
                "^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
                        +"((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                        +"[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                        +"([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                        +"[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
                        +"([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$";

        CharSequence inputStr = email;

        Pattern pattern = Pattern.compile(regExpn, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);

        if(matcher.matches())
            return true;
        else
            return false;
    }
    private void updateImage() {
        final CharSequence[] options = { "Take Photo", "Choose from Gallery","Cancel" };

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Choose your profile picture");

        builder.setItems(options, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int item) {

                if (options[item].equals("Take Photo")) {
                    Intent takePicture = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(takePicture, 0);

                } else if (options[item].equals("Choose from Gallery")) {
                    Intent pickPhoto = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(pickPhoto , 1);

                } else if (options[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_CANCELED) {
            switch (requestCode) {
                case 0:
                    if (resultCode == RESULT_OK && data != null) {
                        Bitmap selectedImage = (Bitmap) data.getExtras().get("data");
                        profile_image_update.setImageBitmap(selectedImage);
//                        fileUri = (Uri) getOutputMediaFileUri();

                        if(EasyPermissions.hasPermissions(this, Manifest.permission.READ_EXTERNAL_STORAGE))
                        {
                            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
                            String imageFileName = "JPEG_" + timeStamp + "_";

//                        File outputFileUri = null;
//                            File getImage = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
                            File getImage = getExternalFilesDir(Environment.DIRECTORY_PICTURES);

                            File image = null;
                            try {
                                image = File.createTempFile(
                                        imageFileName,  /* prefix */
                                        ".jpg",         /* suffix */
                                        getImage      /* directory */
                                );
                            } catch (IOException e) {
                                e.printStackTrace();
                            }


                            if (image != null) {
//                            outputFileUri = new File(getImage.getPath(), "profile.png");
                                picturePath = image.getAbsolutePath();
//                            picturePath = outputFileUri.getPath();
                            }
                        }
                        else
                        {
                            EasyPermissions.requestPermissions(this, getString(R.string.rationale_camera), CAMERA_REQUEST_CODE, Manifest.permission.READ_EXTERNAL_STORAGE);
                        }

                    }

                    break;
                case 1:
                    if (resultCode == RESULT_OK && data != null) {
                        galleryData = data;
                        if(EasyPermissions.hasPermissions(this, Manifest.permission.READ_EXTERNAL_STORAGE))
                        {
                            Uri selectedImage = data.getData();
                            String[] filePathColumn = {MediaStore.Images.Media.DATA};
                            if (selectedImage != null) {
                                Cursor cursor = this.getContentResolver().query(selectedImage,
                                        filePathColumn, null, null, null);
                                if (cursor != null) {
                                    cursor.moveToFirst();

                                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                                    picturePath = cursor.getString(columnIndex);
                                    profile_image_update.setImageBitmap(BitmapFactory.decodeFile(picturePath));
                                    cursor.close();
                                }
                            }
                        }
                        else
                        {
                            EasyPermissions.requestPermissions(this, getString(R.string.rationale_gallery), GALLERY_REQUEST_CODE, Manifest.permission.READ_EXTERNAL_STORAGE);
                        }
                    }
                    break;
            }
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
        if(requestCode == 100)
        {
            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            String imageFileName = "JPEG_" + timeStamp + "_";

//                        File outputFileUri = null;
            File getImage = getExternalFilesDir(Environment.DIRECTORY_PICTURES);

            File image = null;
            try {
                image = File.createTempFile(
                        imageFileName,  /* prefix */
                        ".jpg",         /* suffix */
                        getImage      /* directory */
                );
            } catch (IOException e) {
                e.printStackTrace();
            }


            if (image != null) {
//                            outputFileUri = new File(getImage.getPath(), "profile.png");
                picturePath = image.getAbsolutePath();
//                            picturePath = outputFileUri.getPath();
            }
            if(requestCode == 101)
            {
                Uri selectedImage = galleryData.getData();
                String[] filePathColumn = {MediaStore.Images.Media.DATA};
                if (selectedImage != null) {
                    Cursor cursor = getContentResolver().query(selectedImage,
                            filePathColumn, null, null, null);
                    if (cursor != null) {
                        cursor.moveToFirst();

                        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                        picturePath = cursor.getString(columnIndex);
//                        imageView.setImageBitmap(BitmapFactory.decodeFile(picturePath));
                        cursor.close();
                    }
                }
            }
        }

    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {

    }
}