package com.example.administrator.httplib;

import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * Created by Administrator on 2016/6/22 0022.
 */
public class RequestQueue {

    private BlockingDeque<Request> requestQueue = new LinkedBlockingDeque<>();

    private final int MAX_THREAD = 3;

    private NetworkDispatcher[] worker = new NetworkDispatcher[MAX_THREAD];

    public RequestQueue() {
        iniDispatcher();
    }

    private void iniDispatcher() {
        for (int i = 0; i < worker.length; i++) {
            worker[i] = new NetworkDispatcher(requestQueue);
            worker[i].start();
        }
    }

    public void addRequest(Request request) {
        requestQueue.add(request);
    }

    private void  stop(){
        for (int i = 0; i < worker.length; i++) {
            worker[i].flag = false;
            worker[i].interrupt();
        }
    }

}
