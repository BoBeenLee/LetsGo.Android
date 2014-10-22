package com.example.googlemap.app.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.googlemap.app.R;
import com.example.googlemap.app.model.TourLocation;
import com.example.googlemap.app.util.BeanUtils;

import java.io.InputStream;
import java.net.URL;
import java.util.List;
import java.util.Random;

/**
 * Created by BoBinLee on 2014-07-08.
 */
public class TourListAdapter extends BaseAdapter {
    LayoutInflater inflater;
    List<TourLocation> tourList;

    public TourListAdapter(Context ctx,  List<TourLocation> tourList) {
        inflater = LayoutInflater.from(ctx);
        this.tourList = tourList;
    }
    @Override
    public int getCount() {
        return tourList.size();
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
        ViewHolder holder;

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.staggered_grid_item,
                    null);
            holder = new ViewHolder();
            holder.imageView = (ImageView) convertView .findViewById(R.id.image_view);
            holder.textView = (TextView) convertView.findViewById(R.id.text_view);
            convertView.setTag(holder);
        }
        holder = (ViewHolder) convertView.getTag();

        try {
            Log.v("test", BeanUtils.getBeanGetValue(tourList.get(position)));
        } catch(Exception e){}

        TourLocation tourItem = tourList.get(position);
        holder.textView.setText(tourItem.getTitle() + " - " + tourItem.getAddr1());

        if(tourItem.getFirstimage() != null) {
            try {
                URL url = new URL(tourItem.getFirstimage());
                Bitmap bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                holder.imageView.setImageBitmap(bmp);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return convertView;
    }
//
//    class ImageDownloader extends AsyncTask<String, Void, Bitmap> {
//        ImageView bmImage;
//
//        public ImageDownloader(ImageView bmImage) {
//            this.bmImage = bmImage;
//        }
//
//        protected Bitmap doInBackground(String... urls) {
//            String url = urls[0];
//            Bitmap mIcon = null;
//            try {
//                InputStream in = new java.net.URL(url).openStream();
//                mIcon = BitmapFactory.decodeStream(in);
//            } catch (Exception e) {
//                Log.e("Error", e.getMessage());
//            }
//            return mIcon;
//        }
//
//        protected void onPostExecute(Bitmap result) {
//            bmImage.setImageBitmap(result);
//        }
//    }

    static class ViewHolder {
        public ImageView imageView;
        public TextView textView;
    }
}
