package com.example.hp.gep;

/**
 * Created by HP on 20/10/2017.
 */

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class ListAdapter extends BaseAdapter {




        Context context;
        List<Event> valueList;
        public ListAdapter(List<Event> listValue, Context context)
        {
            this.context = context;
            this.valueList = listValue;
        }

        @Override
        public int getCount()
        {
            return this.valueList.size();
        }

        @Override
        public Object getItem(int position)
        {
            return this.valueList.get(position);
        }

        @Override
        public long getItemId(int position)
        {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent)
        {
            ViewItem viewItem = null;
            if(convertView == null)
            {
                viewItem = new ViewItem();
                LayoutInflater layoutInfiater = (LayoutInflater)this.context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
                //LayoutInflater layoutInfiater = LayoutInflater.from(context);
                convertView = layoutInfiater.inflate(R.layout.activity_list_adapter_view, null);

                viewItem.txtTitle = (TextView)convertView.findViewById(R.id.adapter_text_title);
                viewItem.txtdescription = (TextView)convertView.findViewById(R.id.adapter_text_description);
                convertView.setTag(viewItem);
            }
            else
            {
                viewItem = (ViewItem) convertView.getTag();
            }

            viewItem.txtTitle.setText(valueList.get(position).event_name);
            viewItem.txtdescription.setText(valueList.get(position).event_date);

            return convertView;
        }


    class ViewItem
    {
        TextView txtTitle;
        TextView txtdescription;


    }

}
