package kr.forpet.view.pet.factory;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import kr.forpet.R;
import kr.forpet.data.entity.Food;
import kr.forpet.view.factory.ViewFactory;
import kr.forpet.view.pet.adapter.MealPagerAdapter;

public class MealViewFactory implements ViewFactory {

    private List<Food> mDogList;
    private List<Food> mCatList;

    public MealViewFactory(List<Food> dogList, List<Food> catList) {
        this.mDogList = dogList;
        this.mCatList = catList;
    }

    @Override
    public View createView(Context context) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(R.layout.layout_meal, null);

        TabLayout tabLayoutMeal = itemView.findViewById(R.id.tab_layout_meal);
        ViewPager viewPagerMeal = itemView.findViewById(R.id.view_pager_meal);

        List<List<Food>> list = new ArrayList<>();
        list.add(mDogList);
        list.add(mCatList);

        viewPagerMeal.setAdapter(new MealPagerAdapter(context, list));
        viewPagerMeal.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayoutMeal));
        tabLayoutMeal.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPagerMeal.setCurrentItem(tab.getPosition());
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
