package com.sampra.ui;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.adapters.ToolbarBindingAdapter;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.sampra.R;
import com.sampra.data.model.ContactFormModel;
import com.sampra.data.model.about_us.AboutUsModel;
import com.sampra.data.remote.ApiClient;
import com.sampra.data.remote.ApiInterface;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import pub.devrel.easypermissions.EasyPermissions;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EmailActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener, EasyPermissions.PermissionCallbacks {
    private static final int CAMERA_REQUEST_CODE = 100;
    private static final int GALLERY_REQUEST_CODE = 101 ;
    Toolbar toolbar;
    Spinner spinner;
    Button attach_btn,submit_btn;
    String names[] = {"Query Type*","Membership Query","Royalties Query","General Query"};
    private ImageView imageView;
    private String selectedItemValue;
    TextInputEditText name,email,phone,query_msg;
    private String user_name,user_email,user_phone,user_msg;
    private ApiInterface apiService;
    private String picturePath;
    private Uri fileUri;
    private Object selectedImagePath;
    private Intent galleryData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email);
        initView();
        setSpinnerAdapter();
    }

    private void setSpinnerAdapter() {
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this,   android.R.layout.simple_spinner_item, names);
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down view
        spinner.setAdapter(spinnerArrayAdapter);
        spinner.setOnItemSelectedListener(this);
    }

    private void initView() {
        name = findViewById(R.id.name);
        email = findViewById(R.id.email);
        phone = findViewById(R.id.phone);
        query_msg = findViewById(R.id.msg);
        submit_btn = findViewById(R.id.submit_btn);
        attach_btn = findViewById(R.id.attach_btn);
        toolbar = findViewById(R.id.toolbar);
        spinner = findViewById(R.id.spinner);
        imageView = findViewById(R.id.imageView);
        toolbar.setTitle("Email Us");
        setSupportActionBar(toolbar);
        attach_btn.setOnClickListener(this);
        submit_btn.setOnClickListener(this);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed(); // Implemented by activity
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.attach_btn:
                selectImage(this);
                break;
            case R.id.submit_btn:
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

                if(query_msg.getText().toString().equals(""))
                    query_msg.setError("Enter Message");
                else
                    user_msg = query_msg.getText().toString();

                if(isEmailValid(email.getText().toString()) == true)
                {
                    user_email = email.getText().toString();
                    if(selectedItemValue.compareTo("Query Type*") == 0)
                        Toast.makeText(getApplicationContext(),"Please select query type",Toast.LENGTH_LONG).show();
                    if(user_name != null && user_email != null && user_phone != null && user_msg != null && selectedItemValue.compareTo("Query Type*") != 0)
                        hitContactFormApi();

                }
                else
                email.setError("Invalid Email");
                break;
        }
    }

    private void hitContactFormApi() {
        int query_value =0;
        if(selectedItemValue.equals("Membership Query"))
            query_value = 1;
        if(selectedItemValue.equals("Royalties Query"))
            query_value = 2;
        if(selectedItemValue.equals("General Query"))
            query_value = 3;
        apiService = ApiClient.getClient().create(ApiInterface.class);
//        Call<String> call = apiService.geCcontactFormDetails(user_name,user_email,user_phone,user_msg,String.valueOf(query_value));
//        Map<String,String> params = new HashMap<String, String>();
//        params.put("name", user_name);
//        params.put("email", user_email);
//        params.put("phone_number", user_phone);
//        params.put("message", user_msg);
//        params.put("query_type", String.valueOf(query_value));
//        params.put("file", file);
        File file = new File(picturePath);
        RequestBody fileReqBody = RequestBody.create(MediaType.parse("image/*"), file);
        MultipartBody.Part part = MultipartBody.Part.createFormData("file", file.getName(), fileReqBody);

        RequestBody name = RequestBody.create(MediaType.parse("text/plain"), user_name);
        RequestBody email = RequestBody.create(MediaType.parse("text/plain"), user_email);
        RequestBody phone_number = RequestBody.create(MediaType.parse("text/plain"), user_phone);
        RequestBody message = RequestBody.create(MediaType.parse("text/plain"), user_msg);
        RequestBody query_type = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(query_value));

        Call<ContactFormModel> call = apiService.getContactFormDetails(part,name,email,phone_number,message,query_type);
        call.enqueue(new Callback<ContactFormModel>() {
            @Override
            public void onResponse(Call<ContactFormModel> call, Response<ContactFormModel> response) {
               showPopUP(response);
            }

            @Override
            public void onFailure(Call<ContactFormModel> call, Throwable t) {

            }
        });
    }

    private void showPopUP(Response<ContactFormModel> response) {
        new AlertDialog.Builder(this)
                .setMessage(response.body().getMessage())
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Continue with delete operation
                    }
                })
                .show();
    }

    private void selectImage(Context context) {
        final CharSequence[] options = { "Take Photo", "Choose from Gallery","Cancel" };

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_CANCELED) {
            switch (requestCode) {
                case 0:
                    if (resultCode == RESULT_OK && data != null) {
//                        Bitmap selectedImage = (Bitmap) data.getExtras().get("data");
//                        imageView.setImageBitmap(selectedImage);
//                        fileUri = (Uri) getOutputMediaFileUri();

                        if(EasyPermissions.hasPermissions(this, Manifest.permission.READ_EXTERNAL_STORAGE) && EasyPermissions.hasPermissions(this, Manifest.permission.WRITE_EXTERNAL_STORAGE))
                        {
                            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
                            String imageFileName = "JPEG_" + timeStamp + "_";
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
                                Cursor cursor = getContentResolver().query(selectedImage,
                                        filePathColumn, null, null, null);
                                if (cursor != null) {
                                    cursor.moveToFirst();

                                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                                    picturePath = cursor.getString(columnIndex);
                                    imageView.setImageBitmap(BitmapFactory.decodeFile(picturePath));
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
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        selectedItemValue = parent.getSelectedItem().toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

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
                        imageView.setImageBitmap(BitmapFactory.decodeFile(picturePath));
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
