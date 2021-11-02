package com.tuplv.mynote.activity;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.navigation.NavigationView;
import com.huawei.hms.ads.AdParam;
import com.huawei.hms.ads.BannerAdSize;
import com.huawei.hms.ads.HwAds;
import com.huawei.hms.ads.banner.BannerView;
import com.huawei.hms.hmsscankit.ScanUtil;
import com.huawei.hms.ml.scan.HmsScan;
import com.huawei.hms.ml.scan.HmsScanAnalyzerOptions;
import com.tuplv.mynote.R;
import com.tuplv.mynote.fragment.CalendarFragment;
import com.tuplv.mynote.fragment.NoteFragment;
import com.tuplv.mynote.fragment.RemindFragment;
import com.tuplv.mynote.fragment.SettingsFragment;
import com.tuplv.mynote.fragment.TrashFragment;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    public static final int DEFAULT_VIEW = 0x22;

    private static final int FRAGMENT_NOTE = 0;
    private static final int FRAGMENT_CALENDAR = 1;
    private static final int FRAGMENT_REMIND = 2;
    private static final int FRAGMENT_TRASH = 3;
    private static final int FRAGMENT_SETTINGS = 4;

    private int currentFragment = FRAGMENT_NOTE;

    DrawerLayout drawerLayout;
    Toolbar tbMain;
    NavigationView nav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            this.requestPermissions(
                    new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE},
                    DEFAULT_VIEW);
        }
        mapping();

        setSupportActionBar(tbMain);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, tbMain, R.string.nav_drawer_open, R.string.nav_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        nav.setNavigationItemSelectedListener(this);

        replaceFragment(new NoteFragment());
        nav.getMenu().findItem(R.id.nav_note).setChecked(true);
    }

    private void mapping() {
        drawerLayout = findViewById(R.id.drawerLayout);
        tbMain = findViewById(R.id.tbMain);
        nav = findViewById(R.id.nav);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_note:
                if (currentFragment != FRAGMENT_NOTE) {
                    getSupportActionBar().setTitle("My Note");
                    replaceFragment(new NoteFragment());
                    currentFragment = FRAGMENT_NOTE;
                }
                break;
            case R.id.nav_calendar:
                if (currentFragment != FRAGMENT_CALENDAR) {
                    getSupportActionBar().setTitle("Calendar");
                    replaceFragment(new CalendarFragment());
                    currentFragment = FRAGMENT_CALENDAR;
                }
                break;
            case R.id.nav_remind:
                if (currentFragment != FRAGMENT_REMIND) {
                    getSupportActionBar().setTitle("Remind");
                    replaceFragment(new RemindFragment());
                    currentFragment = FRAGMENT_REMIND;
                }
                break;
            case R.id.nav_trash:
                if (currentFragment != FRAGMENT_TRASH) {
                    getSupportActionBar().setTitle("Trash");
                    replaceFragment(new TrashFragment());
                    currentFragment = FRAGMENT_TRASH;
                }
                break;
            case R.id.nav_settings:
                if (currentFragment != FRAGMENT_SETTINGS) {
                    getSupportActionBar().setTitle("Settings");
                    replaceFragment(new SettingsFragment());
                    currentFragment = FRAGMENT_SETTINGS;
                }
                break;
        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    private void replaceFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.contentFrame, fragment);
        transaction.commit();
    }
}