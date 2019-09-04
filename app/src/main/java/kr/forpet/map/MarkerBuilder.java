package kr.forpet.map;

import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import kr.forpet.R;
import kr.forpet.data.entity.Shop;

public class MarkerBuilder {

    private LatLng mLatLng;

    private String mCategory;
    private String mHash;
    private String mTitle;
    private String mEvent;
    private String mSale;

    public MarkerBuilder(LatLng latLng) {
        this.mLatLng = latLng;
    }

    public MarkerBuilder category(String category) {
        this.mCategory = category;
        return this;
    }

    public MarkerBuilder hash(String hash) {
        this.mHash = hash;
        return this;
    }

    // TODO: remove after
    public MarkerBuilder title(String title) {
        this.mTitle = title;
        return this;
    }

    public MarkerBuilder event(String event) {
        this.mEvent = event;
        return this;
    }

    public MarkerBuilder sale(String sale) {
        this.mSale = sale;
        return this;
    }

    public MarkerOptions build() {
        MarkerOptions marker = new MarkerOptions();
        marker.position(mLatLng);
        marker.title(mTitle);
        marker.snippet(mHash);
        marker.icon(BitmapDescriptorFactory.fromResource(MarkerBuilder.getIcon(mCategory)));

        return marker;
    }

    public static int getIcon(String category) {
        int drawable = 0;

        switch (Shop.CategoryGroupCode.compare(category)) {
            case SHOP:
                drawable = R.drawable.marker_shop;
                break;
            case PHARM:
                drawable = R.drawable.marker_pharm;
                break;
            case HOSPITAL:
                drawable = R.drawable.marker_hospital;
                break;
            case DOGPENSION:
                drawable = R.drawable.marker_dogpension;
                break;
            case DOGGROUND:
                drawable = R.drawable.marker_dogground;
                break;
            case DOGCAFE:
                drawable = R.drawable.marker_cafe;
                break;
            case CATCAFE:
                drawable = R.drawable.marker_cafe;
                break;
            case BEAUTY:
                drawable = R.drawable.marker_beauty;
                break;
        }

        return drawable;
    }
}