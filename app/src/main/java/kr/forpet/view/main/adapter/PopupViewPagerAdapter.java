package kr.forpet.view.main.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

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

        TextView textDistance = itemView.findViewById(R.id.text_popup_distance);
        TextView textPlaceName = itemView.findViewById(R.id.text_popup_place);
        TextView textAddressName = itemView.findViewById(R.id.text_popup_address);
        Button buttonNavigation = itemView.findViewById(R.id.button_popup_navigation);
        Button buttonCall = itemView.findViewById(R.id.button_popup_call);

        Shop shop = mList.get(position);

        textDistance.setText(String.format("%.2f km", shop.getDistance() / 1000));
        textPlaceName.setText(shop.getPlaceName());
        textAddressName.setText(shop.getRoadAddressName());

        itemView.setOnClickListener((v) -> {
            onItemListener.onItemClick(mList.get(position));
        });

        buttonNavigation.setOnClickListener((v) -> {
        });

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

    private OnItemListener onItemListener;

    public void setOnItemListener(OnItemListener onItemListener) {
        this.onItemListener = onItemListener;
    }

    public interface OnItemListener {
        void onItemClick(Shop shop);
    }
}