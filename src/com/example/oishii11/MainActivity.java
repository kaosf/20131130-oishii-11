package com.example.oishii11;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnKeyListener;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends Activity {
	ArrayList<String> list = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final EditText edit = (EditText)findViewById(R.id.editText1);
        edit.setOnKeyListener(new OnKeyListener() {
			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				if (keyCode == KeyEvent.KEYCODE_ENTER) {
					String str = edit.getText().toString();
					if (str.length() == 0) {
						return true;
					}
					update(str);
					edit.setText("");
					return true;
				}
				return false;
			}
		});
    }

	private void update(String str) {
		list.add(0, str);
		StringBuffer str2 = new StringBuffer();
		int line = 0;
		for (String message : list) {
			if (line > 20) {
				break;
			}
			str2.append(message);
			str2.append('\n');
			line++;
		}
		TextView view = (TextView) findViewById(R.id.textView1);
		view.setText(str2);
	}

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
}
