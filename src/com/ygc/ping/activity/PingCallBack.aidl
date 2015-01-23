package com.ygc.ping.activity;  
interface PingCallBack{  
    void pingStart();
    void ping(String message);
    void pingStop();
} 