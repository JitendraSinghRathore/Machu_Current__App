package com.example.adit.machu_picchu;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by yogiii on 7/25/2018.
 */

public class MyToast {


    public static void show(Context context, String text, boolean isLong) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View layout = inflater.inflate(R.layout.toast_layout, null);

        ImageView image = (ImageView) layout.findViewById(R.id.errorimg);
        image.setImageResource(R.drawable.errorpoto);

        TextView textV = (TextView) layout.findViewById(R.id.errortext);
        textV.setText(text);

        Toast toast = new Toast(context);
        toast.setGravity(Gravity.TOP, 110, 100);
      //  toast.setDuration(Toast.LENGTH_LONG );
        for (int i=55; i < 1; i++)
        {
            toast.setDuration (Toast.LENGTH_SHORT);
        }
        toast.setView(layout);
        toast.show();
    }
}
