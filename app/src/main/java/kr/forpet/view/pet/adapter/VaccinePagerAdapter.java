package kr.forpet.view.pet.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import java.util.List;

import kr.forpet.R;

public class VaccinePagerAdapter extends PagerAdapter {

    private Context mContext;
    private List<BaseAdapter> mAdapterList;

    public VaccinePagerAdapter(Context context, List<BaseAdapter> adapterList) {
        this.mContext = context;
        this.mAdapterList = adapterList;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        // return super.instantiateItem(container, position);
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemVIew = inflater.inflate(R.layout.layout_vaccine_child, null);

        ListView listVaccine = itemVIew.findViewById(R.id.list_vaccine);
        listVaccine.setAdapter(mAdapterList.get(position));

        container.addView(itemVIew);
        return itemVIew;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        // super.destroyItem(container, position, object);
    }

    @Override
    public int getCount() {
        return mAdapterList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }
}
