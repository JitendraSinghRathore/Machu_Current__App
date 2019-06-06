package com.example.adit.machu_picchu;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.Window;




public class Utility
{
    public static Dialog dialog;
    public static void showProgressDialog(Context context_dilog, String Title)
    {
        // check privious dialog
        dialog= new Dialog(context_dilog);
        if (dialog != null || dialog.isShowing())
        {
            dialog.dismiss();
        }
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable((new ColorDrawable(android.graphics.Color.TRANSPARENT)));
        dialog.setContentView(R.layout.prgress_bar_layout);
        dialog.setCancelable(true);
        dialog.show();
    }
    public static void hideProgressDialog()
    {
        if (dialog != null)
        {
            if (dialog.isShowing())
            {
                dialog.dismiss();
            }
            dialog = null;
        }
    }
}