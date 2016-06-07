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
import pictures.component.nianxin.com.picture.PictruesResolver;
import pictures.component.nianxin.com.picture.PictureProxy;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.functions.Action1;
import rx.subscriptions.CompositeSubscription;
import utils.RxUtils;

public class PicturesActivity extends AppCompatActivity implements PictureProxy.PictureCallBack {


    @Bind(R.id.rcv_main)
    RecyclerView rcv_main;

//    protected PictureProxy pictureProxy;

    protected PicturesAdapter adapter;

    protected List<Picture> models = new ArrayList<>();

    private Context context;


    protected CompositeSubscription compositeSubscription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        context = this;
        rcv_main.setLayoutManager(new LinearLayoutManager(this));
        adapter = new PicturesAdapter(this, models);
        rcv_main.setAdapter(adapter);
        compositeSubscription = new CompositeSubscription();
        Subscription subscription = Observable.create(new Observable.OnSubscribe<Map<String, List<Picture>>>() {
            @Override
            public void call(Subscriber<? super Map<String, List<Picture>>> subscriber) {
                subscriber.onNext(PictruesResolver.getPicturs(context));
            }
        }).compose(RxUtils.<Map<String, List<Picture>>>transformerShedule())
                .subscribe(new Action1<Map<String, List<Picture>>>() {
                    @Override
                    public void call(Map<String, List<Picture>> maps) {
                        onSuccess(maps);
                    }
                });
        compositeSubscription.add(subscription);

//        pictureProxy = new PictureProxy(this);
//        pictureProxy.startPictureProxy(this);
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
//        pictureProxy.destroyProxy();
        if (compositeSubscription != null) {
            compositeSubscription.unsubscribe();
        }
        RefWatcher refWatcher = BaseApplication.getRefWatcher(this);
        refWatcher.watch(this);
    }


}
