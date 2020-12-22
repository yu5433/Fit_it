package com.example.fitit;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {
    private Context nowContext;
    private final String name = "Name";
    private final String password = "Password";

//    private final String date = "date";
//    private final String upperlimb = "Upperlimb";
//    private final String lowerlimb = "Lowerlimb";
//    private final String softness = "Softness";
//    private final String endurance = "Endurance";

    private static final String databaseName = "LocalDB";
    private static final int databaseVersion = 1;

    // account info
    private final String AccountTableName = "AccountInfo";
    private final String createAccountTableSQL = "CREATE TABLE IF NOT EXISTS " + this.AccountTableName
            + " ( " + this.name + " VARCHAR(255)," + this.password + " VARCHAR(255) ) ;";

    private final String deleteAccountTableSQL = "DROP TABLE IF EXISTS " + this.AccountTableName + ";";

    public DBHelper(Context context){
        super(context,databaseName,null,databaseVersion);
        this.nowContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            db.execSQL(this.createAccountTableSQL);
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        try {
            db.execSQL(this.deleteAccountTableSQL);
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void insertToAccount(String name,String password){
        SQLiteDatabase myLocalDB = this.getReadableDatabase() ;

        ContentValues contentValues = new ContentValues();
        contentValues.put(this.name,name);
        contentValues.put(this.password,password);

        long nowID = myLocalDB.insert(this.AccountTableName,null,contentValues);

        final String successLog = "註冊成功！";
        Toast.makeText(this.nowContext,successLog,Toast.LENGTH_SHORT).show();
    }

    public ArrayList<AccountInfo> getAccountInfo(){
        ArrayList<AccountInfo> accountList = new ArrayList<AccountInfo>();

        SQLiteDatabase myLocalDB = this.getReadableDatabase();
        String[] myColumn = {this.name,this.password};

        Cursor myCursor = myLocalDB.query(this.AccountTableName,myColumn,null,null,null,null,null);

        while(myCursor.moveToNext()){
            String name = myCursor.getString(myCursor.getColumnIndex(this.name));
            String phone = myCursor.getString(myCursor.getColumnIndex(this.password));

            AccountInfo accountInfo = new AccountInfo();
            accountInfo.init(name,phone);
            accountList.add(accountInfo);
        }

        return accountList;
    }


}