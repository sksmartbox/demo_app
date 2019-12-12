package com.sampra.ui.home.contact;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.sampra.R;
import com.sampra.ui.EmailActivity;

public class ContacatFragment extends Fragment implements View.OnClickListener {

    private ContactViewModel contactViewModel;
    private LinearLayout email_view,apply_view;
    private TextView marker;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        contactViewModel =
                ViewModelProviders.of(this).get(ContactViewModel.class);
        View root = inflater.inflate(R.layout.fragment_contact, container, false);
        email_view = root.findViewById(R.id.email_view);
        apply_view = root.findViewById(R.id.apply_view);
        marker = root.findViewById(R.id.marker);
        marker.setOnClickListener(this);
        email_view.setOnClickListener(this);
        apply_view.setOnClickListener(this);
//        final TextView textView = root.findViewById(R.id.text_notifications);
        contactViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
//                textView.setText(s);
            }
        });
        return root;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
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

                        // Specifying a listener allows you to take an action before dismissing the dialog.
                        // The dialog is automatically dismissed when a dialog button is clicked.
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                String uri = "http://maps.google.com/maps?saddr=" + -26.193875 + "," + 28.029870 + "&daddr=" + -26.194135 + "," + 28.029795;
                                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                                startActivity(intent);
                            }
                        })

                        // A null listener allows the button to dismiss the dialog and take no further action.
                        .setNegativeButton(android.R.string.no, null)
                       // .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
                break;
        }
    }

    private void applyView(Context context) {
        final CharSequence[] options = { "APPLY AS A RECORD COMPANY", "CANCEL","APPLY AS A RECORDING ARTIST" };

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("SAMPRA");
        builder.setItems(options, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int item) {

                if (options[item].equals("APPLY AS A RECORD COMPANY")) {

                } else if (options[item].equals("CANCEL")) {
                    dialog.dismiss();

                } else if (options[item].equals("APPLY AS A RECORDING ARTIST")) {

                }
            }
        });
        builder.show();
    }
}