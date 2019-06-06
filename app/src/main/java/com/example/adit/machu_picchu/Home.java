package com.example.adit.machu_picchu;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.onesignal.OSPermissionObserver;
import com.onesignal.OSPermissionStateChanges;
import com.onesignal.OSPermissionSubscriptionState;
import com.onesignal.OSSubscriptionObserver;
import com.onesignal.OSSubscriptionStateChanges;
//import com.onesignal.OneSignal;
import com.onurkaganaldemir.ktoastlib.KToast;

import org.json.JSONException;
import org.json.JSONObject;

import static android.support.v4.widget.SwipeRefreshLayout.*;
import static com.onesignal.OneSignal.logoutEmail;


public class Home extends Fragment implements OnRefreshListener{
    private ProgressBar progressBar;
    private WebView webview;
    private SwipeRefreshLayout swipeLayout;
    private Context baseContext;
    String answer;


    @SuppressLint("WrongConstant")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_home, container, false);



        webview = (WebView) view.findViewById(R.id.webview);




        progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
        swipeLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeLayout);
        swipeLayout.setOnRefreshListener(this);
        progressBar.setMax(100);
        WebSettings webSettings = webview.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webview.getSettings().setBuiltInZoomControls(true);
        webview.getSettings().setDisplayZoomControls(false);
        webview.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
        webview.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        webview.getSettings().setAppCacheEnabled(true);
        webview.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        webSettings.setDomStorageEnabled(true);
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        webSettings.setUseWideViewPort(true);
        webSettings.setSavePassword(true);
        webSettings.setSaveFormData(true);
        webSettings.setEnableSmoothTransition(true);
        webview.loadUrl("https://www.peruways.com/tickets-machu-picchu-php/");
        webview.setWebViewClient(new WebViewClient());
        webview.setScrollbarFadingEnabled(true);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            webview.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        } else {
            webview.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }

        progressBar.setProgress(0);
        Utility.showProgressDialog(getContext(),"Your profile Loading");

        webview.setWebChromeClient(new WebChromeClient());

        webview.setWebChromeClient(new WebChromeClient() {
            private String title;

            public void setTitle(String title) {
                this.title = title;
            }

            public void onProgressChanged(WebView view, int progress) {

                progressBar.setProgress(progress);


                setTitle("Please Wait A Moment...");
                if (progress == 100) {
                    setTitle(view.getTitle());

                    progressBar.setVisibility(View.GONE);
                    Utility.dialog.dismiss();

                } else {
                    progressBar.setVisibility(View.VISIBLE);

                }
                super.onProgressChanged(view, progress);
            }
        });




        return view;


    }


    @Override
    public void onRefresh() {
        webview.reload();

        swipeLayout.setRefreshing(false);
    }


    public Object getSystemService(String connectivityService) {
        return null;
    }
}
