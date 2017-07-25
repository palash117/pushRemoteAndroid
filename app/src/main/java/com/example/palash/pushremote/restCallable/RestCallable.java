package com.example.palash.pushremote.restCallable;


import android.content.Context;

import java.util.concurrent.Callable;

/**
 * Created by palash on 22/7/17.
 */

public abstract class RestCallable implements Callable<String> {

    private StringBuilder sbr = null;
    private String resposne = null;

    Context context= null;

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public StringBuilder getSbr() {
        return sbr;
    }

    public void setSbr(StringBuilder sbr) {
        this.sbr = sbr;
    }

    public String getResposne() {
        return resposne;
    }

    public void setResposne(String resposne) {
        this.resposne = resposne;
    }
}
