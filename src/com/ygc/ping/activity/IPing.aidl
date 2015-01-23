package com.ygc.ping.activity;  
import com.ygc.ping.activity.PingCallBack;
interface IPing{  
    void startPing(String str,PingCallBack pingCallBack);  
    void stopPing();  
} 