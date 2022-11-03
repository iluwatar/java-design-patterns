package com.iluwatar.tabledatagateway;

public class QueryException extends RuntimeException{

    private static final long serialVersionUID = 2L;

    public QueryException(){}

    public QueryException(String msg){
        super(msg);
    }
}
