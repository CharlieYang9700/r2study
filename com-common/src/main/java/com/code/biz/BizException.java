package com.code.biz;

/**
 * @author ping
 */
public class BizException extends Exception {

    private static final long serialVersionUID = -1477327071538069456L;
    private final String errorCode;
    private final String errorMsg;

    public BizException(String errorCode, String errorMsg) {
        super(String.format("错误编码:%s,错误信息:%s", errorCode, errorMsg));
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }

    public BizException(String errorMsg) {
        super(String.format("错误编码:100,错误信息:%s", errorMsg));
        this.errorCode = "100";
        this.errorMsg = errorMsg;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

}