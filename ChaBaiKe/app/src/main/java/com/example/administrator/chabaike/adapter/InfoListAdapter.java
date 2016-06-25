package com.example.administrator.chabaike.adapter;

import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.chabaike.R;
import com.example.administrator.chabaike.beans.Info;
import com.example.administrator.httplib.BitmapRequest;
import com.example.administrator.httplib.HttpHelper;
import com.example.administrator.httplib.Request;

import java.util.List;

/**
 * Created by Administrator on 2016/6/22 0022.
 */
public class InfoListAdapter extends BaseAdapter {

    private static final String TAG = InfoListAdapter.class.getSimpleName();
    private List<Info> infoList;

    public InfoListAdapter(List<Info> infoList) {
        this.infoList = infoList;
    }

    @Override
    public int getCount() {
        return infoList == null ? 0 : infoList.size();
    }

    @Override
    public Info getItem(int position) {
        return infoList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = View.inflate(parent.getContext(), R.layout.content_lv_item, null);
            viewHolder = new ViewHolder();
            viewHolder.tv_desc = (TextView) convertView.findViewById(R.id.item_tv_desc);
            viewHolder.tv_time = (TextView) convertView.findViewById(R.id.item_tv_time);
            viewHolder.tv_rcount = (TextView) convertView.findViewById(R.id.item_tv_rc);
            viewHolder.iv_icon = (ImageView) convertView.findViewById(R.id.item_iv);
            convertView.setTag(viewHolder);
        }
        Info info = getItem(position);
        viewHolder = (ViewHolder) convertView.getTag();
        viewHolder.tv_time.setText(""+info.getTime());
        viewHolder.tv_desc.setText(info.getDescription());
        viewHolder.tv_rcount.setText(""+info.getRcount());
        viewHolder.iv_icon.setImageResource(R.mipmap.icon);
        loadImage(viewHolder.iv_icon, "http://tnfs.tngou.net/image" + info.getImg() + "_100X100");
        return convertView;
    }

    public class ViewHolder {
        public TextView tv_desc;
        public TextView tv_time;
        public TextView tv_rcount;
        public ImageView iv_icon;
    }

    public void loadImage(final ImageView iv, final String url) {

        //Log.d(TAG, "loadImage() returned: url=" + url);
        iv.setTag(url);
        BitmapRequest br = new BitmapRequest(url, Request.Method.GET, new Request.Callback<Bitmap>() {
            @Override
            public void onError(Exception e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(final Bitmap response) {
                if (iv != null && response != null && ((String) iv.getTag()).equals(url)) {
                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            iv.setImageBitmap(response);
                        }
                    });
                }
            }
        });
        HttpHelper.addRequest(br);
    }
}
