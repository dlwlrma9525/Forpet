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
import kr.forpet.data.entity.CommonDisease;

public class CommonDiseaseListAdapter extends BaseAdapter {

    private List<CommonDisease> mList;

    public CommonDiseaseListAdapter(List<CommonDisease> list) {
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
            convertView = inflater.inflate(R.layout.item_common_disease, parent, false);
            holder = new ViewHolder(convertView);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        CommonDisease disease = mList.get(position);

        holder.textDiseaseName.setText(disease.getDisease());
        holder.textDiseaseInfection.setText(disease.getInfection().replace("\\n", System.getProperty("line.separator")));
        holder.textDiseaseSymptom.setText(disease.getSymptom().replace("\\n", System.getProperty("line.separator")));
        holder.textDiseasePrevention.setText(disease.getPrevention().replace("\\n", System.getProperty("line.separator")));

        return convertView;
    }

    class ViewHolder {

        @BindView(R.id.text_disease_name)
        TextView textDiseaseName;

        @BindView(R.id.text_disease_infection)
        TextView textDiseaseInfection;

        @BindView(R.id.text_disease_symptom)
        TextView textDiseaseSymptom;

        @BindView(R.id.text_disease_prevention)
        TextView textDiseasePrevention;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
