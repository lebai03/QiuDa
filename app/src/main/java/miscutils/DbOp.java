package miscutils;

import java.text.SimpleDateFormat;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DbOp extends SQLiteOpenHelper{

    private static DbOp instance;
    private SQLiteDatabase db;
    
    public static final int PROFILE_USERNAME = 0x00000001;
    public static final int PROFILE_PASSWORD = 0x00000002;
//    public static final int PROFILE_LASTLOGIN = 0x00000004;
//    public static final int PROFILE_HEADIMAGE = 0x00000008;
    
    public DbOp(Context context, String name, CursorFactory factory, int version) {
		super(context, name, factory, version);
		// TODO Auto-generated constructor stub
		
		if (db == null) {
			db = context.openOrCreateDatabase(name, version, factory);
			createDefault();
		}
	}

	@Override
	public void onCreate(SQLiteDatabase arg0) {
		// TODO Auto-generated method stub
	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub
		
	}

	public static DbOp getDbOp(Context context) {
		if (instance == null)
			instance = new DbOp(context, "EnjoyDB", null, 1);
		
		return instance;
	}
	
	private void createDefault() {
	
	}
	
	private void createTable() {
		
	}
	
	private void insertIntoTable() {
		
	}
	
	private void deleteFromTable() {
		
	}
	
	private void queryInTable() {
		
	}
	
	public void upgradeUserTable(String username, String password) {
		
		if (db != null) {
			String DROP_TABLE = "drop table if exists Profile";
			Log.v("DbOp", "DROP TABLE " + DROP_TABLE);
			try {
				db.execSQL(DROP_TABLE);
			} catch (SQLiteException e) {
				Log.e("DbOp", "Error Happen in Drop User Table!!");
			}
			String CREATE_TABLE = "create table if not exists Profile(username varchar(32) not null, password varchar(128), lastlogin varchar(24) not null)";
			Log.v("DbOp", "CREATE TABLE " + CREATE_TABLE);
			try {
				db.execSQL(CREATE_TABLE);
			} catch (SQLiteException e) {
				Log.e("DbOp", "Error Happen in Create User Table!!");
			}

			SimpleDateFormat sDateFormat = new SimpleDateFormat(
					"yyyy-MM-dd-HH-mm-ss");
			String date = sDateFormat.format(new java.util.Date());
			String INSERT_TABLE;
			if (password == "") {
				INSERT_TABLE = "insert into Profile(username) values ("
						+ username + ")";
			} else {
				INSERT_TABLE = "insert into Profile(username, password, lastlogin) values(\""
						+ username
						+ "\",\""
						+ password
						+ "\",\""
						+ date
						+ "\")";
			}
			Log.v("DbOp", "INSERT TABLE " + INSERT_TABLE);
			try {
				db.execSQL(INSERT_TABLE);
			} catch (SQLiteException e) {
				Log.e("DbOp", "Error Happen in Insert User Table!!");
			}
		}
	}
}
