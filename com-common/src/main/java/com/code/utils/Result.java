package com.code.utils;

import com.code.core.common.BaseErrorCode;

/**
 * @author ping
 */
public class Result<T> {
    private Boolean success;
    private ResultStatus status;
    /**
     * 操作代码（0：成功，1：失败）（兼容以前往外提供的接口）
     */
    private int code;
    private String errorCode;
    private String errorMsg;
    private T data;
    // 提示信息（兼容以前往外提供的接口）
    private String message;


    public Result(String errorCode, String errorMsg) {
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
        this.status = ResultStatus.ERROR;
        this.success = Boolean.FALSE;
        this.message = errorMsg;
        this.code = 1;
    }


    public Result(int code, String errorMsg, T data) {
        Result<T> result = new Result<>(data);
        result.code = code;
        result.errorMsg = errorMsg;
        result.message = errorMsg;
        result.status = result.code == 0 ? ResultStatus.OK : ResultStatus.ERROR;
    }

    public Result(T data) {
        this.data = data;
        this.status = ResultStatus.OK;
        this.success = Boolean.TRUE;
        this.code = 0;
    }

    public Result(ResultStatus status) {
        this.status = status;
        this.code = ResultStatus.OK.equals(status) ? 0 : 1;
        this.success = ResultStatus.OK.equals(status);
    }

    public Result() {
        this.code = 0;
        this.status = ResultStatus.OK;
        this.success = Boolean.TRUE;
    }

    public static Result error(String errorCode, String errorMsg) {
        return new Result(errorCode, errorMsg);
    }

    public static Result error(BaseErrorCode errorCode) {
        return new Result(errorCode.getCode(), errorCode.getDesc());
    }

    public static Result error(String message) {
        return new Result("100", message);
    }


    public static <T> Result<T> success(T data) {
        return new Result<>(data);
    }

    public static Result<String> success() {
        return new Result<>();
    }


    public String getErrorCode() {
        return errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public T getData() {
        return data;
    }

    public int getCode() {
        return code;
    }


    public String getErrorMessage() {
        return message;
    }

    public ResultStatus getStatus() {
        return status;
    }

    /**
     * 返回状态
     */
    public enum ResultStatus {
        /**
         * 成功
         */
        OK,
        /**
         * 失败
         */
        ERROR
    }
}