package trip.trip.com.worldcup.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import trip.trip.com.worldcup.Celebrity;

/**
 * CelebrityTable 에 직접 쿼리를 날리는 객체
 */
public class CelebrityTable {

    private SQLiteDatabase mDatabase;

    public CelebrityTable(Context context) {
        context = context.getApplicationContext();
        mDatabase = new CelebrityBaseHelper(context).getWritableDatabase();
    }

    /**
     * Celebrity Table 에서 모든 ID Select and return
     * @return
     */
    public ArrayList<Integer> wholeCelebrityID() {

        ArrayList<Integer> idList = new ArrayList<>();

        Cursor cursor = mDatabase.query(
                CelebrityDBSchema.CelebrityTable.NAME,
                new String[]{CelebrityDBSchema.CelebrityTable.Cols.ID},
                null,
                null,
                null,
                null,
                null);

        if(cursor.moveToFirst()){
            while (!cursor.isAfterLast()) {
                int id = cursor.getInt(cursor.getColumnIndex(CelebrityDBSchema.CelebrityTable.Cols.ID));
                idList.add(id);
                cursor.moveToNext();
            }
        }

        cursor.close();

        return idList;
    }

    /**
     * Celebrity Table 에서 특정 ID를 가진 row Select and return
     * @return
     */
    public ArrayList<Celebrity> specificCelebrity(List<Integer> idList){
        ArrayList<Celebrity> celebrityList = new ArrayList<>();

        if(idList.size() < 1)
            return celebrityList;


        String[] selectionArgs = new String[idList.size()];

        StringBuilder buf = new StringBuilder();
        for(int i = 0; i < idList.size(); i++){
            buf.append('?');
            if(i < idList.size() - 1)
                buf.append(',');

            selectionArgs[i] = idList.get(i).toString();
        }

        String selection = String.format("%s IN( %s )",CelebrityDBSchema.CelebrityTable.Cols.ID, buf.toString());

        Cursor cursor = mDatabase.query(
                CelebrityDBSchema.CelebrityTable.NAME,
                new String[]{CelebrityDBSchema.CelebrityTable.Cols.NAME, CelebrityDBSchema.CelebrityTable.Cols.IMAGE_PATH},
                selection,
                selectionArgs,
                null,
                null,
                null);

        if(cursor.moveToFirst()){
            while (!cursor.isAfterLast()) {
                String name = cursor.getString(cursor.getColumnIndex(CelebrityDBSchema.CelebrityTable.Cols.NAME));
                String imagePath = cursor.getString(cursor.getColumnIndex(CelebrityDBSchema.CelebrityTable.Cols.IMAGE_PATH));
                Celebrity celebrity = new Celebrity(name, imagePath);
                celebrityList.add(celebrity);
                cursor.moveToNext();
            }
        }

        cursor.close();

        return celebrityList;
    }
}
