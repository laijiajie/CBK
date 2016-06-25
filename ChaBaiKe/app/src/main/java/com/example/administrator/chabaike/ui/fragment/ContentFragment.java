package com.example.administrator.chabaike.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.administrator.chabaike.R;
import com.example.administrator.chabaike.adapter.InfoListAdapter;
import com.example.administrator.chabaike.beans.Info;
import com.example.administrator.chabaike.ui.activity.DetailActivity;
import com.example.administrator.httplib.HttpHelper;
import com.example.administrator.httplib.Request;
import com.example.administrator.httplib.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

/**
 * Created by Administrator on 2016/6/22 0022.
 */
public class ContentFragment extends Fragment {

    private ListView listView;
    private int class_id;
    private PtrClassicFrameLayout mRefreshView;
    private InfoListAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle bundle) {
        View view = View.inflate(getActivity(), R.layout.frag_content, null);
        initView(view);
        class_id = getArguments().getInt("id");
        if (bundle != null){
            Parcelable[] ps =  bundle.getParcelableArray("cache");
            Info[] ins = (Info[]) bundle.getParcelableArray("cache");
            if (ins != null && ins.length != 0){
                infos = Arrays.asList(ins);
                adapter = new InfoListAdapter(infos);
                listView.setAdapter(adapter);

            }else {
                getDataFromNetwork();
                getDeatilFromNetwork();
            }
        }else {
            getDataFromNetwork();
            getDeatilFromNetwork();
        }

        return view;
    }

    private void initView(View view) {
        listView = (ListView) view.findViewById(R.id.frag_content_lv);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (infos != null && infos.size() != 0) {

                    int info_id = class_id;
                    Intent intent = new Intent();
                    Bundle bundle=new Bundle();
                    bundle.putParcelable("info",infos.get(position));
                    intent.putExtras(bundle);
                    intent.putExtra("choice",1);
                    intent.setClass(getActivity(), DetailActivity.class);
                    intent.putExtra("info_id", info_id);
                    startActivity(intent);
                }
            }
        });
        mRefreshView = (PtrClassicFrameLayout) view.findViewById(R.id.rotate_header_list_view_frame);
        mRefreshView.setResistance(1.7f);
        mRefreshView.setRatioOfHeaderHeightToRefresh(1.2f);
        mRefreshView.setDurationToClose(200);
        mRefreshView.setDurationToCloseHeader(1000);
        mRefreshView.setPullToRefresh(true);
        mRefreshView.setKeepHeaderWhenRefresh(true);
        mRefreshView.setPtrHandler(new PtrHandler() {
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header);
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                getDataFromNetwork();
            }
        });

    }

    private List<Info> infos = new ArrayList<>();

    private void getDataFromNetwork() {
        String url = "http://www.tngou.net/api/info/list?id=" + class_id;
        StringRequest req = new StringRequest(url, Request.Method.GET, new Request.Callback<String>() {

            @Override
            public void onError(Exception e) {

            }

            @Override
            public void onResponse(String response) {
                try {
                    JSONObject object = new JSONObject(response);
                    List<Info> infoList = parseJson2List(object);
                    if (infoList != null) {
                        infos.clear();
                        infos.addAll(infoList);

                        if (adapter == null) {
                            adapter = new InfoListAdapter(infos);
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    listView.setAdapter(adapter);
                                }
                            });

                        }else {
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    adapter.notifyDataSetChanged();
                                }
                            });
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mRefreshView.refreshComplete();
                    }
                });
            }
        });
        HttpHelper.addRequest(req);
    }
    private void getDeatilFromNetwork() {
        String url = "http://www.tngou.net/api/info/show?id=" + class_id;
        StringRequest req = new StringRequest(url, Request.Method.GET, new Request.Callback<String>() {

            @Override
            public void onError(Exception e) {

            }

            @Override
            public void onResponse(String response) {
                try {
                    JSONObject object = new JSONObject(response);
                    List<Info> infoList = parseJson2List(object);
                    if (infoList != null) {
                        infos.clear();
                        infos.addAll(infoList);

                        if (adapter == null) {
                            adapter = new InfoListAdapter(infos);
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    listView.setAdapter(adapter);
                                }
                            });

                        }else {
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    adapter.notifyDataSetChanged();
                                }
                            });
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mRefreshView.refreshComplete();
                    }
                });
            }
        });
        HttpHelper.addRequest(req);
    }

    private List<Info> parseJson2List(JSONObject jsonObject) throws JSONException {
        if (jsonObject == null)
            return null;
        JSONArray array = jsonObject.getJSONArray("tngou");

        if (array == null || array.length() == 0)
            return null;
        List<Info> list = new ArrayList<>();

        int len = array.length();
        JSONObject obj =null;
        Info info = null;
        for (int i = 0; i < len; i++) {
            obj = array.getJSONObject(i);
            info = new Info();
            info.setDescription(obj.optString("description"));
            info.setCount(obj.optInt("rcount"));
            long time = obj.getLong("time");
            String str = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(time);
            info.setTime(time);
            info.setImg(obj.optString("img"));
            info.setId(obj.optInt("Id"));
            info.setTitle(obj.optString("title"));
            info.setKeywords(obj.optString("keywords"));
            info.setMessage(obj.optString("message"));
            list.add(info);
        }
        return list;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        if (infos == null || infos.size() == 0) return;
        Info[] parce = new Info[infos.size()];
        infos.toArray(parce);
        outState.putParcelableArray("cache", parce);
    }
}
