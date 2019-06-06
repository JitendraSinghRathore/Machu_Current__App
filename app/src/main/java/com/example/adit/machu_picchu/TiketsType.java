package com.example.adit.machu_picchu;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.onurkaganaldemir.ktoastlib.KToast;


public class TiketsType extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    private ProgressBar progressBar;
    private WebView webview;
    private SwipeRefreshLayout swipeLayout;
    private boolean finishing, isInternetConnected;
    private TextView tv_no_internet;
    // Boolean isShowingAlert;
    String answer;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_tikets_type, container, false);


        webview = (WebView) view.findViewById(R.id.webview);

        ConnectivityManager cm = (ConnectivityManager) getActivity()
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();

        if (activeNetwork == null) {

            isInternetConnected = false;

            // webview.setVisibility(View.INVISIBLE);

            answer = "No internet Connectivity";

            KToast.errorToast(getActivity(), answer, Gravity.BOTTOM, KToast.LENGTH_AUTO);
        } else {

            isInternetConnected = true;
        }


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
        webview.loadUrl("http://www.peruways.com/types-of-tickets");
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

    private void startNextActivity() {
        if (isFinishing())
            return;
        Navigation_Drawaer.getInstance().getSupportActionBar().show();
        webview.setVisibility(View.VISIBLE);

        if (isInternetConnected) {
            tv_no_internet.setVisibility(View.GONE);
        } else {
            tv_no_internet.setVisibility(View.VISIBLE);
            webview.setVisibility(View.GONE);
        }


    }


    @Override
    public void onRefresh() {
        webview.reload();

        swipeLayout.setRefreshing(false);
    }


    public Object getSystemService(String connectivityService) {
        return null;
    }

    public boolean isFinishing() {
        return finishing;
    }
}