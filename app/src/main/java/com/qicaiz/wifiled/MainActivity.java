package com.qicaiz.wifiled;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    /**TCP连接线程*/
    private ConnectThread mConnectThread;
    /**Socket套接字*/
    private Socket mSocket;
    /**服务器IP*/
    private EditText mEtIP;
    /**服务器端口*/
    private EditText mEtPort;
    /**连接按钮*/
    private Button mBtnConnect;
    /**红色LED灯开按钮*/
    private Button mBtnRedOn;
    /**红色LED灯关按钮*/
    private Button mBtnRedOff;
    /**黄色LED灯开按钮*/
    private Button mBtnYellowOn;
    /**黄色LED灯关按钮*/
    private Button mBtnYellowOff;
    /**蓝色LED灯开按钮*/
    private Button mBtnBlueOn;
    /**蓝色LED灯关按钮*/
    private Button mBtnBlueOff;
    private PrintStream out;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mBtnConnect = (Button) findViewById(R.id.btn_connect);
        mEtIP = (EditText) findViewById(R.id.et_ip);
        mEtPort = (EditText) findViewById(R.id.et_port);
        mBtnRedOn = (Button) findViewById(R.id.btn_red_on);
        mBtnRedOff = (Button) findViewById(R.id.btn_red_off);
        mBtnYellowOn = (Button) findViewById(R.id.btn_yellow_on);
        mBtnYellowOff = (Button) findViewById(R.id.btn_yellow_off);
        mBtnBlueOn = (Button) findViewById(R.id.btn_blue_on);
        mBtnBlueOff = (Button) findViewById(R.id.btn_blue_off);
        mBtnConnect.setOnClickListener(this);
        mBtnRedOn.setOnClickListener(this);
        mBtnRedOff.setOnClickListener(this);
        mBtnYellowOn.setOnClickListener(this);
        mBtnYellowOff.setOnClickListener(this);
        mBtnBlueOn.setOnClickListener(this);
        mBtnBlueOff.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_connect:
                //连接
                if (mSocket == null || !mSocket.isConnected()) {
                    String ip = mEtIP.getText().toString();
                    int port = Integer.valueOf(mEtPort.getText().toString());
                    mConnectThread = new ConnectThread(ip, port);
                    mConnectThread.start();
                }
                if (mSocket != null && mSocket.isConnected()) {
                    try {
                        mSocket.close();
                        mBtnConnect.setText("连接");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                break;
            case R.id.btn_red_on:
                if (out != null) {
                    out.print("1");
                    out.flush();
                }
                break;
            case R.id.btn_red_off:
                if (out != null) {
                    out.print("2");
                    out.flush();
                }
                break;
            case R.id.btn_yellow_on:
                if (out != null) {
                    out.print("3");
                    out.flush();
                }
                break;
            case R.id.btn_yellow_off:
                if (out != null) {
                    out.print("4");
                    out.flush();
                }
                break;
            case R.id.btn_blue_on:
                if (out != null) {
                    out.print("5");
                    out.flush();
                }
                break;
            case R.id.btn_blue_off:
                if (out != null) {
                    out.print("6");
                    out.flush();
                }
                break;
        }
    }

    private class ConnectThread extends Thread {
        private String ip;
        private int port;

        public ConnectThread(String ip, int port) {
            this.ip = ip;
            this.port = port;
        }

        @Override
        public void run() {
            try {
                mSocket = new Socket(ip, port);
                out = new PrintStream(mSocket.getOutputStream());
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mBtnConnect.setText("断开");
                    }
                });
                new HeartBeatThread().start();
            } catch (IOException e) {
                e.printStackTrace();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(MainActivity.this, "连接失败", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }
    }

    private class HeartBeatThread extends Thread {
        @Override
        public void run() {
            while (true) {
                try {
                    Thread.sleep(3000);
                    if (!mSocket.isConnected()) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mBtnConnect.setText("连接");
                                Toast.makeText(MainActivity.this, "连接已关闭", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
