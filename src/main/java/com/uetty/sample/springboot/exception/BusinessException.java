package com.uetty.sample.springboot.exception;


import com.uetty.sample.springboot.constant.ResponseCode;

/**
 * 业务异常类
 */
public class BusinessException extends RuntimeException {

    ResponseCode responseCode = ResponseCode.INTERNAL_ERROR;
    boolean untracePrint = false;

    /**
     * 业务异常
     */
    public BusinessException() {
        super();
    }

    public BusinessException(ResponseCode responseCode) {
        super("business exception");
        this.responseCode = responseCode;
    }

    /**
     * 业务异常
     * @param message 异常消息
     */
    public BusinessException(String message) {
        super(message);
    }

    /**
     * 业务异常
     * @param responseCode 错误码
     * @param message 异常消息
     */
    public BusinessException(ResponseCode responseCode, String message) {
        super(message);
        this.responseCode = responseCode;
    }
    /**
     * 业务异常
     * @param throwable 导致该业务异常的异常
     */
    public BusinessException(Throwable throwable) {
        super(throwable);
    }
    /**
     * 业务异常
     * @param message 异常消息
     * @param throwable 导致该业务异常的异常
     */
    public BusinessException(String message, Throwable throwable) {
        super(message, throwable);
    }
    /**
     * 业务异常
     * @param responseCode 错误码
     * @param message 异常消息
     * @param throwable 导致该业务异常的异常
     */
    public BusinessException(ResponseCode responseCode, String message, Throwable throwable) {
        super(message, throwable);
        this.responseCode = responseCode;
    }

    /**
     * 业务异常
     * @param untracePrint 指定是否减少printStrackTrace的打印信息行数
     */
    public BusinessException(boolean untracePrint) {
        super();
        this.untracePrint = untracePrint;
        setUntrace();
    }

    /**
     * 业务异常
     * @param message 异常信息
     * @param untracePrint 指定是否减少printStrackTrace的打印信息行数
     */
    public BusinessException(String message, boolean untracePrint) {
        super(message);
        this.untracePrint = untracePrint;
        setUntrace();
    }

    /**
     * 业务异常
     * @param responseCode 错误码
     * @param message 异常信息
     * @param untracePrint 指定是否减少printStrackTrace的打印信息行数
     */
    public BusinessException(ResponseCode responseCode, String message, boolean untracePrint) {
        super(message);
        this.responseCode = responseCode;
        setUntrace();
    }

    private void setUntrace() {
        if (!untracePrint) {
            return;
        }
        StackTraceElement[] stackTrace = getStackTrace();
        if (stackTrace.length >= 2) {
            stackTrace = new StackTraceElement[] {stackTrace[1]};
        } else {
            stackTrace = new StackTraceElement[0];
        }
        setStackTrace(stackTrace);
    }

    public ResponseCode getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(ResponseCode responseCode) {
        this.responseCode = responseCode;
    }
}
