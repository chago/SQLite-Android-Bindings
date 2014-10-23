package org.sqlite.app.customsqlite;

import java.io.File;

import org.sqlite.database.sqlite.SQLiteDatabase;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

public class MyTestSQLite extends Activity {
	private final File DB_PATH = new File(Environment
			.getExternalStorageDirectory().getPath() + "/sample.emb");

	private TextView mTextView;
	private SQLiteDatabase mDatabase;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.my_test_sqlite);
		mTextView = (TextView) findViewById(R.id.textView1);
	}

	public void check_if_database_exists(View view) {
		mTextView.setText(DB_PATH.exists() ? "File exists" : "File not exists");
	}

	public void open_database(View view) {
		System.loadLibrary("sqliteX");

		mDatabase = SQLiteDatabase.openOrCreateDatabase(DB_PATH, null);
		mDatabase.execSQL("PRAGMA key = 'sample'");

		mTextView.setText("Database opened");
	}

	public void load_info_table(View view) {
		// load data info table v1
		if (mDatabase != null && mDatabase.isOpen()) {
			Cursor c = mDatabase.rawQuery("SELECT * FROM INFOTABLE_V1", null);
			if (c.moveToFirst()) {
				String text = "";
				while (!c.isAfterLast()) {
					text += (!TextUtils.isEmpty(text) ? "\r\n" : "")
							+ c.getString(1) + "|" + c.getString(2);
					c.moveToNext();
				}
				mTextView.setText(text);
			}
		}
	}
	
	public void load_payee(View view) {
		// load data payee
		if (mDatabase != null && mDatabase.isOpen()) {
			Cursor c = mDatabase.rawQuery("SELECT * FROM PAYEE_V1", null);
			if (c.moveToFirst()) {
				String text = "";
				while (!c.isAfterLast()) {
					text += (!TextUtils.isEmpty(text) ? "\r\n" : "")
							+ c.getString(1);
					c.moveToNext();
				}
				mTextView.setText(text);
			}
		}
	}
	
	public void update_data(View view) {
		// load data payee
		if (mDatabase != null && mDatabase.isOpen()) {
			ContentValues values = new ContentValues();
			values.put("INFOVALUE", 3);
			String whereClause = "INFONAME=?";
			int rows = mDatabase.update("INFOTABLE_V1", values, whereClause, new String[] {"DATAVERSION"});
			mTextView.setText("Rows updated: " + Integer.toString(rows));
		}
	}
}
