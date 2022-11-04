package com.iluwatar.tabledatagateway;

/**
 * The exception that the failure of SQL query would be thrown
 * @author Taowen Huang
 *
 */
public class QueryException extends RuntimeException{

    private static final long serialVersionUID = 2L;

    public QueryException(){}

    public QueryException(String msg){
        super(msg);
    }
}
