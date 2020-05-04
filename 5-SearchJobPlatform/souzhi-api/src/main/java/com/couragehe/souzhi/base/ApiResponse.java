package com.couragehe.souzhi.base;

import java.io.Serializable;

/**
 * @PackageName:com.couragehe.souzhi.base
 * @ClassName:ApiResponse
 * @Description: Api格式封装
 * @Autor:CourageHe
 * @Date: 2020/4/23 22:37
 */
public class ApiResponse implements Serializable {
    private int code;
    private String message;
    private Object data;
    private boolean more;

    public ApiResponse(int code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }
    public ApiResponse(int code, String message, Object data, boolean more) {
        this.code = code;
        this.message = message;
        this.data = data;
        this.more = more;
    }

    public ApiResponse() {
        this.code = Status.SUCCESS.getCode();
        this.message = Status.SUCCESS.getStandardMessage();
    }


    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public boolean isMore() {
        return more;
    }

    public void setMore(boolean more) {
        this.more = more;
    }

    public static ApiResponse ofMessage(int code ,String message){
        return new ApiResponse(code,message,null);
    }
    public static ApiResponse ofSuccess(int code ,String message){
        return new ApiResponse(Status.SUCCESS.getCode(),Status.SUCCESS.getStandardMessage(),null);
    }
    public static ApiResponse ofStatus(Status status){
        return new ApiResponse(status.getCode(),status.getStandardMessage(),null);
    }

    public enum Status{
        SUCCESS(200,"OK"),
        BAD_REQUEST(400,"Bad Request"),
        INTERNAL_SERVER_ERROR(500,"Unknown Internal Error"),
        NOT_VALID_PARM(40005,"Not Valid Params"),
        NOT_SUPPORTED_OPERATION(40006,"Not Supported Operation"),
        NOT_LOGIN(50000,"Not Login");

        private int code;
        private String standardMessage;

        Status(int code,String standardMessage){
            this.code = code;
            this.standardMessage = standardMessage;
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getStandardMessage() {
            return standardMessage;
        }

        public void setStandardMessage(String standardMessage) {
            this.standardMessage = standardMessage;
        }
    }
}
