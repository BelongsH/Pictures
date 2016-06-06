package com.nianxin.component.pictures;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.squareup.leakcanary.RefWatcher;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import adapter.PicturesAdapter;
import app.BaseApplication;
import butterknife.Bind;
import butterknife.ButterKnife;
import model.Picture;
import pictures.component.nianxin.com.picture.PictureProxy;

public class PicturesActivity extends AppCompatActivity implements PictureProxy.PictureCallBack {


    @Bind(R.id.rcv_main)
    RecyclerView rcv_main;

    protected PictureProxy pictureProxy;

    protected PicturesAdapter adapter;

    protected List<Picture> models = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        rcv_main.setLayoutManager(new LinearLayoutManager(this));
        adapter = new PicturesAdapter(this, models);
        rcv_main.setAdapter(adapter);
//        HashMap<String, List<Picture>> maps = (HashMap<String, List<Picture>>) PictruesResolver.getPicturs(getApplicationContext());
//        onSuccess(maps);
        pictureProxy = new PictureProxy(this);
        pictureProxy.startPictureProxy(this);
    }

    @Override
    public void onSuccess(Map<String, List<Picture>> maps) {
        Iterator<Map.Entry<String, List<Picture>>> iterable = maps.entrySet().iterator();
        while (iterable.hasNext()) {
            Map.Entry<String, List<Picture>> model = iterable.next();
            List<Picture> lists = model.getValue();
            if (lists != null) models.addAll(lists);
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onFail(String meesage) {

    }

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, PicturesActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        pictureProxy.destroyProxy();
        RefWatcher refWatcher = BaseApplication.getRefWatcher(this);
        refWatcher.watch(this);
    }


}
