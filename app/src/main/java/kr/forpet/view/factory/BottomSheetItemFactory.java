package kr.forpet.view.factory;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.location.Location;
import android.net.Uri;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.Task;

import java.lang.reflect.Field;

import butterknife.BindView;
import butterknife.ButterKnife;
import kr.forpet.R;
import kr.forpet.data.entity.Shop;
import kr.forpet.data.entity.ShopOpenTime;

public class BottomSheetItemFactory implements ItemViewFactory {

    private ViewHolder mHolder;
    private Shop mShop;

    public BottomSheetItemFactory(Shop shop) {
        this.mShop = shop;
    }

    @Override
    public View createView(Context context) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View contentView = inflater.inflate(R.layout.layout_bottom_sheet, null);

        mHolder = new ViewHolder(contentView);
        mHolder.textPlace.setText(mShop.getPlaceName());
        mHolder.textAddress.setText(mShop.getRoadAddressName());
        mHolder.textPhone.setText(mShop.getPhone());
        mHolder.textHomepage.setText(mShop.getHomepage());

        createOptionView(context, mHolder, mShop);

        StringBuilder sb = new StringBuilder();
        for (ShopOpenTime time : mShop.getShopOpenTimeList()) {
            if (time.getType().equals("영업일"))
                sb.append(time.getDay()).append(" : ").append(time.getPeriod()).append(" ");
            else
                sb.append(time.getType()).append(" : ").append(time.getDay()).append(" ");

            if (time.getRemarks() != null)
                sb.append(time.getRemarks());

            sb.append(System.getProperty("line.separator"));
        }
        mHolder.textTime.setText(sb.toString());

        if (mShop.getAdditionalInfo() != null) {
            // do something..
        } else {
            mHolder.layoutInfo.setVisibility(View.GONE);
        }

        if (mShop.getCategoryGroupCode().equals(Shop.CategoryGroupCode.PHARM.toString())) {
            TextView textMedicine = contentView.findViewById(R.id.text_sheet_medicine);
            textMedicine.setText(mShop.getShopPharmacy().getMedicineCategoriesForSale());
        } else {
            mHolder.layoutMedicine.setVisibility(View.GONE);
        }

        CheckBox.OnCheckedChangeListener onCheckedChangeListener = (v, isChecked) -> {
            if (isChecked)
                v.setTextColor(Color.parseColor("#4b4b4b"));
            else
                v.setTextColor(Color.parseColor("#d6d6d6"));
        };

        mHolder.cbRecommendConsensus.setOnCheckedChangeListener(onCheckedChangeListener);
        mHolder.cbRecommendFacility.setOnCheckedChangeListener(onCheckedChangeListener);
        mHolder.cbRecommendPlace.setOnCheckedChangeListener(onCheckedChangeListener);

        if (mShop.getIntro() != null)
            mHolder.textIntro.setText(mShop.getIntro().replace("\\r\\n", System.getProperty("line.separator")));

        mHolder.buttonNavigation.setOnClickListener((v) -> {
            try {
                FusedLocationProviderClient client = LocationServices.getFusedLocationProviderClient(context);
                client.getLastLocation().addOnCompleteListener((@NonNull Task<Location> task) -> {
                    if (task.isSuccessful()) {
                        Location lastLocation = task.getResult();
                        LatLng start = new LatLng(lastLocation.getLatitude(), lastLocation.getLongitude());

                        String uri = new StringBuilder("http://maps.google.com/maps?")
                                .append("saddr=").append(start.latitude).append(",").append(start.longitude)
                                .append("&daddr=").append(mShop.getY()).append(",").append(mShop.getX())
                                .toString();

                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.addCategory(Intent.CATEGORY_LAUNCHER);
                        intent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");

                        context.startActivity(intent);
                    }
                });
            } catch (SecurityException e) {
                e.printStackTrace();
            }
        });

        mHolder.buttonCall.setOnClickListener((v) -> {
            Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + mShop.getPhone()));
            context.startActivity(intent);
        });

        return contentView;
    }

    private void createOptionView(Context context, ViewHolder holder, Shop shop) {
        FrameLayout.LayoutParams params
                = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        params.setMargins(0, 0, Math.round(10 * metrics.density), Math.round(10 * metrics.density));

        try {
            Class clazz = mShop.getClass();

            for (Field field : clazz.getDeclaredFields()) {
                String fieldName = field.getName();

                if (fieldName.contains("opt")) {
                    field.setAccessible(true);
                    String value = (String) field.get(shop);

                    if (value != null && value.equals("Y")) {
                        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        View itemView = inflater.inflate(R.layout.item_opt, null);
                        itemView.setLayoutParams(params);

                        ImageView image = itemView.findViewById(R.id.image_item_opt);
                        TextView text = itemView.findViewById(R.id.text_item_opt);

                        int resourceId;
                        String description;

                        if (fieldName.contains("Parking")) {
                            resourceId = R.drawable.enable_parking;
                            description = "주차";
                        } else if (fieldName.contains("Reservation")) {
                            resourceId = R.drawable.enable_reservation;
                            description = "예약";
                        } else if (fieldName.contains("Wifi")) {
                            resourceId = R.drawable.enable_wifi;
                            description = "무선인터넷";
                        } else if (fieldName.contains("365")) {
                            resourceId = R.drawable.enable_365;
                            description = "연중무휴";
                        } else if (fieldName.contains("Night")) {
                            resourceId = R.drawable.enable_night;
                            description = "연중무휴";
                        } else if (fieldName.contains("Shop")) {
                            resourceId = R.drawable.enable_shop;
                            description = "용품";
                        } else if (fieldName.contains("Beauty")) {
                            resourceId = R.drawable.enable_beauty;
                            description = "미용";
                        } else if (fieldName.contains("Bigdog")) {
                            resourceId = R.drawable.enable_bigdog;
                            description = "대형견 가능";
                        } else {
                            resourceId = R.drawable.enable_hotel;
                            description = "호텔";
                        }

                        image.setImageResource(resourceId);
                        text.setText(description);
                        holder.gridOpt.addView(itemView);
                    }
                }
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    class ViewHolder {

        @BindView(R.id.text_sheet_place_name)
        TextView textPlace;

        @BindView(R.id.text_sheet_address)
        TextView textAddress;

        @BindView(R.id.text_sheet_phone)
        TextView textPhone;

        @BindView(R.id.text_sheet_homepage)
        TextView textHomepage;

        @BindView(R.id.text_sheet_time)
        TextView textTime;

        @BindView(R.id.grid_sheet_opt)
        GridLayout gridOpt;

        @BindView(R.id.layout_sheet_info)
        LinearLayout layoutInfo;

        @BindView(R.id.layout_sheet_medicine)
        LinearLayout layoutMedicine;

        @BindView(R.id.cb_recommend_consensus)
        CheckBox cbRecommendConsensus;

        @BindView(R.id.cb_recommend_facility)
        CheckBox cbRecommendFacility;

        @BindView(R.id.cb_recommend_price)
        CheckBox cbRecommendPlace;

        @BindView(R.id.text_sheet_intro)
        TextView textIntro;

        @BindView(R.id.button_sheet_navigation)
        Button buttonNavigation;

        @BindView(R.id.button_sheet_call)
        Button buttonCall;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
