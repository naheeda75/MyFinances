package com.example.myfinances;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "myfinances.db";
    private static final int DATABASE_VERSION = 1;

    // Define the table names
    public static final String TABLE_CD = "cds";
    public static final String TABLE_LOAN = "loans";
    public static final String TABLE_CHECKING_ACCOUNT = "checking_account";

    // Define the columns for each table
    public static final String COL_ID = "id";
    public static final String COL_ACCOUNT_NUMBER = "account_number";
    public static final String COL_INITIAL_BALANCE = "initial_balance";
    public static final String COL_CURRENT_BALANCE = "current_balance";
    public static final String COL_PAYMENT_AMOUNT = "payment_amount";
    public static final String COL_INTEREST_RATE = "interest_rate";
    // ... other column definitions

    // SQL statements to create tables
    private static final String CREATE_TABLE_CD = "CREATE TABLE " + TABLE_CD + " (" +
            COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COL_ACCOUNT_NUMBER + " TEXT, " +
            COL_INITIAL_BALANCE + " REAL, " +
            COL_CURRENT_BALANCE + " REAL, " +
            COL_INTEREST_RATE + " REAL);";

    private static final String CREATE_TABLE_LOAN = "CREATE TABLE " + TABLE_LOAN + " (" +
            COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COL_ACCOUNT_NUMBER + " TEXT, " +
            COL_INITIAL_BALANCE + " REAL, " +
            COL_CURRENT_BALANCE + " REAL, " +
            COL_PAYMENT_AMOUNT + " REAL, " +
            COL_INTEREST_RATE + " REAL);";

    private static final String CREATE_TABLE_CHECKING_ACCOUNT = "CREATE TABLE " + TABLE_CHECKING_ACCOUNT + " (" +
            COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COL_ACCOUNT_NUMBER + " TEXT, " +
            COL_CURRENT_BALANCE + " REAL);";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create the tables
        db.execSQL(CREATE_TABLE_CD);
        db.execSQL(CREATE_TABLE_LOAN);
        db.execSQL(CREATE_TABLE_CHECKING_ACCOUNT);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Handle database upgrades if needed
    }
}
