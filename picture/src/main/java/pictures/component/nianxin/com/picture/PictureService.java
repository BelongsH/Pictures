package pictures.component.nianxin.com.picture;

import android.app.IntentService;
import android.content.Intent;

import com.google.gson.Gson;

import java.util.HashMap;
import java.util.List;

import model.Picture;
import utils.SPUtils;

/**
 * Created by liuhuiliang on 16/6/6.
 */
public class PictureService extends IntentService {


    public PictureService(String name) {
        super(name);
    }


    public PictureService() {
        this(PictureService.class.getName());
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Intent broadcastIntent = new Intent();
        HashMap<String, List<Picture>> maps = (HashMap<String, List<Picture>>) PictruesResolver.getPicturs(getApplicationContext());
        broadcastIntent.setAction(PictureReceiver.PictureReceiver_ACTION);
        broadcastIntent.addCategory(Intent.CATEGORY_DEFAULT);
        SPUtils.put(getApplicationContext(),"picturs",new Gson().toJson(maps));
        sendBroadcast(broadcastIntent);
    }
}
