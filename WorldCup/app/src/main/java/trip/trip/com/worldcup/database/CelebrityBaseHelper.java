package trip.trip.com.worldcup.database;

import android.content.Context;
import android.content.res.AssetManager;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.IOException;

/**
 * celebrity database create and create Celebrity table
 * then init Celebrity table from assets image file
 */
public class CelebrityBaseHelper extends SQLiteOpenHelper {
    private static final int VERSION = 1;

    private Context mContext;

    protected CelebrityBaseHelper(Context context) {
        super(context, CelebrityDBSchema.DATABASE_NAME, null, VERSION);
        this.mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = String.format
                ("create table %s ( %s integer primary key autoincrement, %s text not null, %s text not null )",
                        CelebrityDBSchema.CelebrityTable.NAME,
                        CelebrityDBSchema.CelebrityTable.Cols.ID,
                        CelebrityDBSchema.CelebrityTable.Cols.NAME,
                        CelebrityDBSchema.CelebrityTable.Cols.IMAGE_PATH);
        db.execSQL(query);

        String insertQuery = String.format("insert into %s(%s, %s) values(?, ?)",
                CelebrityDBSchema.CelebrityTable.NAME,
                CelebrityDBSchema.CelebrityTable.Cols.NAME,
                CelebrityDBSchema.CelebrityTable.Cols.IMAGE_PATH);

        AssetManager assetManager = mContext.getAssets();
        try {
            String[] imagePaths = assetManager.list("image");
            for (String imagePath : imagePaths) {
                String[] temp = imagePath.split("\\.");
                String name = temp[0];
                String path =  "image/" + imagePath;
                db.execSQL(insertQuery, new String[]{name, path});
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}
