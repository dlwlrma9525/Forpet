package kr.forpet.view.main.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import kr.forpet.R;
import kr.forpet.data.entity.Shop;

public class FavoriteListAdapter extends BaseAdapter {

    private Context mContext;
    private List<Shop> mDataList;

    public FavoriteListAdapter(Context context, List<Shop> dataList) {
        mContext = context;
        mDataList = dataList;
    }

    @Override
    public int getCount() {
        return mDataList.size();
    }

    @Override
    public Object getItem(int position) {
        return mDataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item_favorite, parent, false);
            holder = new ViewHolder(convertView);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Shop shop = mDataList.get(position);
        int resourceId = 0;

        switch (Shop.CategoryGroupCode.compare(shop.getCategoryGroupCode())) {
            case SHOP:
                resourceId = R.drawable.marker_shop;
                break;
            case PHARM:
                resourceId = R.drawable.marker_pharm;
                break;
            case HOSPITAL:
                resourceId = R.drawable.marker_hospital;
                break;
            case DOGPENSION:
                resourceId = R.drawable.marker_dogpension;
                break;
            case DOGGROUND:
                resourceId = R.drawable.marker_dogground;
                break;
            case DOGCAFE:
                resourceId = R.drawable.marker_cafe;
                break;
            case CATCAFE:
                resourceId = R.drawable.marker_cafe;
                break;
            case BEAUTY:
                resourceId = R.drawable.marker_beauty;
                break;
        }

        holder.imageFavorite.setImageResource(resourceId);
        holder.textPlace.setText(shop.getPlaceName());
        holder.textAddress.setText(shop.getRoadAddressName());

        return convertView;
    }

    class ViewHolder {

        @BindView(R.id.image_favor_logo)
        ImageView imageFavorite;

        @BindView(R.id.text_favor_name)
        TextView textPlace;

        @BindView(R.id.text_favor_description)
        TextView textAddress;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
