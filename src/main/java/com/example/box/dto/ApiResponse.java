package com.example.box.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.io.Serializable;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<T> implements Serializable {
    private String responseCode;
    private String responseMsg;
    private String responseDesc;
    private T data;

    public ApiResponse() {}

    public ApiResponse(String responseCode, String responseMsg, String responseDesc, T data) {
        this.responseCode = responseCode;
        this.responseMsg = responseMsg;
        this.responseDesc = responseDesc;
        this.data = data;
    }

    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>("00","Success",null, data);
    }

    public static <T> ApiResponse<T> successMessage(String responseDesc) {
        return new ApiResponse<>("00", "Success", responseDesc, null);
    }

    public static <T> ApiResponse<T> failed(T data) {
        return new ApiResponse<>("99","Failed",null, data);
    }

    public static <T> ApiResponse<T> error(String errorMsg) {
        return new ApiResponse<>("55","Failed", errorMsg, null);
    }

    public String getResponseCode() { return responseCode; }
    public void setResponseCode(String responseCode) { this.responseCode = responseCode; }
    public String getResponseMsg() { return responseMsg; }
    public void setResponseMsg(String responseMsg) { this.responseMsg = responseMsg; }
    public String getResponseDesc() { return responseDesc; }
    public void setResponseDesc(String responseDesc) { this.responseDesc = responseDesc; }
    public T getData() { return data; }
    public void setData(T data) { this.data = data; }
}
