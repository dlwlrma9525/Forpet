package kr.forpet.view.pet.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.google.android.material.tabs.TabLayout;

import java.util.List;

import kr.forpet.R;
import kr.forpet.data.entity.Food;

public class MealPagerAdapter extends PagerAdapter {

    private Context mContext;
    private List<List<Food>> mList;

    public MealPagerAdapter(Context context, List<List<Food>> list) {
        this.mContext = context;
        this.mList = list;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        // return super.instantiateItem(container, position);
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemVIew = inflater.inflate(R.layout.layout_meal_child, null);

        ImageView imageMeal = itemVIew.findViewById(R.id.image_meal);
        TextView textSpec = itemVIew.findViewById(R.id.text_meal_spec);
        TextView textStrong = itemVIew.findViewById(R.id.text_meal_strong);
        TextView textWeek = itemVIew.findViewById(R.id.text_meal_week);
        TextView textPrecaution = itemVIew.findViewById(R.id.text_meal_precaution);

        List<Food> foodList = mList.get(position);

        TabLayout tabLayout = itemVIew.findViewById(R.id.tab_layout_child);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                Food food = foodList.get(tab.getPosition());

                Glide.with(itemVIew)
                        .load(Uri.parse(new StringBuilder("file:///android_asset/food_image/").append(food.getImageUrl()).append(".png").toString()))
                        .error(R.drawable.icon_no_image)
                        .into(imageMeal);

                textSpec.setText(food.getSpec().replace("\\n", System.getProperty("line.separator")));
                textStrong.setText(food.getStrong().replace("\\n", System.getProperty("line.separator")));
                textWeek.setText(food.getWeak().replace("\\n", System.getProperty("line.separator")));
                textPrecaution.setText(food.getPrecaution().replace("\\n", System.getProperty("line.separator")));
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        for (Food food : foodList) {
            tabLayout.addTab(tabLayout.newTab().setText(food.getFoodType()));
        }

        container.addView(itemVIew);
        return itemVIew;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        // super.destroyItem(container, position, object);
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }
}
