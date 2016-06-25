package com.example.administrator.httplib;

import java.util.HashMap;

/**
 * Created by Administrator on 2016/6/22 0022.
 */
abstract public class Request<T> {

    private String url;
    private Method method;
    private Callback callback;

    public Request(String url, Method method, Callback callback) {
        this.url = url;
        this.method = method;
        this.callback = callback;
    }

    public enum Method {
        GET, POST
    }

    public Callback getCallback() {
        return callback;
    }

    public void setCallback(Callback callback) {
        this.callback = callback;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public void onError(Exception e) {
        callback.onError(e);
    }

    protected void onResponse(T res){
        callback.onResponse(res);
    }

    public static interface Callback<T> {
        void onError(Exception e);

        void onResponse(T response);
    }

    public HashMap<String,String> getPostParams(){
        return null;
    }

    abstract public void dispatchContent(byte[] content);

}
