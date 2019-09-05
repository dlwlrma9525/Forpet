package kr.forpet.view.main.activity;

import android.Manifest;
import android.content.Intent;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
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
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.ViewCollections;
import kr.forpet.R;
import kr.forpet.data.entity.Shop;
import kr.forpet.util.Permission;
import kr.forpet.view.main.adapter.PopupViewPagerAdapter;
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
    private BottomSheetBehavior mPersistentBottomSheet;

    private List<Marker> mMarkerCache = new ArrayList<>();
    private Marker mMarker;

    private float zoom = 15.0f;
    private boolean onCameraIdleInPageSelected;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.drawer_layout)
    DrawerLayout drawer;

    @BindView(R.id.nav_view)
    NavigationView navigationView;

    @BindView(R.id.bottom_navigation_view)
    BottomNavigationView bottomNavigationView;

    @BindView(R.id.view_pager_popup)
    ViewPager popupViewPager;

    @BindView(R.id.include_bottom_sheet)
    ViewGroup bottomSheetView;

    @BindView(R.id.view_dim)
    View dimView;

    @BindView(R.id.button_my_locate)
    ImageButton buttonMyLocate;

    @BindViews({R.id.fab_diagnosis, R.id.fab_search_pharmacy, R.id.fab_search_meal})
    List<FloatingActionButton> floatingActionButtons;

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

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMainPresenter.onMapReady(googleMap);

        googleMap.setOnMarkerClickListener((marker) -> {
            if (mMarker != null) {
                BitmapDescriptor snapshot = (BitmapDescriptor) mMarker.getTag();
                mMarker.setIcon(snapshot);
            }

            mMarker = marker;
            mMarker.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.marker_current));

            mMainPresenter.onMarkerClick();
            return false;
        });

        googleMap.setOnCameraIdleListener(() -> {
            // Called when camera movement has ended, there are no pending animations and the user has stopped interacting with the map.
            LatLngBounds latLngBounds = mMap.getProjection().getVisibleRegion().latLngBounds;
            MenuItem item = bottomNavigationView.getMenu().findItem(bottomNavigationView.getSelectedItemId());

            if (onCameraIdleInPageSelected) {
                onCameraIdleInPageSelected = false;
            } else {
                if (popupViewPager.getVisibility() != View.GONE) {
                    if (zoom != mMap.getCameraPosition().zoom)
                        zoom = mMap.getCameraPosition().zoom;
                    else
                        mMainPresenter.onMapUpdate(latLngBounds, item);
                } else {
                    mMainPresenter.onMapUpdate(latLngBounds, item);
                }
            }
        });

        mMainPresenter.onMyLocate((@NonNull Task<Location> task) -> {
            if (task.isSuccessful()) {
                Location result = task.getResult();
                LatLng latLng = new LatLng(result.getLatitude(), result.getLongitude());

                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));
            } else {
                Log.d("GooglePlayServices", "Current location is null. Using defaults.");
                Log.e("GooglePlayServices", "Exception: %s", task.getException());
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START))
            drawer.closeDrawer(GravityCompat.START);
        else
            super.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_search)
            return true;

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void updatePopup(List<Shop> shopList) {
        PagerAdapter adapter = new PopupViewPagerAdapter(getApplicationContext(), shopList);
        popupViewPager.setAdapter(adapter);
        popupViewPager.setVisibility(View.VISIBLE);
        popupViewPager.setCurrentItem(mMarkerCache.indexOf(mMarker));
    }

    @Override
    public void updateMap(List<MarkerOptions> markerOptionsList) {
        for (MarkerOptions markerOptions : markerOptionsList) {
            if (mMarker != null) {
                if (mMarker.getPosition().equals(markerOptions.getPosition())) {
                    mMarkerCache.add(mMarker);
                    continue;
                }
            }

            Marker marker = mMap.addMarker(markerOptions);
            BitmapDescriptor snapshot = markerOptions.getIcon();
            marker.setTag(snapshot);

            mMarkerCache.add(marker);
        }
    }

    @Override
    public void clearMap() {
        for (Marker marker : mMarkerCache) {
            if (marker.equals(mMarker))
                continue;

            marker.remove();
        }

        mMarkerCache.clear();

        popupViewPager.removeAllViews();
        popupViewPager.setVisibility(View.GONE);
    }

    private void init() {
        initActionBar();
        initGoogleMap();
        initPopupViewPager();
        initNavigationView();
        initBottomNavigationView();
    }

    private void initActionBar() {
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
    }

    private void initGoogleMap() {
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        buttonMyLocate.setOnClickListener((v) -> mMainPresenter.onMyLocate((@NonNull Task<Location> task) -> {
            if (task.isSuccessful()) {
                Location result = task.getResult();
                LatLng latLng = new LatLng(result.getLatitude(), result.getLongitude());

                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));
            } else {
                Log.d("GooglePlayServices", "Current location is null. Using defaults.");
                Log.e("GooglePlayServices", "Exception: %s", task.getException());
            }
        }));
    }

    private void initPopupViewPager() {
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        popupViewPager.setVisibility(View.GONE);
        popupViewPager.setClipToPadding(false);
        popupViewPager.setPageMargin(Math.round(5 * metrics.density));
        popupViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                BitmapDescriptor snapshot = (BitmapDescriptor) mMarker.getTag();
                mMarker.setIcon(snapshot);

                mMarker = mMarkerCache.get(position);
                mMarker.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.marker_current));
                mMarker.showInfoWindow();

                onCameraIdleInPageSelected = true;
                mMap.animateCamera(CameraUpdateFactory.newLatLng(mMarker.getPosition()));
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void initNavigationView() {
        navigationView.findViewById(R.id.button_regist_shop).setOnClickListener((v) -> {
            Intent intent = new Intent(this, RegistActivity.class);
            startActivity(intent);
        });
    }

    private void initBottomNavigationView() {
        ViewCollections.run(floatingActionButtons, (v, i) -> v.hide());

        bottomNavigationView.setOnNavigationItemSelectedListener((@NonNull MenuItem item) -> {
            if (item.getItemId() == R.id.action_more) {
                ViewCollections.run(floatingActionButtons, (v, i) -> {
                    if (v.isShown())
                        v.hide();
                    else
                        v.show();
                });
            } else {
                ViewCollections.run(floatingActionButtons, (v, i) -> {
                    if (v.isShown())
                        v.hide();
                });

                mMarker = null;
                mMarkerCache.clear();
                mMap.clear();
                popupViewPager.removeAllViews();

                LatLngBounds latLngBounds = mMap.getProjection().getVisibleRegion().latLngBounds;
                mMainPresenter.onMapUpdate(latLngBounds, item);
            }

            return true;
        });

        mPersistentBottomSheet = BottomSheetBehavior.from(bottomSheetView);
        mPersistentBottomSheet.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                if (newState == BottomSheetBehavior.STATE_SETTLING) {
                    if (dimView.getVisibility() == View.GONE)
                        dimView.setVisibility(View.VISIBLE);
                    else
                        dimView.setVisibility(View.GONE);
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

            }
        });
    }
}