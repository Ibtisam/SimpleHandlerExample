package com.example.asimplehandlerexample;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import androidx.annotation.NonNull;

public class WorkerThreadA implements Runnable{
    private Handler uiHandler;
    private Handler threadBHandler;
    private ThreadAHandler threadAHandler;
    Package aPackage;

    public WorkerThreadA(Handler uiHandler, Package aPackage){
        this.uiHandler = uiHandler;
        this.aPackage = aPackage;
        threadAHandler = new ThreadAHandler(Looper.myLooper());
    }
    @Override
    public void run() {
        aPackage.put("Hello Thread B.");
        Message msg = threadBHandler.obtainMessage(0, aPackage);
        threadBHandler.sendMessage(msg);

        aPackage.put("What are you doing?");
        msg = threadBHandler.obtainMessage(0, aPackage);
        threadBHandler.sendMessage(msg);
    }

    public ThreadAHandler getThreadAHandler(){
        return threadAHandler;
    }

    public void setThreadBHandler(Handler threadBHandler){
        this.threadBHandler = threadBHandler;
    }

    class ThreadAHandler extends Handler{
        public ThreadAHandler(@NonNull Looper looper){
            super(looper);
        }
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0:
                    Package aPackage= (Package) msg.obj;
                    String messageText = aPackage.take();
                    Message message = uiHandler.obtainMessage(0, "Message from Thread B: " + messageText);
                    uiHandler.sendMessage(message);
                    break;
            }
        }
    }
}
