package kr.forpet.view.factory;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import kr.forpet.R;
import kr.forpet.data.entity.Shop;
import kr.forpet.data.entity.ShopOpenTime;
import kr.forpet.map.GpsManager;

public class BottomSheetItemFactory implements ItemViewFactory {

    private Shop mData;

    public BottomSheetItemFactory(Shop shop) {
        this.mData = shop;
    }

    @Override
    public View createView(Context context) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View contentView = inflater.inflate(R.layout.layout_bottom_sheet, null);
        createOptionView(context, contentView, mData);


        SharedPreferences sharedPref
                = context.getSharedPreferences(context.getString(R.string.shared_name), Context.MODE_PRIVATE);
        String sharedData = sharedPref.getString(context.getString(R.string.shared_key_favorite), "");

        Gson gson = new Gson();
        String[] json = gson.fromJson(sharedData, String[].class);
        List<String> favorites = new ArrayList<>(Arrays.asList(json));

        CheckBox cbFavorite = contentView.findViewById(R.id.cb_sheet_favorite);
        cbFavorite.setChecked(favorites.contains(mData.getForpetHash()));
        cbFavorite.setOnCheckedChangeListener((v, isChecked) -> {
            if (isChecked) {
                favorites.add(mData.getForpetHash());
            } else {
                favorites.remove(mData.getForpetHash());
            }

            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putString(context.getString(R.string.shared_key_favorite), gson.toJson(favorites.toArray()));
            editor.commit();
        });


        ImageView imagePhoto = contentView.findViewById(R.id.image_sheet_photo);
        Glide.with(contentView)
                .load(new StringBuilder("http://forpets.kr/images/main/").append(mData.getForpetHash()).append(".jpg").toString())
                .error(R.drawable.icon_no_image)
                .centerCrop()
                .into(imagePhoto);


        TextView textName = contentView.findViewById(R.id.text_sheet_name);
        TextView textAddr = contentView.findViewById(R.id.text_sheet_addr);
        TextView textPhone = contentView.findViewById(R.id.text_sheet_phone);
        TextView textHomepage = contentView.findViewById(R.id.text_sheet_homepage);
        TextView textTime = contentView.findViewById(R.id.text_sheet_time);

        textName.setText(mData.getPlaceName());
        textAddr.setText(mData.getRoadAddressName());
        textPhone.setText(mData.getPhone());
        textHomepage.setText(mData.getHomepage());

        if (mData.getShopOpenTimeList() != null) {
            StringBuilder builder = new StringBuilder();
            for (ShopOpenTime time : mData.getShopOpenTimeList()) {
                if (time.getType().equals("영업일")) {
                    builder.append(time.getDay()).append(":").append(time.getPeriod());
                } else {
                    builder.append(time.getType()).append(":").append(time.getDay());
                }

                String remarks = time.getRemarks();
                builder.append((remarks != null) ? remarks : "");
                builder.append(System.getProperty("line.separator"));
            }
            textTime.setText(builder.toString());
        }


        CheckBox cbRecommendConsensus = contentView.findViewById(R.id.cb_recommend_consensus);
        CheckBox cbRecommendFacility = contentView.findViewById(R.id.cb_recommend_facility);
        CheckBox cbRecommendPrice = contentView.findViewById(R.id.cb_recommend_price);

        CheckBox.OnCheckedChangeListener onCheckedChangeListener = (v, isChecked) -> {
            String color = (isChecked) ? "#4b4b4b" : "#d6d6d6";
            v.setTextColor(Color.parseColor(color));
        };
        cbRecommendConsensus.setOnCheckedChangeListener(onCheckedChangeListener);
        cbRecommendFacility.setOnCheckedChangeListener(onCheckedChangeListener);
        cbRecommendPrice.setOnCheckedChangeListener(onCheckedChangeListener);


        if (mData.getAdditionalInfo() != null) {
            // do something..
        } else {
            contentView.findViewById(R.id.layout_sheet_info).setVisibility(View.GONE);
        }

        if (mData.getCategoryGroupCode().equals(Shop.CategoryGroupCode.PHARM.toString())) {
            if (mData.getShopPharmacy() != null) {
                TextView textMedicine = contentView.findViewById(R.id.text_sheet_medicine);
                textMedicine.setText(mData.getShopPharmacy().getMedicineCategoriesForSale());
            }
        } else {
            contentView.findViewById(R.id.layout_sheet_medicine).setVisibility(View.GONE);
        }

        if (mData.getIntro() != null) {
            TextView textIntro = contentView.findViewById(R.id.text_sheet_intro);
            textIntro.setText(mData.getIntro().replace("\\r\\n", System.getProperty("line.separator")));
        }


        Button buttonNavigate = contentView.findViewById(R.id.button_sheet_navigate);
        Button buttonCall = contentView.findViewById(R.id.button_sheet_call);

        buttonNavigate.setOnClickListener((v) -> {
            GpsManager manager = GpsManager.getInstance(context);
            LatLng start = new LatLng(manager.getLocation().getLatitude(), manager.getLocation().getLongitude());

            String uri = new StringBuilder("http://maps.google.com/maps?")
                    .append("saddr=").append(start.latitude).append(",").append(start.longitude)
                    .append("&daddr=").append(mData.getY()).append(",").append(mData.getX())
                    .toString();

            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.addCategory(Intent.CATEGORY_LAUNCHER);
            intent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");

            context.startActivity(intent);
        });

        buttonCall.setOnClickListener((v) -> {
            Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + mData.getPhone()));
            context.startActivity(intent);
        });


        return contentView;
    }

    private void createOptionView(Context context, View contentView, Shop shop) {
        // https://developer.android.com/reference/android/view/ViewGroup.LayoutParams
        ViewGroup.MarginLayoutParams params
                = new ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        params.setMargins(0, 0, Math.round(10 * metrics.density), Math.round(10 * metrics.density));

        try {
            Class clazz = shop.getClass();

            for (Field field : clazz.getDeclaredFields()) {
                String fieldName = field.getName();

                if (fieldName.contains("opt")) {
                    field.setAccessible(true);
                    String value = (String) field.get(shop);

                    if (value != null && value.equals("Y")) {
                        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        View itemView = inflater.inflate(R.layout.item_option, null);
                        itemView.setLayoutParams(params);

                        ImageView logo = itemView.findViewById(R.id.image_opt_logo);
                        TextView description = itemView.findViewById(R.id.text_opt_description);

                        if (fieldName.contains("Parking")) {
                            logo.setImageResource(R.drawable.enable_parking);
                            description.setText("주차");
                        } else if (fieldName.contains("Reservation")) {
                            logo.setImageResource(R.drawable.enable_reservation);
                            description.setText("예약");
                        } else if (fieldName.contains("Wifi")) {
                            logo.setImageResource(R.drawable.enable_wifi);
                            description.setText("무선인터넷");
                        } else if (fieldName.contains("365")) {
                            logo.setImageResource(R.drawable.enable_365);
                            description.setText("연중무휴");
                        } else if (fieldName.contains("Night")) {
                            logo.setImageResource(R.drawable.enable_night);
                            description.setText("야간");
                        } else if (fieldName.contains("Shop")) {
                            logo.setImageResource(R.drawable.enable_shop);
                            description.setText("용품");
                        } else if (fieldName.contains("Beauty")) {
                            logo.setImageResource(R.drawable.enable_beauty);
                            description.setText("미용");
                        } else if (fieldName.contains("Bigdog")) {
                            logo.setImageResource(R.drawable.enable_bigdog);
                            description.setText("대형견 가능");
                        } else {
                            logo.setImageResource(R.drawable.enable_hotel);
                            description.setText("호텔");
                        }

                        GridLayout gridOpt = contentView.findViewById(R.id.gridlayout_sheet_opt);
                        gridOpt.addView(itemView);
                    }
                }
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}