package kr.forpet.view.main.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
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
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import kr.forpet.R;
import kr.forpet.data.entity.Shop;
import kr.forpet.util.Permission;
import kr.forpet.view.main.presenter.MainPresenter;
import kr.forpet.view.main.presenter.MainPresenterImpl;
import kr.forpet.view.regist.RegistActivity;

public class MainActivity extends AppCompatActivity
        implements MainPresenter.View, OnMapReadyCallback {

    private static final String[] PERMISSION_ARRAY
            = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.CALL_PHONE};
    private static final int REQUEST_PERMISSION = 0;

    private MainPresenter mMainPresenter;
    private GoogleMap mMap;

    private BottomSheetBehavior persistentBottomSheet;

    private List<Marker> cache = new ArrayList<>();
    private float zoom = 15.0f;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.drawer_layout)
    DrawerLayout drawer;

    @BindView(R.id.nav_view)
    NavigationView navigationView;

    @BindView(R.id.bottom_navigation_view)
    BottomNavigationView bottomNavigationView;

    @BindView(R.id.include_bottom_sheet)
    ViewGroup bottomSheetView;

    @BindView(R.id.include_popup)
    ViewGroup popupView;

    @BindView(R.id.dim_effect)
    View dimEffectView;

    @BindView(R.id.fab_diagnosis)
    FloatingActionButton fabDiagnosis;

    @BindView(R.id.fab_search_pharmacy)
    FloatingActionButton fabSearchPharmacy;

    @BindView(R.id.fab_search_meal)
    FloatingActionButton fabSearchMeal;

    @BindView(R.id.button_my_locate)
    ImageButton buttonMyLocate;

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

            Shop.CatCode catCode = Shop.CatCode.SHOP;
            switch (bottomNavigationView.getSelectedItemId()) {
                case R.id.action_supplies:
                    catCode = Shop.CatCode.SHOP;
                    break;
                case R.id.action_pharm:
                    catCode = Shop.CatCode.PHARM;
                    break;
                case R.id.action_hospital:
                    catCode = Shop.CatCode.HOSPITAL;
                    break;
                case R.id.action_hair:
                    catCode = Shop.CatCode.BEAUTY;
                    break;
            }

            if (mMap.getCameraPosition().zoom < zoom) {
                cache.clear();
                mMap.clear();
            }

            zoom = mMap.getCameraPosition().zoom;

            Projection projection = mMap.getProjection();
            LatLngBounds latLngBounds = projection.getVisibleRegion().latLngBounds;

            mMainPresenter.onMapSearch(catCode, latLngBounds);
        });

        googleMap.setOnMarkerClickListener((marker) -> {
            mMainPresenter.onMarkerClick(marker);
            return false;
        });

        mMainPresenter.onMyLocate((@NonNull Task<Location> task) -> {
            if (task.isSuccessful()) {
                Location result = task.getResult();
                LatLng latLng = new LatLng(result.getLatitude(), result.getLongitude());

                MarkerOptions markerOptions = new MarkerOptions();
                markerOptions.position(latLng);
                markerOptions.snippet("Me");

                addMarker(markerOptions);
                moveCamera(latLng);
            } else {
                Log.d("GooglePlayServices", "Current location is null. Using defaults.");
                Log.e("GooglePlayServices", "Exception: %s", task.getException());
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else if (popupView.getVisibility() == View.VISIBLE) {
            if (persistentBottomSheet.getState() == BottomSheetBehavior.STATE_EXPANDED)
                persistentBottomSheet.setState(BottomSheetBehavior.STATE_COLLAPSED);

            popupView.setVisibility(View.GONE);
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
    public void showPopup(Shop shop) {
        if (shop != null) {
            TextView textPlaceName = popupView.findViewById(R.id.text_popup_place);
            TextView textAddressName = popupView.findViewById(R.id.text_popup_address);
            Button buttonNavigation = popupView.findViewById(R.id.button_popup_navigation);
            Button buttonCall = popupView.findViewById(R.id.button_popup_call);

            textPlaceName.setText(shop.getPlaceName());
            textAddressName.setText(shop.getRoadAddressName());

            buttonNavigation.setOnClickListener((v) -> mMainPresenter.onMyLocate((@NonNull Task<Location> task) -> {
                if (task.isSuccessful()) {
                    Location result = task.getResult();
                    LatLng latLng = new LatLng(result.getLatitude(), result.getLongitude());

                    StringBuilder sb = new StringBuilder("http://maps.google.com/maps?")
                            .append("saddr=").append(latLng.latitude).append(",").append(latLng.longitude)
                            .append("&daddr=").append(shop.getY()).append(",").append(shop.getX());

                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(sb.toString()));
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.addCategory(Intent.CATEGORY_LAUNCHER);
                    intent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");

                    startActivity(intent);
                }
            }));

            buttonCall.setOnClickListener((v) -> {
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + shop.getPhone()));
                startActivity(intent);
            });

            popupView.setOnClickListener((v) -> persistentBottomSheet.setState(BottomSheetBehavior.STATE_EXPANDED));
            popupView.setVisibility(View.VISIBLE);
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

        navigationView.findViewById(R.id.button_regist_hosp).setOnClickListener((v) -> {
        });

        navigationView.findViewById(R.id.button_regist_pharmacy).setOnClickListener((v) -> {
        });

        navigationView.findViewById(R.id.button_regist_shop).setOnClickListener((v) -> {
            Intent intent = new Intent(this, RegistActivity.class);
            intent.putExtra("type", Shop.class);
            startActivity(intent);
        });

        navigationView.findViewById(R.id.button_regist_advertisement).setOnClickListener((v) -> {
        });

        bottomNavigationView.setOnNavigationItemSelectedListener((@NonNull MenuItem item) -> {
            if (item.getItemId() == R.id.action_more) {
                if (fabDiagnosis.isShown()) {
                    fabDiagnosis.hide();
                    fabSearchPharmacy.hide();
                    fabSearchMeal.hide();
                } else {
                    fabDiagnosis.show();
                    fabSearchPharmacy.show();
                    fabSearchMeal.show();
                }
            } else {
                if (fabDiagnosis.isShown()) {
                    fabDiagnosis.hide();
                    fabSearchPharmacy.hide();
                    fabSearchMeal.hide();
                }

                if (popupView.getVisibility() == View.VISIBLE)
                    popupView.setVisibility(View.GONE);

                Projection projection = mMap.getProjection();
                LatLngBounds latLngBounds = projection.getVisibleRegion().latLngBounds;

                cache.clear();
                mMap.clear();
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLngBounds.getCenter(), mMap.getCameraPosition().zoom));
            }

            return true;
        });

        persistentBottomSheet = BottomSheetBehavior.from(bottomSheetView);
        persistentBottomSheet.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                if (newState == BottomSheetBehavior.STATE_SETTLING) {
                    if (dimEffectView.getVisibility() == View.GONE)
                        dimEffectView.setVisibility(View.VISIBLE);
                    else
                        dimEffectView.setVisibility(View.GONE);
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

            }
        });

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

        buttonMyLocate.setOnClickListener((v) -> mMainPresenter.onMyLocate((@NonNull Task<Location> task) -> {
            if (task.isSuccessful()) {
                Location result = task.getResult();
                LatLng latLng = new LatLng(result.getLatitude(), result.getLongitude());

                MarkerOptions markerOptions = new MarkerOptions();
                markerOptions.position(latLng);
                markerOptions.snippet("Me");

                addMarker(markerOptions);
                moveCamera(latLng);
            } else {
                Log.d("GooglePlayServices", "Current location is null. Using defaults.");
                Log.e("GooglePlayServices", "Exception: %s", task.getException());
            }
        }));
    }
}