package com.project.foodieadmin.Helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.project.foodieadmin.Domain.AdminDataDomain;

import java.util.ArrayList;

public class SQLiteAdminData extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "admin.db";
    private static final String TABLE_NAME = "admin_data";

    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_PHONE = "phone";
    private static final String COLUMN_EMAIL = "email";
    private static final String COLUMN_USERNAME = "username";

    public SQLiteAdminData(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE_ADMIN_DATA =  "CREATE TABLE " + TABLE_NAME + "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_NAME + " VARCHAR(150) NOT NULL, " +
                COLUMN_PHONE + " VARCHAR(20) NOT NULL, " +
                COLUMN_EMAIL + " VARCHAR(150) NOT NULL, " +
                COLUMN_USERNAME + " VARCHAR(50) NOT NULL)";

        db.execSQL(CREATE_TABLE_ADMIN_DATA);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    private SQLiteDatabase database;

    public void open() throws SQLException {
        database = this.getWritableDatabase();
    }

    private String[] allColumns = {COLUMN_ID, COLUMN_NAME, COLUMN_PHONE, COLUMN_EMAIL,
            COLUMN_USERNAME};

    private AdminDataDomain cursorToPayment(Cursor cursor) {
        AdminDataDomain admindata = new AdminDataDomain();

        admindata.setId(cursor.getInt(0));
        admindata.setName(cursor.getString(1));
        admindata.setPhone(cursor.getString(2));
        admindata.setEmail(cursor.getString(3));
        admindata.setUsername(cursor.getString(4));

        return admindata;
    }

    public void createData(int id, String name, String phone, String email, String username) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_ID, id);
        values.put(COLUMN_NAME, name);
        values.put(COLUMN_PHONE, phone);
        values.put(COLUMN_EMAIL, email);
        values.put(COLUMN_USERNAME, username);

        database.insert(TABLE_NAME, null, values);
    }

    public AdminDataDomain getData(int id) {
        AdminDataDomain admindata = new AdminDataDomain();

        Cursor cursor = database.query(TABLE_NAME, allColumns, "id = " + id, null, null ,null, null);
        cursor.moveToFirst();
        admindata = cursorToPayment(cursor);
        cursor.close();

        return admindata;
    }

    public ArrayList<AdminDataDomain> getAllData() {
        ArrayList<AdminDataDomain> dataList = new ArrayList<>();

        Cursor cursor = database.query(TABLE_NAME, allColumns, null, null, null, null, null);
        cursor.moveToFirst();

        while(!cursor.isAfterLast()) {
            AdminDataDomain data = cursorToPayment(cursor);
            dataList.add(data);
            cursor.moveToNext();
        }

        cursor.close();
        return dataList;
    }

    public AdminDataDomain getFirstRow() {
        Cursor cursor = database.query(TABLE_NAME, allColumns, null, null, null, null, null);
        cursor.moveToFirst();
        AdminDataDomain data = cursorToPayment(cursor);
        cursor.close();
        return data;
    }

    public void clear() {
        database.execSQL("DELETE FROM "+ TABLE_NAME);
    }
}
