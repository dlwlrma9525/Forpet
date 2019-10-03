package kr.forpet.view.factory;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.BaseAdapter;

import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import kr.forpet.R;
import kr.forpet.data.entity.Vaccination;
import kr.forpet.view.pet.adapter.VaccineListAdapter;
import kr.forpet.view.pet.adapter.VaccinePagerAdapter;

public class VaccineViewFactory implements ViewFactory {

    private List<Vaccination> mCatList;
    private List<Vaccination> mDogList;

    public VaccineViewFactory(List<Vaccination> catList, List<Vaccination> dogList) {
        this.mCatList = catList;
        this.mDogList = dogList;
    }

    @Override
    public View createView(Context context) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(R.layout.layout_vaccine, null);

        TabLayout tabLayoutVaccine = itemView.findViewById(R.id.tab_layout_vaccine);
        ViewPager viewPagerVaccine = itemView.findViewById(R.id.view_pager_vaccine);

        List<BaseAdapter> adapterList = new ArrayList<>();
        adapterList.add(new VaccineListAdapter(mDogList));
        adapterList.add(new VaccineListAdapter(mCatList));

        viewPagerVaccine.setAdapter(new VaccinePagerAdapter(context, adapterList));
        viewPagerVaccine.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayoutVaccine));
        tabLayoutVaccine.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPagerVaccine.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        return itemView;
    }
}
