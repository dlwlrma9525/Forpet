package kr.forpet.view.main.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

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
import com.google.android.gms.maps.Projection;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import kr.forpet.R;
import kr.forpet.data.entity.ForpetShop;
import kr.forpet.util.Permission;
import kr.forpet.view.main.presenter.MainPresenter;
import kr.forpet.view.main.presenter.MainPresenterImpl;
import kr.forpet.view.request.RequestActivity;

public class MainActivity extends AppCompatActivity
        implements MainPresenter.View, OnMapReadyCallback {

    private static final String[] PERMISSION_ARRAY
            = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};
    private static final int REQUEST_PERMISSION = 0;

    private MainPresenter mMainPresenter;
    private BottomSheetBehavior bottomSheetBehavior;
    private GoogleMap mMap;

    private List<Marker> cache = new ArrayList();

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.drawer_layout)
    DrawerLayout drawer;

    @BindView(R.id.nav_view)
    NavigationView navigationView;

    @BindView(R.id.bottom_navigation_view)
    BottomNavigationView bottomNavigationView;

    @BindView(R.id.include_pop_up)
    ViewGroup popUpView;

    @BindView(R.id.include_card)
    ViewGroup cardView;

    @BindView(R.id.fab_diagnosis)
    FloatingActionButton fabDiagnosis;

    @BindView(R.id.fab_search_pharmacy)
    FloatingActionButton fabSearchPharmacy;

    @BindView(R.id.fab_search_meal)
    FloatingActionButton fabSearchMeal;

    @BindView(R.id.image_button_gps)
    ImageButton buttonGps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

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

        googleMap.setOnCameraIdleListener(() -> {
            // Called when camera movement has ended, there are no pending animations and the user has stopped interacting with the map.

            ForpetShop.CatCode catCode = ForpetShop.CatCode.SHOP;
            switch (bottomNavigationView.getSelectedItemId()) {
                case R.id.action_supplies:
                    catCode = ForpetShop.CatCode.SHOP;
                    break;
                case R.id.action_pharm:
                    catCode = ForpetShop.CatCode.PHARM;
                    break;
                case R.id.action_hospital:
                    catCode = ForpetShop.CatCode.HOSPITAL;
                    break;
                case R.id.action_hair:
                    catCode = ForpetShop.CatCode.BEAUTY;
                    break;
            }

            Projection projection = mMap.getProjection();
            LatLngBounds latLngBounds = projection.getVisibleRegion().latLngBounds;

            mMainPresenter.onMapSearch(catCode, latLngBounds);
        });

        googleMap.setOnMarkerClickListener((marker) -> {
            mMainPresenter.onMarkerClick(marker);
            return false;
        });
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else if (cardView.getVisibility() == View.VISIBLE) {
            if (bottomSheetBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED)
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);

            cardView.setVisibility(View.GONE);
        } else {
            super.onBackPressed();
        }
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
    public void addMarker(MarkerOptions markerOptions) {
        // Add a marker and move the camera..
        for (Marker marker : cache)
            if (marker.getPosition().equals(markerOptions.getPosition()))
                return;

        cache.add(mMap.addMarker(markerOptions));
    }

    @Override
    public void moveCamera(LatLng latLng) {
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15.0f));
    }

    @Override
    public void showCard(ForpetShop shop) {
        if (shop != null) {
            TextView textPlaceName = cardView.findViewById(R.id.text_place_name);
            TextView textAddressName = cardView.findViewById(R.id.text_address_name);
            Button buttonNavigation = cardView.findViewById(R.id.button_navigation);
            Button buttonCall = cardView.findViewById(R.id.button_call);

            textPlaceName.setText(shop.getPlaceName());
            textAddressName.setText(shop.getRoadAddressName());

            buttonNavigation.setOnClickListener((v) -> {
//                StringBuilder sb = new StringBuilder("http://maps.google.com/maps?");
//
//                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(sb.toString()));
//                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                intent.addCategory(Intent.CATEGORY_LAUNCHER);
//                intent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");
//
//                startActivity(intent);
            });

            buttonCall.setOnClickListener((v) -> {

            });

            cardView.setOnClickListener((v) -> {
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
            });

            cardView.setVisibility(View.VISIBLE);
        }
    }

    private void init() {
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(false);
        // actionBar.setDisplayHomeAsUpEnabled(true);

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

        navigationView.findViewById(R.id.button_hosp).setOnClickListener((v) -> {
        });

        navigationView.findViewById(R.id.button_pharmacy).setOnClickListener((v) -> {
        });

        navigationView.findViewById(R.id.button_shop).setOnClickListener((v) -> {
            Intent intent = new Intent(this, RequestActivity.class);
            intent.putExtra("type", ForpetShop.class);
            startActivity(intent);
        });

        navigationView.findViewById(R.id.button_advertisement).setOnClickListener((v) -> {
        });

        bottomNavigationView.setOnNavigationItemSelectedListener((@NonNull MenuItem item) -> {
            if (fabDiagnosis.isShown()) {
                fabDiagnosis.hide();
                fabSearchPharmacy.hide();
                fabSearchMeal.hide();
            }

            if (item.getItemId() == R.id.action_more) {
                fabDiagnosis.show();
                fabSearchPharmacy.show();
                fabSearchMeal.show();
            } else {
                if (cardView.getVisibility() == View.VISIBLE)
                    cardView.setVisibility(View.GONE);

                Projection projection = mMap.getProjection();
                LatLngBounds latLngBounds = projection.getVisibleRegion().latLngBounds;

                cache.clear();
                mMap.clear();
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLngBounds.getCenter(), mMap.getCameraPosition().zoom));
            }

            return true;
        });

        bottomSheetBehavior = BottomSheetBehavior.from(popUpView);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        fabDiagnosis.hide();
        fabDiagnosis.setOnClickListener((v) -> {
        });

        fabSearchPharmacy.hide();
        fabSearchPharmacy.setOnClickListener((v) -> {
        });

        fabSearchMeal.hide();
        fabSearchMeal.setOnClickListener((v) -> {
        });

        buttonGps.setOnClickListener((v) -> mMainPresenter.onRequestGps());
    }
}