package kr.forpet.view.main.adapter;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.net.Uri;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.Task;

import java.util.List;

import kr.forpet.R;
import kr.forpet.data.entity.Shop;

public class PopupViewPagerAdapter extends PagerAdapter {

    private Context mContext;
    private List<Shop> mList;

    public PopupViewPagerAdapter(Context context, List<Shop> list) {
        this.mContext = context;
        this.mList = list;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        // super.instantiateItem(container, position);
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(R.layout.item_popup, container, false);
        itemView.setOnClickListener((v) -> onItemListener.onItemClick(mList.get(position)));

        Shop shop = mList.get(position);

        TextView textDistance = itemView.findViewById(R.id.text_item_distance);
        textDistance.setText(String.format("%.2f km", shop.getDistance() / 1000));

        TextView textPlace = itemView.findViewById(R.id.text_item_place);
        textPlace.setText(shop.getPlaceName());

        TextView textAddress = itemView.findViewById(R.id.text_item_address);
        textAddress.setText(shop.getRoadAddressName());

        GridLayout grid = itemView.findViewById(R.id.grid_item_opt);

        if (shop.getOptParking() != null)
            grid.addView(createOptionView(R.drawable.enable_parking));
        if (shop.getOptReservation() != null)
            grid.addView(createOptionView(R.drawable.enable_reservation));
        if (shop.getOptWifi() != null)
            grid.addView(createOptionView(R.drawable.enable_wifi));
        if (shop.getOpt365().equals("Y"))
            grid.addView(createOptionView(R.drawable.enable_365));
        if (shop.getOptNight().equals("Y"))
            grid.addView(createOptionView(R.drawable.enable_night));
        if (shop.getOptShop().equals("Y"))
            grid.addView(createOptionView(R.drawable.enable_shop));
        if (shop.getOptBeauty().equals("Y"))
            grid.addView(createOptionView(R.drawable.enable_beauty));
        if (shop.getOptBigdog().equals("Y"))
            grid.addView(createOptionView(R.drawable.enable_bigdog));
        if (shop.getOptHotel().equals("Y"))
            grid.addView(createOptionView(R.drawable.enable_hotel));

        Button buttonNavigation = itemView.findViewById(R.id.button_item_navigation);
        buttonNavigation.setOnClickListener((v) -> {
            try {
                FusedLocationProviderClient client = LocationServices.getFusedLocationProviderClient(mContext);
                client.getLastLocation().addOnCompleteListener((@NonNull Task<Location> task) -> {
                    if (task.isSuccessful()) {
                        Location lastLocation = task.getResult();
                        LatLng start = new LatLng(lastLocation.getLatitude(), lastLocation.getLongitude());

                        String uri = new StringBuilder("http://maps.google.com/maps?")
                                .append("saddr=").append(start.latitude).append(",").append(start.longitude)
                                .append("&daddr=").append(shop.getY()).append(",").append(shop.getX())
                                .toString();

                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.addCategory(Intent.CATEGORY_LAUNCHER);
                        intent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");

                        mContext.startActivity(intent);
                    }
                });
            } catch (SecurityException e) {
                e.printStackTrace();
            }
        });

        Button buttonCall = itemView.findViewById(R.id.button_item_call);
        buttonCall.setOnClickListener((v) -> {
            Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + shop.getPhone()));
            mContext.startActivity(intent);
        });

        container.addView(itemView);
        return itemView;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        // super.destroyItem(container, position, object);
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    private ImageView createOptionView(int resourceId) {
        FrameLayout.LayoutParams params
                = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        DisplayMetrics metrics = mContext.getResources().getDisplayMetrics();
        params.height = Math.round(21 * metrics.density);
        params.width = Math.round(21 * metrics.density);
        params.setMarginEnd(Math.round(5 * metrics.density));

        ImageView image = new ImageView(mContext);
        image.setImageResource(resourceId);
        image.setLayoutParams(params);

        return image;
    }

    private OnItemListener onItemListener;

    public void setOnItemListener(OnItemListener onItemListener) {
        this.onItemListener = onItemListener;
    }

    public interface OnItemListener {
        void onItemClick(Shop shop);
    }
}