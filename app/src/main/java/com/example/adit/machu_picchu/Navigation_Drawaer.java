package com.example.adit.machu_picchu;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.onurkaganaldemir.ktoastlib.KToast;

import com.onesignal.OSNotification;
import com.onesignal.OSNotificationOpenResult;
import com.onesignal.OneSignal;

import org.json.JSONException;

import static android.app.ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND;
import static android.app.ActivityManager.RunningAppProcessInfo.IMPORTANCE_VISIBLE;
import static java.security.AccessController.getContext;

public class Navigation_Drawaer extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    ImageView imageView;
    Toolbar toolbar;
    Homea homea;
    AppBarLayout appBarLayout;
    static Navigation_Drawaer nav_drawer_activity;
    private Object systemService;

    Boolean isShowingAlert;
    private static String NOTIFICATION_PREF = "notification preferences";
    private static String NOTIFICATION_TITLE_KEY = "notifiction title";
    private static String NOTIFICATION_BODY_KEY = "notifiction body";
    private static String NOTIFICATION_URL_KEY = "url";
    TextView book_tikets,type_of_tikets;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_navigation__drawaer);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        appBarLayout = (AppBarLayout) findViewById(R.id.appbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().hide();
        nav_drawer_activity = this;
        isShowingAlert = false;
        setupOneSignalNotifications();
        toolbar.setTitleTextColor(Color.BLUE);
        imageView=(ImageView)findViewById(R.id.refresh);
        book_tikets=(TextView) findViewById(R.id.toolbar_title);
        type_of_tikets=(TextView) findViewById(R.id.toolbar_title_typetikets);
        Fragment fragment = null;
        fragment = new Homea();
        displaySelectedFragment(fragment);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();


        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ConnectivityManager cm = (ConnectivityManager) getApplicationContext()
                        .getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo activeNetwork = cm.getActiveNetworkInfo();

                if (activeNetwork != null) {

                    Fragment fragment = null;
                    fragment = new Home();
                    displaySelectedFragment(fragment);
                }else{

                    KToast.errorToast(Navigation_Drawaer.this, "No internet Connectivity", Gravity.BOTTOM, KToast.LENGTH_AUTO);
                }
            }
        });
//



    }

//
    @Override
    public void onResume() {
        super.onResume();

        checkForPendingNotification();
    }

    private void checkForPendingNotification(){

        //Check for pending notifications
        SharedPreferences prefs = getNotificationPrefs();

        if (prefs.getString(NOTIFICATION_TITLE_KEY, null) != null) {

            if(!isShowingAlert)
                showAlertDialogWithMesssage(prefs.getString(NOTIFICATION_TITLE_KEY,null), prefs.getString(NOTIFICATION_BODY_KEY, null), prefs.getString(NOTIFICATION_URL_KEY,null));

            clearNotificationPrefs();
        }
    }

    private void setupOneSignalNotifications(){

        OneSignal.startInit(getApplicationContext())
                .autoPromptLocation(true)
                .inFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification)
                .setNotificationReceivedHandler(new OneSignal.NotificationReceivedHandler() {
                    @Override
                    public void notificationReceived(OSNotification notification) {

                            if(!notification.isAppInFocus){

                                //Save notifications when it arrives
                                SharedPreferences.Editor editor = getNotificationPrefs().edit();
                                editor.putString(NOTIFICATION_TITLE_KEY, notification.payload.title);
                                editor.putString(NOTIFICATION_BODY_KEY, notification.payload.body);

                                try {
                                    if(notification.payload.additionalData != null){

                                        editor.putString(NOTIFICATION_URL_KEY, notification.payload.additionalData.getString("url"));
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                                editor.apply();
                            }else {

                                if(!isShowingAlert) {
                                    showAlertDialogWithMesssage(notification);

                                notification.shown = true;
                                cancelNotification(getApplicationContext(), notification.androidNotificationId);
                            }
                        }
                    }
                })
                .setNotificationOpenedHandler(new OneSignal.NotificationOpenedHandler() {

                    @Override
                    public void notificationOpened(OSNotificationOpenResult result) {


                        if(!isShowingAlert) {
                            showAlertDialogWithMesssage(result.notification);
//                            Toast.makeText(getApplicationContext(), result.notification.payload.title, Toast.LENGTH_LONG).show();

//                            if(foregrounded())
//                                clearNotificationPrefs();
                        }


                    }
                })

                .unsubscribeWhenNotificationsAreDisabled(true)
                .init();
    }

    private SharedPreferences getNotificationPrefs(){

        SharedPreferences prefs = getApplicationContext().getApplicationContext().getSharedPreferences(NOTIFICATION_PREF, Context.MODE_PRIVATE);

        return prefs;
    }

    private void clearNotificationPrefs(){

        SharedPreferences.Editor editor = getNotificationPrefs().edit();
        editor.clear();
        editor.apply();
    }
//
    public boolean foregrounded() {
        ActivityManager.RunningAppProcessInfo appProcessInfo = new ActivityManager.RunningAppProcessInfo();
        ActivityManager.getMyMemoryState(appProcessInfo);
        return (appProcessInfo.importance == IMPORTANCE_FOREGROUND || appProcessInfo.importance == IMPORTANCE_VISIBLE);
    }



    public static void cancelNotification(Context ctx, int notificationId) {
        String ns = Context.NOTIFICATION_SERVICE;
        NotificationManager nMgr = (NotificationManager) ctx.getSystemService(ns);
        assert nMgr != null;
        nMgr.cancel(notificationId);
    }

    public void showAlertDialogWithMesssage(String title, String message, String url){

        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
        builder1.setMessage(title)
                .setTitle(message);

        if(url != null){

            builder1.setPositiveButton(
                        "Open",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                isShowingAlert = false;

                                String url = null;

                                if (!url.startsWith("http://") && !url.startsWith("https://"))
                                    url = "http://" + url;

                                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                                startActivity(browserIntent);

                            }
                        });
        }

        builder1.setNegativeButton(
                "Close",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        isShowingAlert = false;
//                        if(foregrounded()) {
//                            cancelNotification(getApplicationContext());
//                        }
                    }
                });

        AlertDialog alert11 = builder1.create();

        alert11.setCancelable(false);
        alert11.setCanceledOnTouchOutside(false);
        isShowingAlert = true;

        alert11.show();
    }

    public void showAlertDialogWithMesssage(final OSNotification notification){

        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
        builder1.setMessage(notification.payload.body)
                .setTitle(notification.payload.title);

        if(notification.payload.additionalData != null){

            builder1.setPositiveButton(
                    "Open",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            isShowingAlert = false;

                            String url = null;
                            try {
                                url = notification.payload.additionalData.getString("url");

                                if (!url.startsWith("http://") && !url.startsWith("https://"))
                                    url = "http://" + url;

                                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                                startActivity(browserIntent);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });
        }

        builder1.setNegativeButton(
                "Close",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        isShowingAlert = false;

                    }
                });

        AlertDialog alert11 = builder1.create();

        alert11.setCancelable(false);
        alert11.setCanceledOnTouchOutside(false);
        isShowingAlert = true;

        alert11.show();
    }

//
//

    public static Navigation_Drawaer getInstance(){
        return   nav_drawer_activity;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
          //  super.onBackPressed();
            android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(Navigation_Drawaer.this);
            builder.setTitle("Confirm Exit");
            builder.setMessage("Are you sure you want to exit?")
                    .setCancelable(false)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                        public void onClick(DialogInterface dialog, int id) {
                            finishAffinity();
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });
            android.app.AlertDialog alert = builder.create();
            alert.show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.navigation__drawaer, menu);
        return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressLint("ResourceType")
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Fragment fragment = null;
        if (id == R.id.home) {
//            Intent intent=new Intent(Navigation_Drawaer.this,HomeActivity.class);
//            startActivity(intent);
            ConnectivityManager cm = (ConnectivityManager) getApplicationContext()
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo activeNetwork = cm.getActiveNetworkInfo();

            if (activeNetwork != null) {

                fragment = new Home();
                displaySelectedFragment(fragment);
                book_tikets.setVisibility(View.VISIBLE);
                type_of_tikets.setVisibility(View.GONE);

            }else{

                KToast.errorToast(Navigation_Drawaer.this, "No internet Connectivity", Gravity.BOTTOM, KToast.LENGTH_AUTO);
            }

        } else if (id == R.id.nav_share) {

            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            intent.putExtra(Intent.EXTRA_SUBJECT,"Machu_Picchu");
            String text = "Cusco to Machu Picchu Hidroelectrica";
            intent.putExtra(Intent.EXTRA_TEXT,"Machu_Picchu is a Cusco to Machu Picchu Hidroelectrica");
            startActivity(Intent.createChooser(intent, "Share via"));
        } else if (id == R.id.nav_send) {
            Intent intent = new Intent (Intent.ACTION_VIEW , Uri.parse("mailto:" + "contact@peruways.com"));
            intent.putExtra(Intent.EXTRA_SUBJECT, "Your Feedback's");
            intent.putExtra(Intent.EXTRA_TEXT, "Your Feedback's");
            startActivity(intent);
        }
        else if (id == R.id.urla) {
            fragment = new TiketsType();
            displaySelectedFragment(fragment);
            book_tikets.setVisibility(View.GONE);
            type_of_tikets.setVisibility(View.VISIBLE);

          /*  String url="http://www.peruways.com/types-of-tickets";
            Intent intent=new Intent(Intent.ACTION_VIEW,Uri.parse(url));
            startActivity(intent);*/

        }
       /* else if (id==R.id.urla)
        {
            String url="WWW.Google.Com";
            Intent intent=new Intent(Intent.ACTION_VIEW,Uri.parse(url));
            startActivity(intent);
        }*/


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    private void displaySelectedFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame, fragment);
        fragmentTransaction.commit();
    }

    public Object getSystemService() {
        return systemService;
    }

    public void onNewIntent() {

    }
}
