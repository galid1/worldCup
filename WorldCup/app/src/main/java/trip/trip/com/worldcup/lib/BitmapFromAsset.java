package trip.trip.com.worldcup.lib;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import java.io.IOException;
import java.io.InputStream;

public class BitmapFromAsset {

    private Context mContext;

    public BitmapFromAsset(Context mContext) {
        this.mContext = mContext;
    }

    public Bitmap loadBitmap(String path) {
        AssetManager assetManager = mContext.getAssets();

        try {
            InputStream inputStream = assetManager.open(path);
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = 1;
            Bitmap bitmap = BitmapFactory.decodeStream(inputStream, new Rect(-1, -1, -1, -1), options);
            return bitmap;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
