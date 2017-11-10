package com.example.hp.gep;

import android.app.Activity;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;
public class fRequestAdapter extends BaseAdapter  {

    Context context;
        List<friendData> valueList;
        public fRequestAdapter(List<friendData> listValue, Context context)
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
                convertView = layoutInfiater.inflate(R.layout.activity_f_request_adapter, null);

                viewItem.txtRname = (TextView)convertView.findViewById(R.id.txtRname);
                viewItem.txtRemail = (TextView)convertView.findViewById(R.id.txtRemail);
                viewItem.txtRgender = (TextView)convertView.findViewById(R.id.txtRgender);
                convertView.setTag(viewItem);
            }
            else
            {
                viewItem = (ViewItem) convertView.getTag();
            }

            viewItem.txtRname.setText(valueList.get(position).Name);
            viewItem.txtRemail.setText(valueList.get(position).email);
            viewItem.txtRgender.setText(valueList.get(position).gender);
            return convertView;
        }


        class ViewItem
        {
            TextView txtRname;
            TextView txtRemail;
            TextView txtRgender;


        }

    }





