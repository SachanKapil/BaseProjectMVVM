package com.baseprojectmvvm.data.model;

import androidx.annotation.NonNull;

public class FailureResponse {

    private int errorCode;
    private String errorMessage;

    public FailureResponse(int errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public FailureResponse() {
    }

    public static FailureResponse getGenericError() {
        FailureResponse response = new FailureResponse();
        response.errorCode = 1818;
        response.errorMessage = "Something went wrong";
        return response;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    @NonNull
    @Override
    public String toString() {
        return "Error Code : " + errorCode + " Error Message : " + errorMessage;
    }
}
