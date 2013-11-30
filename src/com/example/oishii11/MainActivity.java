package com.example.oishii11;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnKeyListener;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends Activity implements Runnable {
	ArrayList<Message> list = new ArrayList<Message>();
	Socket socket = null;
	Handler handler = new Handler();

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
					update(new Message(str));
					edit.setText("");
					return true;
				}
				return false;
			}
		});
		new Thread(this).start();
	}

	private void update(Message str) {
		for (Message msg : list) {
			if (msg.getId() == str.getId()) {
				return;
			}
		}
		list.add(0, str);
		try {
			OutputStream os = socket.getOutputStream();
			// protocol "<id>:<body>\n"
			os.write(Integer.toString(str.getId()).getBytes());
			os.write(":".getBytes());
			os.write(str.getBody().getBytes());
			os.write("\n".getBytes());
			os.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		final StringBuffer str2 = new StringBuffer();
		int line = 0;
		for (Message message : list) {
			if (line > 20) {
				break;
			}
			str2.append(message.getBody());
			str2.append('\n');
			line++;
		}
		this.handler.post(new Runnable() {
			@Override
			public void run() {
				TextView view = (TextView) findViewById(R.id.textView1);
				view.setText(str2);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void run() {
		try {
			socket = new Socket("192.168.0.201", 4444);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			InputStream is = socket.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(is));
			while (true) {
				String line = reader.readLine();
				int index = line.indexOf(':');
				if (index >= 0) {
					String id = line.substring(0, index);
					String body = line.substring(index + 1);
					Message message = new Message(Integer.valueOf(id), body);
					update(message);
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
    
}
