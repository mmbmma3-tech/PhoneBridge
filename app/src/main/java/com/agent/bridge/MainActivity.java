package com.agent.bridge;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import java.net.Socket;

public class MainActivity extends Activity {
    private TextView statusText;
    private EditText ipInput;
    private Button connectBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        statusText = (TextView) findViewById(R.id.statusText);
        ipInput = (EditText) findViewById(R.id.ipInput);
        connectBtn = (Button) findViewById(R.id.connectBtn);

        connectBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                connectToBridge(ipInput.getText().toString());
            }
        });
    }

    private void connectToBridge(final String ip) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            statusText.setText("🔄 جاري الاتصال...");
                        }
                    });
                    Socket socket = new Socket(ip, 8765);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            statusText.setText("🟢 متصل بالجسر بنجاح!");
                        }
                    });
                } catch (final Exception e) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            statusText.setText("🔴 خطأ: " + e.getMessage());
                        }
                    });
                }
            }
        }).start();
    }
}
