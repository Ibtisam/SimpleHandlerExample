package com.example.asimplehandlerexample;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import androidx.annotation.NonNull;

public class WorkerThreadB implements Runnable{
    private Handler uiHandler;
    private Handler threadAHandler;
    private ThreadBHandler threadBHandler;
    private Package aPackage;

    public WorkerThreadB(Handler uiHandler, Package aPackage){
        this.uiHandler = uiHandler;
        this.aPackage = aPackage;
        threadBHandler = new ThreadBHandler(Looper.myLooper());
    }

    @Override
    public void run() {
        aPackage.put("Hi Thread A.");
        Message msg = threadAHandler.obtainMessage(0,aPackage);
        threadAHandler.sendMessage(msg);
        
        aPackage.put("I am doing my work.");
        msg = threadAHandler.obtainMessage(0,aPackage);
        threadAHandler.sendMessage(msg);
    }

    public ThreadBHandler getThreadBHandler(){
        return threadBHandler;
    }

    public void setThreadAHandler(Handler threadAHandler) {
        this.threadAHandler = threadAHandler;
    }

    class ThreadBHandler extends Handler{
        public ThreadBHandler(@NonNull Looper looper){
            super(looper);
        }
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0:
                    Package aPackage= (Package) msg.obj;
                    String messageText = aPackage.take();
                    Message message = uiHandler.obtainMessage(0,"Message from Thread A: "+messageText);
                    uiHandler.sendMessage(message);
                    break;
            }
        }
    }
}
