package com.qicaiz.wifiled;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ConnectThread mConnectThread;
    private Socket mSocket;
    private EditText mEtIP;
    private EditText mEtPort;
    private Button mBtnRedOn;
    private Button mBtnRedOff;
    private Button mBtnYellowOn;
    private Button mBtnYellowOff;
    private Button mBtnBlueOn;
    private Button mBtnBlueOff;
    private PrintStream out;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button connectBtn = (Button) findViewById(R.id.btn_connect);
        mEtIP = (EditText) findViewById(R.id.et_ip);
        mEtPort = (EditText) findViewById(R.id.et_port);
        mBtnRedOn = (Button) findViewById(R.id.btn_red_on);
        mBtnRedOff = (Button) findViewById(R.id.btn_red_off);
        mBtnYellowOn = (Button) findViewById(R.id.btn_yellow_on);
        mBtnYellowOff = (Button) findViewById(R.id.btn_yellow_off);
        mBtnBlueOn = (Button) findViewById(R.id.btn_blue_on);
        mBtnBlueOff = (Button) findViewById(R.id.btn_blue_off);
        connectBtn.setOnClickListener(this);
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
                String ip = mEtIP.getText().toString();
                int port = Integer.valueOf(mEtPort.getText().toString());
                if (mSocket == null) {
                    mConnectThread = new ConnectThread(ip, port);
                    mConnectThread.start();
                }

                break;
            case R.id.btn_red_on:
                out.print("1");
                out.flush();
                break;
            case R.id.btn_red_off:
                out.print("2");
                out.flush();
                break;
            case R.id.btn_yellow_on:
                out.print("3");
                out.flush();
                break;
            case R.id.btn_yellow_off:
                out.print("4");
                out.flush();
                break;
            case R.id.btn_blue_on:
                out.print("5");
                out.flush();
                break;
            case R.id.btn_blue_off:
                out.print("6");
                out.flush();
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
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}