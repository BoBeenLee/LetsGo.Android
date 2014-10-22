package com.example.googlemap.app.adapter;

import android.content.Context;
import android.location.Address;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.googlemap.app.R;

import java.util.List;

/**
 * Created by BoBinLee on 2014-07-08.
 */
public class NameListAdapter extends BaseAdapter implements View.OnClickListener {
    Context context;
    PopupWindow popupWindow;
    List<Address> addressList;
    TextView targetText;
    Toast toast;

    public NameListAdapter(){

    }

    public NameListAdapter(Context context, PopupWindow popupWindow, List<Address> addressList, TextView targetText) {
        this.context = context;
        this.addressList = addressList;
        this.targetText = targetText;
        this.popupWindow = popupWindow;
    }

    @Override
    public int getCount() {
        return addressList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = null;
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(context);

            view = inflater.inflate(R.layout.list_item, null);
            Button listName = (Button)view.findViewById(R.id.list_name);
            listName.setTag(position);
            listName.setText(addressList.get(position).getAddressLine(0));
//            Log.v("test", addressList.get(position).toString());
//            listName.setTag(convertView.getTag(), position + "");
            listName.setOnClickListener(this);
            convertView = view;
        }
        return convertView;
    }

    @Override
    public void onClick(View v) {
        int posititon = (Integer) v.getTag();

        targetText.setText(addressList.get(posititon).getAddressLine(0));
        targetText.setTag(addressList.get(posititon));

        popupWindow.dismiss();
    }
}
