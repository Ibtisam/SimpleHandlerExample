package com.example.asimplehandlerexample;

public class Package {

    private String message;

    private boolean empty = true;

    public synchronized String take() {
        // Wait until message is
        // available.
        //guarded block
        while (empty) {
            try {
                wait();
            } catch (InterruptedException e) {
            }
        }
        // Toggle status.
        empty = true;

        notify();
        return message;
    }

    public synchronized void put(String message) {
        // Wait until message has
        // been retrieved.
        while (!empty) {
            try {
                wait();
            } catch (InterruptedException e) {
            }
        }
        // Toggle status.
        empty = false;
        // Store message.
        this.message = message;

        notify();
    }
}