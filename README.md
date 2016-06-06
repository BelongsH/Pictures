# Pictures
>android中快速获取本地的照片，用于头像上传和发布主题等等

![UiButton](https://github.com/BelongsH/UiButton/blob/master/screenshot.jpg)

Gradle
------
```
dependencies {
    ...
    compile 'com.nianxin:pictures:1.0.0'
}
```

Usage
-----
```java

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



    //destroy proxy
    @Override
    protected void onDestroy() {
        super.onDestroy();
        pictureProxy.destroyProxy();
        RefWatcher refWatcher = BaseApplication.getRefWatcher(this);
        refWatcher.watch(this);
    }

}


```



