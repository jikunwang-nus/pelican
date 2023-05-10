package com.charsmart.data.cases.common;

import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * Result
 * </p>
 *
 * @author lanmi.xin@charsmart.com
 * @since 2020/2/22
 */
@Getter
@Setter
public class Result {
    private boolean success = false;
    private String message;
    private String code;
    private Object data;

    public static Result success() {
        Result result = new Result();
        result.setSuccess(true);
        return result;
    }

    public static Result success(Object data) {
        Result result = new Result();
        result.setSuccess(true);
        result.setData(data);
        return result;
    }

    public static Result fail(Object data, String message) {
        Result result = new Result();
        result.setSuccess(false);
        result.setMessage(message);
        result.setData(data);
        return result;
    }

    public static Result fail() {
        return new Result();
    }

    public static Result fail(String message) {
        Result result = new Result();
        result.setMessage(message);
        return result;
    }

    public static Result fail(String code, String message) {
        Result result = new Result();
        result.setCode(code);
        result.setMessage(message);
        return result;
    }
}
