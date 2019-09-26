package kr.forpet.view.factory;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import kr.forpet.R;
import kr.forpet.view.knowledge.adapter.VaccinePagerAdapter;

public class VaccineViewFactory implements ViewFactory {
    @Override
    public View createView(Context context) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View contentView = inflater.inflate(R.layout.layout_vaccine, null);

        TabLayout tabLayoutVaccine = contentView.findViewById(R.id.tab_layout_vaccine);
        ViewPager viewPagerVaccine = contentView.findViewById(R.id.view_pager_vaccine);

        viewPagerVaccine.setAdapter(new VaccinePagerAdapter(context));
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

        return contentView;
    }
}
