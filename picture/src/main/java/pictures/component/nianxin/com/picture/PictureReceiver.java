package pictures.component.nianxin.com.picture;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;
import java.util.Map;

import model.Picture;
import utils.SPUtils;

/**
 * Created by liuhuiliang on 16/6/6.
 */
public class PictureReceiver extends BroadcastReceiver {

    public static final String PictureReceiver_ACTION = "PICTUREACTION";

    private PictureProxy.PictureCallBack callBack;

    public PictureReceiver(PictureProxy.PictureCallBack callBack) {
        this.callBack = callBack;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        try {
            String datas = (String) SPUtils.get(context, "picturs", "");
            SPUtils.put(context, "picturs", "");
            if (TextUtils.isEmpty(datas)) {
                callBack.onFail("图片获取失败");
            } else {
                Map<String, List<Picture>> maps = new Gson().fromJson(datas, new TypeToken<Map<String, List<Picture>>>() {
                }.getType());
                callBack.onSuccess(maps);
            }
        } catch (Exception e) {
            e.printStackTrace();
            callBack.onFail("图片获取失败");
        } finally {

        }
    }
}