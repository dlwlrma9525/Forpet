package kr.forpet.view.main.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import kr.forpet.R;
import kr.forpet.data.PetShop;
import kr.forpet.util.Permission;
import kr.forpet.view.main.presenter.MainPresenter;
import kr.forpet.view.main.presenter.MainPresenterImpl;
import kr.forpet.view.request.activity.RequestActivity;

public class MainActivity extends AppCompatActivity
        implements MainPresenter.View, OnMapReadyCallback {

    private static final String[] PERMISSION_ARRAY
            = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};
    private static final int REQUEST_PERMISSION = 0;

    private MainPresenter mMainPresenter;
    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mMainPresenter = new MainPresenterImpl();
        mMainPresenter.setView(this);
        mMainPresenter.onCreate(getApplicationContext());

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (Permission.checkPermission(this, PERMISSION_ARRAY, REQUEST_PERMISSION))
                init();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMainPresenter.onDestroy();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (Permission.onCheckResult(grantResults))
            init();
        else
            finish();
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMainPresenter.onMapReady(googleMap);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START))
            drawer.closeDrawer(GravityCompat.START);
        else
            super.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.toolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_search) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    // MainPresenter.View implements..

    @Override
    public Context getContext() {
        return getApplicationContext();
    }

    @Override
    public void addMarker(MarkerOptions marker) {
        // Add a marker and move the camera..
        mMap.addMarker(marker);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(marker.getPosition()));
    }

    @Override
    public void moveCamera(LatLng latLng) {
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15.0f));
    }

    private void init() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(false);
        // actionBar.setDisplayHomeAsUpEnabled(true);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        initNavigationView(navigationView);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        toggle.setDrawerIndicatorEnabled(false);
        toggle.setHomeAsUpIndicator(R.drawable.icon_menu_hamburg);
        toggle.setToolbarNavigationClickListener((v) -> {
            if (drawer.isDrawerVisible(GravityCompat.START))
                drawer.closeDrawer(GravityCompat.START);
            else
                drawer.openDrawer(GravityCompat.START);
        });

        FloatingActionButton fabDiagnosis = findViewById(R.id.fab_diagnosis);
        FloatingActionButton fabSearchPharm = findViewById(R.id.fab_search_pharmacy);
        FloatingActionButton fabSearchMeal = findViewById(R.id.fab_search_meal);
        fabDiagnosis.hide();
        fabSearchPharm.hide();
        fabSearchMeal.hide();

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation_view);
        bottomNavigationView.setOnNavigationItemSelectedListener((@NonNull MenuItem item) -> {
            if (fabDiagnosis.isShown()) {
                fabDiagnosis.hide();
                fabSearchPharm.hide();
                fabSearchMeal.hide();
            }

            switch (item.getItemId()) {
                case R.id.action_supplies:
                    return true;
                case R.id.action_pharm:
                    return true;
                case R.id.action_hospital:
                    return true;
                case R.id.action_hair:
                    return true;
                case R.id.action_more:
                    fabDiagnosis.show();
                    fabSearchPharm.show();
                    fabSearchMeal.show();
                    return true;
            }

            return false;
        });

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        findViewById(R.id.image_button_gps)
                .setOnClickListener((v) -> mMainPresenter.onMyGps());
    }

    private void initNavigationView(NavigationView navigationView) {
        navigationView.findViewById(R.id.button_hosp).setOnClickListener((v) -> {
        });

        navigationView.findViewById(R.id.button_pharmacy).setOnClickListener((v) -> {
        });

        navigationView.findViewById(R.id.button_shop).setOnClickListener((v) -> {
            Intent intent = new Intent(this, RequestActivity.class);
            intent.putExtra("type", PetShop.class);
            startActivity(intent);
        });

        navigationView.findViewById(R.id.button_advertisement).setOnClickListener((v) -> {
        });
    }
}
