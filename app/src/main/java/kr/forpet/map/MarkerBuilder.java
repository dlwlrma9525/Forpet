package kr.forpet.map;

import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import kr.forpet.R;
import kr.forpet.data.entity.Shop;

public class MarkerBuilder {
    private LatLng mLatLng;

    private String mCategoryGroupCode = null;
    private String mForpetHash = null;
    private String mPlaceName = null;

    public MarkerBuilder(LatLng latLng) {
        this.mLatLng = latLng;
    }

    public MarkerBuilder categoryGroupCode(String categoryGroupCode) {
        this.mCategoryGroupCode = categoryGroupCode;
        return this;
    }

    public MarkerBuilder forpetHash(String forpetHash) {
        this.mForpetHash = forpetHash;
        return this;
    }

    public MarkerBuilder placeName(String placeName) {
        this.mPlaceName = placeName;
        return this;
    }

    public MarkerOptions build() {
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(mLatLng);
        markerOptions.icon(BitmapDescriptorFactory.fromResource(getIcon(mCategoryGroupCode)));

        if (mPlaceName != null) {
            markerOptions.title(mPlaceName);
        }

        if (mForpetHash != null) {
            markerOptions.snippet(mForpetHash);
        }

        return markerOptions;
    }

    private int getIcon(String category) {
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