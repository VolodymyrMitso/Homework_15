package mitso.v.homework_15.fragments_menu.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "persons.db";
    private static final int DATABASE_VERSION = 1;

    public static final String DATABASE_TABLE = "persons";

    public static final String KEY_ID = "_id";
    public static final String PERSON_LOGIN = "PERSON_LOGIN";
    public static final String PERSON_PASSWORD = "PERSON_PASSWORD";
    public static final String PERSON_FIRST_NAME = "PERSON_FIRST_NAME";
    public static final String PERSON_LAST_NAME = "PERSON_LAST_NAME";
    public static final String PERSON_GENDER = "PERSON_GENDER";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE persons (" +
                KEY_ID + " integer primary key autoincrement, " +
                PERSON_LOGIN + " text not null, " +
                PERSON_PASSWORD + " text not null, " +
                PERSON_FIRST_NAME + " text not null, " +
                PERSON_LAST_NAME + " text not null, " +
                PERSON_GENDER + " text not null);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}