package com.sampra.ui.home.contact;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.sampra.R;
import com.sampra.data.model.about_us.AboutUsModel;
import com.sampra.data.model.contactUs.ContactUsModel;
import com.sampra.data.remote.ApiClient;
import com.sampra.data.remote.ApiInterface;
import com.sampra.ui.EmailActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ContacatFragment extends Fragment implements View.OnClickListener {

    private ContactViewModel contactViewModel;
    private LinearLayout email_view,apply_view;
    private TextView marker,address,weburl,email,contact1,contact2;
    private ApiInterface apiService;
    private LinearLayout contact1_view,contact2_view;
    private Dialog dialog;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        contactViewModel =
                ViewModelProviders.of(this).get(ContactViewModel.class);
        View root = inflater.inflate(R.layout.fragment_contact, container, false);
        initView(root);
        hitContactUsApi();
//        final TextView textView = root.findViewById(R.id.text_notifications);
        contactViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
//                textView.setText(s);
            }
        });
        return root;
    }

    private void initView(View root) {
        email_view = root.findViewById(R.id.email_view);
        apply_view = root.findViewById(R.id.apply_view);
        marker = root.findViewById(R.id.marker);
        address = root.findViewById(R.id.address);
        weburl = root.findViewById(R.id.weburl);
        email = root.findViewById(R.id.email);
        contact1 = root.findViewById(R.id.contact1);
        contact2 = root.findViewById(R.id.contact2);
        contact1_view = root.findViewById(R.id.contact1_view);
        contact2_view = root.findViewById(R.id.contact2_view);
        marker.setOnClickListener(this);
        email_view.setOnClickListener(this);
        apply_view.setOnClickListener(this);
        contact1_view.setOnClickListener(this);
        contact2_view.setOnClickListener(this);
        email.setOnClickListener(this);
    }

    private void hitContactUsApi() {
        apiService =
                ApiClient.getClient().create(ApiInterface.class);
        Call<ContactUsModel> call = apiService.getContactUsDetails();
        call.enqueue(new Callback<ContactUsModel>() {
            @Override
            public void onResponse(Call<ContactUsModel> call, Response<ContactUsModel> response) {
                address.setText(response.body().getContactDetail().getAddress());
                weburl.setText(Html.fromHtml(response.body().getContactDetail().getWeburl()));
                email.setText(response.body().getContactDetail().getEmail());
                contact1.setText(response.body().getContactDetail().getContact1());
                if(response.body().getContactDetail().getContact2().equals(""))
                    contact2_view.setVisibility(View.GONE);
                else
                  contact2.setText(response.body().getContactDetail().getContact2());

            }

            @Override
            public void onFailure(Call<ContactUsModel> call, Throwable t) {

            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.cancel:
                dialog.dismiss();
                break;
            case R.id.artist:
                Intent artist = new Intent(getActivity(),WebActivity.class);
                artist.putExtra("web_url","https://www.sampra.org.za/member-application-form-recording-artists/");
                startActivity(artist);
                dialog.dismiss();
                break;
            case R.id.company:
                Intent company = new Intent(getActivity(),WebActivity.class);
                company.putExtra("web_url","https://www.sampra.org.za/member-application-form-record-companies/");
                startActivity(company);
                dialog.dismiss();
                break;
            case R.id.perfomance:
                Intent perfomance = new Intent(getActivity(),WebActivity.class);
                perfomance.putExtra("web_url","https://www.sampra.org.za/notification-of-recorded-performances/");
                startActivity(perfomance);
                dialog.dismiss();
                break;
            case R.id.email:
                email.setText(Html.fromHtml(email.getText().toString()));
                email.setMovementMethod(LinkMovementMethod.getInstance());
                break;
            case R.id.contact1_view:
                if (Build.VERSION.SDK_INT > 22) {

                    if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {

                        ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CALL_PHONE}, 101);

                        return;
                    }
                    Intent callIntent = new Intent(Intent.ACTION_CALL);
                    callIntent.setData(Uri.parse("tel:+" + contact1.getText().toString().trim()));
                    startActivity(callIntent);
                } else {

                    Intent callIntent = new Intent(Intent.ACTION_CALL);
                    callIntent.setData(Uri.parse("tel:+" + contact1.getText().toString().trim()));
                    startActivity(callIntent);
                }
                break;
            case R.id.contact2_view:
                if (Build.VERSION.SDK_INT > 22) {

                    if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {

                        ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CALL_PHONE}, 101);

                        return;
                    }
                    Intent callIntent = new Intent(Intent.ACTION_CALL);
                    callIntent.setData(Uri.parse("tel:+" + contact2.getText().toString().trim()));
                    startActivity(callIntent);
                } else {

                    Intent callIntent = new Intent(Intent.ACTION_CALL);
                    callIntent.setData(Uri.parse("tel:+" + contact2.getText().toString().trim()));
                    startActivity(callIntent);
                }
                break;
            case R.id.email_view:
                Intent emailIntent = new Intent(getContext(), EmailActivity.class);
                startActivity(emailIntent);
                break;
            case R.id.apply_view:
                applyView(getContext());
                break;
            case R.id.marker:
                new AlertDialog.Builder(getContext())
                        .setTitle("SAMPRA")
                        .setMessage("Would you like to open Maps?")
                        .setNegativeButton(android.R.string.no, null)

                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                String uri = "https://www.google.com/maps/place/20+De+Korte+St,+Johannesburg,+2000,+South+Africa/@-26.1943705,28.0276384,17z/data=!4m13!1m7!3m6!1s0x1e950c04cec144bf:0x383097e7589d788e!2s20+De+Korte+St,+Johannesburg,+2000,+South+Africa!3b1!8m2!3d-26.1943753!4d28.0298271!3m4!1s0x1e950c04cec144bf:0x383097e7589d788e!8m2!3d-26.1943753!4d28.0298271";
                                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                                startActivity(intent);
                            }
                        })
                       // .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
                break;
        }
    }

    private void applyView(Context context) {
        dialog = new Dialog(context);
        dialog.setContentView(R.layout.customdialog_layout);
        TextView title = dialog.findViewById(R.id.title);
        TextView subtitle = dialog.findViewById(R.id.sub_title);
        TextView artist = dialog.findViewById(R.id.artist);
        TextView company = dialog.findViewById(R.id.company);
        TextView perfomance = dialog.findViewById(R.id.perfomance);
        TextView cancel = dialog.findViewById(R.id.cancel);
        cancel.setOnClickListener(this);
        artist.setOnClickListener(this);
        company.setOnClickListener(this);
        perfomance.setOnClickListener(this);
        dialog.show();
    }
}