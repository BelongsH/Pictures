package pictures.component.nianxin.com.picture;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import java.util.List;
import java.util.Map;

import model.Picture;

/**
 * Created by liuhuiliang on 16/6/6.
 */
public class PictureProxy {

    private Context context;

    private PictureReceiver receiver;
    private boolean isRegister = false;

    public PictureProxy(Context context) {
        this.context = context;
    }


    /**
     * 开始获取图片
     **/
    public void startPictureProxy(PictureCallBack callBack) {
        Intent intent = new Intent(context, PictureService.class);
        context.startService(intent);

        //接受者
        receiver = new PictureReceiver(callBack);
        IntentFilter filter = new IntentFilter(PictureReceiver.PictureReceiver_ACTION);
        filter.addCategory(Intent.CATEGORY_DEFAULT);
        context.registerReceiver(receiver, filter);
        isRegister = true;
    }


    public interface PictureCallBack {

        void onSuccess(Map<String, List<Picture>> maps);

        void onFail(String meesage);
    }

    public void destroyProxy() {
        if (receiver == null || !isRegister) return;
        context.unregisterReceiver(receiver);
    }
}
