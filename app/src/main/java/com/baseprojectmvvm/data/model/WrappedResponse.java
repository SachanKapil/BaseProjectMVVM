package com.baseprojectmvvm.data.model;

public class WrappedResponse<T> {

    private T data;
    private FailureResponse failureResponse;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public FailureResponse getFailureResponse() {
        return failureResponse;
    }

    public void setFailureResponse(FailureResponse failureResponse) {
        this.failureResponse = failureResponse;
    }
}
