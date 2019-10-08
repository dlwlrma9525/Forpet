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
import kr.forpet.data.entity.Emergency;

public class EmergencyListAdapter extends BaseAdapter {

    private List<Emergency> mList;

    public EmergencyListAdapter(List<Emergency> list) {
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
            convertView = inflater.inflate(R.layout.item_emergency, parent, false);
            holder = new ViewHolder(convertView);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Emergency emergency = mList.get(position);

        holder.textEmergencyProblem.setText(emergency.getProblem());
        holder.textEmergencySolution.setText(emergency.getSolution());

        return convertView;
    }

    class ViewHolder {

        @BindView(R.id.text_emergency_problem)
        TextView textEmergencyProblem;

        @BindView(R.id.text_emergency_solution)
        TextView textEmergencySolution;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
