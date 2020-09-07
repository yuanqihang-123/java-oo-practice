package com.twu.myException;

/**
 * @author yuan
 * @create 2020-09-07 17:03
 */
public class MyException extends Exception{
    private String message;  //异常对应的描述信息

    public MyException() {
        super();
    }


    public MyException(String message) {
        super(message);
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
