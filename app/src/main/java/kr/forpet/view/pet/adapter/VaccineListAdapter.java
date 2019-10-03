package kr.forpet.view.pet.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import kr.forpet.R;
import kr.forpet.data.entity.Vaccination;

public class VaccineListAdapter extends BaseAdapter {

    private List<Vaccination> mList;

    public VaccineListAdapter(List<Vaccination> list) {
        this.mList = list;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            convertView = inflater.inflate(R.layout.item_vaccine, parent, false);
            holder = new ViewHolder(convertView);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Vaccination vaccine = mList.get(position);

        holder.textVaccineName.setText(vaccine.getVaccineName());
        holder.textVaccineWeek1.setText(vaccine.getWeek1());
        holder.textVaccineWeek2.setText(vaccine.getWeek2());
        holder.textVaccineWeek3.setText(vaccine.getWeek3());
        holder.textVaccineWeek4.setText(vaccine.getWeek4());
        holder.textVaccineWeek5.setText(vaccine.getWeek5());
        holder.textVaccineWeek6.setText(vaccine.getWeek6());

        return convertView;
    }

    class ViewHolder {

        @BindView(R.id.text_vaccine_name)
        TextView textVaccineName;

        @BindView(R.id.text_vaccine_week1)
        TextView textVaccineWeek1;

        @BindView(R.id.text_vaccine_week2)
        TextView textVaccineWeek2;

        @BindView(R.id.text_vaccine_week3)
        TextView textVaccineWeek3;

        @BindView(R.id.text_vaccine_week4)
        TextView textVaccineWeek4;

        @BindView(R.id.text_vaccine_week5)
        TextView textVaccineWeek5;

        @BindView(R.id.text_vaccine_week6)
        TextView textVaccineWeek6;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
