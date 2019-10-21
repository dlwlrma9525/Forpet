package kr.forpet.view.pet.factory;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.BaseAdapter;

import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import kr.forpet.R;
import kr.forpet.data.entity.CommonDisease;
import kr.forpet.data.entity.Emergency;
import kr.forpet.view.factory.ViewFactory;
import kr.forpet.view.pet.adapter.CommonDiseaseListAdapter;
import kr.forpet.view.pet.adapter.EmergencyListAdapter;
import kr.forpet.view.pet.adapter.HealthPagerAdapter;

public class HealthViewFactory implements ViewFactory {

    private List<Emergency> mEmergencyList;
    private List<CommonDisease> mDiseaseList;

    public HealthViewFactory(List<Emergency> emergencyList, List<CommonDisease> diseaseList) {
        this.mEmergencyList = emergencyList;
        this.mDiseaseList = diseaseList;
    }

    @Override
    public View createView(Context context) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(R.layout.layout_health, null);

        TabLayout tabLayoutHealth = itemView.findViewById(R.id.tab_layout_health);
        ViewPager viewPagerHealth = itemView.findViewById(R.id.view_pager_health);

        List<BaseAdapter> adapterList = new ArrayList<>();
        adapterList.add(new EmergencyListAdapter(mEmergencyList));
        adapterList.add(new CommonDiseaseListAdapter(mDiseaseList));

        viewPagerHealth.setAdapter(new HealthPagerAdapter(context, adapterList));
        viewPagerHealth.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayoutHealth));
        tabLayoutHealth.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPagerHealth.setCurrentItem(tab.getPosition());
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
