package com.mvp_0726.common.network;

//public class ServerResultFunction implements io.reactivex.functions.Function<ResponseCustom, Object> {
public class ServerResultFunction implements io.reactivex.functions.Function<Object, Object> {
    @Override
    public Object apply(Object responseCustom) {
        return responseCustom;
    }
}
