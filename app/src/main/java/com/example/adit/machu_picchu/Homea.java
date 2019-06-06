package com.example.adit.machu_picchu;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import com.onesignal.OSNotification;
import com.onesignal.OSNotificationOpenResult;
import com.onesignal.OneSignal;

import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.onurkaganaldemir.ktoastlib.KToast;


import static android.app.ActivityManager.RunningAppProcessInfo.IMPORTANCE_BACKGROUND;
import static android.app.ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND;
import static android.app.ActivityManager.RunningAppProcessInfo.IMPORTANCE_VISIBLE;
import static com.onesignal.OneSignal.setInFocusDisplaying;


public class Homea extends Fragment{
    VideoView videoView;
    ImageView imageView;
    Navigation_Drawaer navigation_drawaer;
    private WebView webview;
    private ProgressBar progressBar;
    private SwipeRefreshLayout swipeLayout;
    private boolean finishing, isInternetConnected;
    private TextView tv_no_internet;
   // Boolean isShowingAlert;
    String answer;
    AppBarLayout appBarLayout;
//    private static final String TAG_FOREGROUND_SERVICE = "FOREGROUND_SERVICE";
//    private static String NOTIFICATION_PREF = "notification preferences";
//    private static String NOTIFICATION_TITLE_KEY = "notifiction title";
//    private static String NOTIFICATION_BODY_KEY = "notifiction body";

    private Intent intent;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_homea, container, false);

        webview = (WebView) view.findViewById(R.id.webview);
        tv_no_internet = view.findViewById(R.id.tv_no_internet);
        videoView = (VideoView) view.findViewById(R.id.videoView);
        progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
//        isShowingAlert = false;
//        setupOneSignalNotifications();
        setupIntroVideo();
       // setupWebView();




        return view;
    }

//    @Override
//    public void onResume() {
//        super.onResume();
//        checkForPendingNotification();
//    }
////
//    @Override
//    public void onDestroy() {
//        super.onDestroy();
//
//    }
//
//
//    private void checkForPendingNotification(){
//
//        //Check for pending notifications
//        SharedPreferences prefs = getNotificationPrefs();
//
//        if (prefs.getString(NOTIFICATION_TITLE_KEY, null) != null) {
//
//            if(!isShowingAlert)
//                showAlertDialogWithMesssage(prefs.getString(NOTIFICATION_TITLE_KEY,null), prefs.getString(NOTIFICATION_BODY_KEY, null));
////
//        }
//    }
//
//
//    private void setupOneSignalNotifications(){
//
//        OneSignal.startInit(getContext())
//                .autoPromptLocation(true)
//                .inFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification)
//                .setNotificationReceivedHandler(new OneSignal.NotificationReceivedHandler() {
//                    @Override
//                    public void notificationReceived(OSNotification notification) {
//
//
//                        //Save notifications when it arrives
//                        SharedPreferences.Editor editor = getNotificationPrefs().edit();
//                        editor.putString(NOTIFICATION_TITLE_KEY, notification.payload.title);
//                        editor.putString(NOTIFICATION_BODY_KEY, notification.payload.body);
//                        editor.apply();
//
//                        if(!isShowingAlert) {
//                            showAlertDialogWithMesssage(notification.payload.title, notification.payload.body);
////                            clearNotificationPrefs();
////                            cancelNotification(getActivity());
//                            stopForeground(true);
//                        }
//                    }
//
//                    private void stopForeground(boolean b) {
//
//                    }
//                })
//                .setNotificationOpenedHandler(new OneSignal.NotificationOpenedHandler() {
//
//                    @Override
//                    public void notificationOpened(OSNotificationOpenResult result) {
//
//
//                        if(!isShowingAlert)
//                            showAlertDialogWithMesssage(result.notification.payload.title, result.notification.payload.body);
////                        clearNotificationPrefs();
////                        cancelNotification(getActivity());
//                        if(foregrounded()) {
//                            cancelNotification(getContext());
//                            clearNotificationPrefs();
//                        }
//
//                    }
//                })
//
//                .unsubscribeWhenNotificationsAreDisabled(true)
//                .init();
//    }
//
//    private SharedPreferences getNotificationPrefs(){
//
//        SharedPreferences prefs = getActivity().getApplicationContext().getSharedPreferences(NOTIFICATION_PREF, Context.MODE_PRIVATE);
//
//        return prefs;
//    }
//
//    private void clearNotificationPrefs(){
//
//        SharedPreferences.Editor editor = getNotificationPrefs().edit();
//        editor.clear();
//        editor.apply();
//    }
//
//    public boolean foregrounded() {
//        ActivityManager.RunningAppProcessInfo appProcessInfo = new ActivityManager.RunningAppProcessInfo();
//        ActivityManager.getMyMemoryState(appProcessInfo);
//        return (appProcessInfo.importance == IMPORTANCE_FOREGROUND || appProcessInfo.importance == IMPORTANCE_VISIBLE);
//    }
//    public boolean background() {
//        ActivityManager.RunningAppProcessInfo appProcessInfo = new ActivityManager.RunningAppProcessInfo();
//        ActivityManager.getMyMemoryState(appProcessInfo);
//        return (appProcessInfo.importance == IMPORTANCE_BACKGROUND || appProcessInfo.importance == IMPORTANCE_VISIBLE);
//    }
//
//    public static void cancelNotification(Context ctx) {
//        String ns = Context.NOTIFICATION_SERVICE;
//        NotificationManager nMgr = (NotificationManager) ctx.getSystemService(ns);
//        assert nMgr != null;
//        nMgr.cancelAll();
//    }
//
//    private void showAlertDialogWithMesssage(String title, String message){
//
//        AlertDialog.Builder builder1 = new AlertDialog.Builder(getContext());
//        builder1.setMessage(message)
//                .setTitle(title);
//
//        builder1.setPositiveButton(
//                "OK",
//                new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int id) {
//                        isShowingAlert = false;
//                        cancelNotification(getContext());
//                        clearNotificationPrefs();
//                        if(foregrounded()) {
//                            cancelNotification(getContext());
//                            clearNotificationPrefs();
//                        }
//
//                    }
//                });
//
//
//
//        AlertDialog alert11 = builder1.create();
//        alert11.show();
//        alert11.setCancelable(false);
//        alert11.setCanceledOnTouchOutside(false);
//        isShowingAlert = true;
//    }


    private void setupIntroVideo(){

        Uri video = Uri.parse("android.resource://" + getContext().getPackageName() + "/" + R.raw.machu);
        videoView.setVideoURI(video);
        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                /*videoView.start();
                videoView.bringToFront();*/
                setupWebView();

            }
        });

        videoView.start();
        videoView.getDuration();
    }



    private void setupWebView(){


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
        webview.setWebViewClient(new MyWebClient());
        webview.setScrollbarFadingEnabled(true);

        webview.setWebChromeClient(new WebChromeClient());

        webview.setWebChromeClient(new WebChromeClient() {
            private String title;

            public void setTitle(String title) {
                this.title = title;
            }

            public void onProgressChanged(WebView view, int progress) {

            }
        });
    }



    private void startNextActivity() {
        if (isFinishing())
            return;
        Navigation_Drawaer.getInstance().getSupportActionBar().show();
        videoView.setVisibility(View.GONE);
        //webview.setVisibility(View.VISIBLE);

        if(isInternetConnected){
            tv_no_internet.setVisibility(View.GONE);
        }else{
            tv_no_internet.setVisibility(View.VISIBLE);
            webview.setVisibility(View.GONE);
        }



    }

    public boolean isFinishing() {
        return finishing;
    }

    public void setIntent(Intent intent) {
        this.intent = intent;
    }


    public class MyWebClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);



            return false;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
           
            startNextActivity();

            super.onPageFinished(view, url);
        }

    }

//    private void onNewIntent(Intent intent) {
//        navigation_drawaer.onNewIntent();
//        setIntent(intent);
//    }


}
