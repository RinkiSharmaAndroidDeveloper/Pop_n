package com.popn;

/**
 * Created by VS3 on 6/20/2017.
 */

public interface AsyncResult<TData> {
    void success(TData data);
    void error(String error);



}

