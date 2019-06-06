package com.example.adit.machu_picchu;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


public class ContactFragment extends Fragment {
    Button ask,gmail;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_contact, container, false);

        ask=(Button)view.findViewById(R.id.query);
        gmail=(Button)view.findViewById(R.id.gmail);

        gmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent (Intent.ACTION_VIEW , Uri.parse("mailto:" + "yourgmail"));
                intent.putExtra(Intent.EXTRA_SUBJECT, "Your Feedback's");
                intent.putExtra(Intent.EXTRA_TEXT, "Your Feedback's");
                startActivity(intent);
            }
        });



        ask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getContext(),Contactus.class);
                startActivity(intent);
            }
        });
        return view;
    }


}
