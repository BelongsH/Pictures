package pictures.component.nianxin.com.picture;

import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.Picture;

/**
 * Created by liuhuiliang on 16/6/6.
 */
public class PictruesResolver {

    public static final String IMAGE_JPEG = "image/jpeg";
    public static final String IMAGE_PNG = "image/png";

    public static final String[] projections = {
            MediaStore.Images.Media._ID,
            MediaStore.Images.Media.BUCKET_ID,
            MediaStore.Images.Media.BUCKET_DISPLAY_NAME,
            MediaStore.Images.Media.DATA,
            MediaStore.Images.Media.DATE_TAKEN,
            MediaStore.Images.Media.ORIENTATION,
            MediaStore.Images.Thumbnails.DATA
    };

    public static Map<String, List<Picture>> getPicturs(Context context) {
        Map<String, List<Picture>> maps = new HashMap<>();
        Cursor mCursor = context.getContentResolver().query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                projections,
                MediaStore.Images.Media.MIME_TYPE + " = ? or " + MediaStore.Images.Media.MIME_TYPE + " = ?",
                new String[]{IMAGE_JPEG, IMAGE_PNG},
                MediaStore.Images.Media.DATE_ADDED + " desc");
        if (mCursor == null) return maps;
        try {
            mCursor.moveToFirst();
            while (mCursor.moveToNext()) {
                int pictureID = mCursor.getInt(mCursor.getColumnIndex(MediaStore.Images.Media._ID));
                String picturePath = mCursor.getString(mCursor.getColumnIndex(MediaStore.Images.Media.DATA));
                String thumbPath = mCursor.getString(mCursor.getColumnIndex(MediaStore.Images.Thumbnails.DATA));
                Picture picture = new Picture(pictureID, picturePath, thumbPath);
                String floderName = mCursor.getString(mCursor.getColumnIndex(MediaStore.Images.Media.BUCKET_DISPLAY_NAME));
                List<Picture> datas = maps.get(floderName);
                if (datas == null) {
                    datas = new ArrayList<>();
                    maps.put(floderName, datas);
                }
                datas.add(picture);
                maps.put(floderName, datas);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            mCursor.close();
        }
        return maps;
    }
}
