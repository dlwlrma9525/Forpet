package kr.forpet.view.main.activity;

import android.Manifest;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

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
import com.google.android.gms.maps.Projection;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
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
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.ViewCollections;
import kr.forpet.R;
import kr.forpet.data.entity.Shop;
import kr.forpet.map.MarkerBuilder;
import kr.forpet.util.Permission;
import kr.forpet.view.factory.BottomSheetViewFactory;
import kr.forpet.view.factory.ItemViewFactory;
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
    private Marker mClickedMarker;

    private boolean onPageSelected = false;

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

    @BindView(R.id.bottom_sheet)
    ViewGroup bottomSheet;

    @BindView(R.id.dim_effect)
    ViewGroup dimEffect;

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
            if(mClickedMarker != null) {
                try {
                    BitmapDescriptor snapshot = (BitmapDescriptor) mClickedMarker.getTag();
                    mClickedMarker.setIcon(snapshot);
                } catch (IllegalArgumentException e) {
                    mClickedMarker.remove();
                }
            }

            mClickedMarker = marker;
            mClickedMarker.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.marker_current));

            return false;
        });

        googleMap.setOnCameraIdleListener(() -> {
            // Called when camera movement has ended, there are no pending animations and the user has stopped interacting with the map.
            Projection projection = mMap.getProjection();
            LatLngBounds latLngBounds = projection.getVisibleRegion().latLngBounds;

            MenuItem item = bottomNavigationView.getMenu().findItem(bottomNavigationView.getSelectedItemId());
            mMainPresenter.onMapUpdate(latLngBounds, getCategoryGroupCode(item));
        });

        mMainPresenter.onMyLocate();
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else if (mPersistentBottomSheet.getState() == BottomSheetBehavior.STATE_COLLAPSED) {
            mPersistentBottomSheet.setState(BottomSheetBehavior.STATE_HIDDEN);
            dimEffect.setVisibility(View.GONE);
        } else {
            super.onBackPressed();
        }
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
    public void updateMyLocate(LatLng latLng) {
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15.0f));
    }

    @Override
    public void updateMap(List<Shop> shopList) {
        clearMap();

        boolean isPopup = false;
        for (Shop shop : shopList) {
            MarkerOptions markerOptions = new MarkerBuilder(new LatLng(shop.getY(), shop.getX()))
                    .categoryGroupCode(shop.getCategoryGroupCode())
                    .placeName(shop.getPlaceName())
                    .forpetHash(shop.getForpetHash())
                    .build();

            Marker marker = mMap.addMarker(markerOptions);
            marker.setTag(markerOptions.getIcon());

            if (mClickedMarker != null && mClickedMarker.getPosition().equals(marker.getPosition())) {
                mClickedMarker = marker;
                mClickedMarker.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.marker_current));

                isPopup = true;
            }

            mMarkerCache.add(marker);
        }

        if (isPopup) {
            Toast.makeText(this, mClickedMarker.getTitle(), Toast.LENGTH_SHORT).show();
            onPageSelected = true;

            popupViewPager.setAdapter(createPagerAdapter(shopList));
            popupViewPager.setCurrentItem(mMarkerCache.indexOf(mClickedMarker));
        }
    }

    public void clearMap() {
        mMap.clear();
        mMarkerCache.clear();

        popupViewPager.removeAllViews();
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

        buttonMyLocate.setOnClickListener((v) -> mMainPresenter.onMyLocate());
    }

    private void initPopupViewPager() {
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        popupViewPager.setClipToPadding(false);
        popupViewPager.setPageMargin(Math.round(5 * metrics.density));
        popupViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (onPageSelected) {
                    onPageSelected = false;
                } else {
                    onPageSelected = true;

                    mClickedMarker = mMarkerCache.get(position);
                    mMap.animateCamera(CameraUpdateFactory.newLatLng(mClickedMarker.getPosition()), 400, null);
                }
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

                mClickedMarker = null;

                Projection projection = mMap.getProjection();
                LatLngBounds latLngBounds = projection.getVisibleRegion().latLngBounds;
                mMainPresenter.onMapUpdate(latLngBounds, getCategoryGroupCode(item));
            }

            return true;
        });

        bottomSheet.setVisibility(View.VISIBLE);
        mPersistentBottomSheet = BottomSheetBehavior.from(bottomSheet);
        mPersistentBottomSheet.setState(BottomSheetBehavior.STATE_HIDDEN);
        mPersistentBottomSheet.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                switch (newState) {
                    case BottomSheetBehavior.STATE_SETTLING:
                        dimEffect.setVisibility(View.VISIBLE);
                        break;
                    case BottomSheetBehavior.STATE_HIDDEN:
                        dimEffect.setVisibility(View.GONE);
                        break;
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

            }
        });

        ViewCollections.run(floatingActionButtons, (v, i) -> v.hide());
    }

    private String getCategoryGroupCode(MenuItem item) {
        Shop.CategoryGroupCode code;
        switch (item.getItemId()) {
            case R.id.action_supplies:
                code = Shop.CategoryGroupCode.SHOP;
                break;
            case R.id.action_pharm:
                code = Shop.CategoryGroupCode.PHARM;
                break;
            case R.id.action_hospital:
                code = Shop.CategoryGroupCode.HOSPITAL;
                break;
            default:
                code = Shop.CategoryGroupCode.BEAUTY;
        }

        return code.toString();
    }

    private PagerAdapter createPagerAdapter(List<Shop> shopList) {
        PopupViewPagerAdapter adapter = new PopupViewPagerAdapter(getApplicationContext(), shopList);
        adapter.setOnItemListener((shop) -> {
            ItemViewFactory factory = new BottomSheetViewFactory(shop);

            bottomSheet.removeAllViews();
            bottomSheet.addView(factory.createView(getApplicationContext()));
            mPersistentBottomSheet.setState(BottomSheetBehavior.STATE_COLLAPSED);
        });

        return adapter;
    }
}