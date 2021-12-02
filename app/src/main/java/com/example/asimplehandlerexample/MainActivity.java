package com.example.asimplehandlerexample;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.TextView;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {
    private TextView textView;
    private UIHandler uiHandler;
    private WorkerThreadA workerThreadA;
    private WorkerThreadB workerThreadB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.textView);
        textView.setMovementMethod(new ScrollingMovementMethod());
        uiHandler = new UIHandler(Looper.getMainLooper());
    }

    public void startBClick(View v){
        Package aPackage = new Package();
        workerThreadA = new WorkerThreadA(uiHandler,aPackage);
        workerThreadB = new WorkerThreadB(uiHandler,aPackage);

        workerThreadA.setThreadBHandler(workerThreadB.getThreadBHandler());
        workerThreadB.setThreadAHandler(workerThreadA.getThreadAHandler());

        //ExecutorService executorService = Executors.newFixedThreadPool(2);
        //executorService.execute(workerThreadA);
        //executorService.execute(workerThreadB);
        Thread threadA = new Thread(workerThreadA);
        Thread threadB = new Thread(workerThreadB);

        threadA.start();
        threadB.start();
    }

    class UIHandler extends Handler {
        public UIHandler(@NonNull Looper looper){
            super(looper);
        }

        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0:
                    textView.append((String)msg.obj);
                    textView.append("\n");
                    break;
            }
        }
    }
}