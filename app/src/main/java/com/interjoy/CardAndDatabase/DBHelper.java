package com.interjoy.CardAndDatabase;

/**
 * Created by ghouken on 2017/12/23.
 */
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class DBHelper extends SQLiteOpenHelper {
    public String CREATE_TABLE = " create table Photo (_id integer primary key autoincrement , fruitname text,fruitinfo text,fruitimage BLOB)";
    private Context pContext;

    public DBHelper(Context context , String name ,
                    CursorFactory factory, int version){
        super (context,name,factory,version);
        // TODO Auto-generated constructor stub
        pContext=context;
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
        db.execSQL(CREATE_TABLE);
        Toast.makeText(pContext,"Create Successded", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub

    }
}
