package kr.forpet.view.main.adapter;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

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
        View itemView = inflater.inflate(R.layout.layout_popup, container, false);
        itemView.setOnClickListener((v) -> onItemListener.onItemClick(mList.get(position)));

        Shop shop = mList.get(position);

        TextView textDistance = itemView.findViewById(R.id.text_popup_distance);
        textDistance.setText(String.format("%.2f km", shop.getDistance() / 1000));

        TextView textPlaceName = itemView.findViewById(R.id.text_popup_place);
        textPlaceName.setText(shop.getPlaceName());

        TextView textAddressName = itemView.findViewById(R.id.text_popup_address);
        textAddressName.setText(shop.getRoadAddressName());

        LinearLayout linearLayout = itemView.findViewById(R.id.linear_popup_option);

        if (shop.getOptParking() != null)
            linearLayout.addView(createOptionView(mContext, R.drawable.enable_parking));
        if (shop.getOptReservation() != null)
            linearLayout.addView(createOptionView(mContext, R.drawable.enable_reservation));
        if (shop.getOptWifi() != null)
            linearLayout.addView(createOptionView(mContext, R.drawable.enable_wifi));
        if (shop.getOpt365().equals("Y"))
            linearLayout.addView(createOptionView(mContext, R.drawable.enable_365));
        if (shop.getOptNight().equals("Y"))
            linearLayout.addView(createOptionView(mContext, R.drawable.enable_night));
        if (shop.getOptShop().equals("Y"))
            linearLayout.addView(createOptionView(mContext, R.drawable.enable_shop));
        if (shop.getOptBeauty().equals("Y"))
            linearLayout.addView(createOptionView(mContext, R.drawable.enable_beauty));
        if (shop.getOptBigdog().equals("Y"))
            linearLayout.addView(createOptionView(mContext, R.drawable.enable_bigdog));
        if (shop.getOptHotel().equals("Y"))
            linearLayout.addView(createOptionView(mContext, R.drawable.enable_hotel));

        Button buttonNavigation = itemView.findViewById(R.id.button_popup_navigation);
        buttonNavigation.setOnClickListener((v) -> {
        });

        Button buttonCall = itemView.findViewById(R.id.button_popup_call);
        buttonCall.setOnClickListener((v) -> {
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

    private ImageView createOptionView(Context context, int resourceId) {
        FrameLayout.LayoutParams params
                = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        params.height = Math.round(21 * metrics.density);
        params.width = Math.round(21 * metrics.density);
        params.setMarginEnd(Math.round(5 * metrics.density));

        ImageView image = new ImageView(context);
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