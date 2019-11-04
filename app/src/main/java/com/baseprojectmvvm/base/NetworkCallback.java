package com.baseprojectmvvm.base;


import com.baseprojectmvvm.data.model.BaseResponse;
import com.baseprojectmvvm.data.model.FailureResponse;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public abstract class NetworkCallback<T> implements Callback<BaseResponse<T>> {

    public static final int AUTH_FAILED = 99;
    private static final int NO_INTERNET = 9;
    private static final int NOT_ABLE_TO_CONNECT = 999;

    abstract void onSuccess(T t);

    abstract void onFailure(FailureResponse failureResponse);

    abstract void onError(Throwable t);

    @Override
    public void onResponse(Call<BaseResponse<T>> call, Response<BaseResponse<T>> response) {
        if (response.isSuccessful()
                && response.body() != null
                && response.body().getStatus().equalsIgnoreCase("success")) {
            onSuccess(response.body().getData());
        } else {
            FailureResponse failureErrorBody = null;
            try {
                failureErrorBody = getFailureErrorBody(response);
            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }
            onFailure(failureErrorBody);
        }
    }

    @Override
    public void onFailure(Call<BaseResponse<T>> call, Throwable t) {
        if (t instanceof SocketTimeoutException || t instanceof UnknownHostException) {
            FailureResponse failureResponseForNoNetwork = getFailureResponseForNoNetwork();
            onFailure(failureResponseForNoNetwork);
        } else if (t instanceof ConnectException) {
            FailureResponse failureResponseForUnableToConnect = getFailureResponseForUnableToConnect();
            onFailure(failureResponseForUnableToConnect);
        } else {
            onError(t);
        }
    }

    private FailureResponse getFailureResponseForNoNetwork() {
        FailureResponse failureResponse = new FailureResponse();
        failureResponse.setErrorMessage("Please check your network and try again");
        failureResponse.setErrorCode(NO_INTERNET);
        return failureResponse;
    }

    private FailureResponse getFailureResponseForUnableToConnect() {
        FailureResponse failureResponse = new FailureResponse();
        failureResponse.setErrorMessage("Unable to connect to Server");
        failureResponse.setErrorCode(NOT_ABLE_TO_CONNECT);
        return failureResponse;
    }

    /**
     * Create your custom failure response out of server response
     * Also save Url for any further use
     *
     * @param body
     */
    private FailureResponse getFailureErrorBody(Response<BaseResponse<T>> body) throws IOException, JSONException {
        if (body == null)
            return FailureResponse.getGenericError();
        FailureResponse failureResponse = new FailureResponse();
        failureResponse.setErrorCode(body.code());
        if (body.errorBody() != null) {
            String errorResponse = body.errorBody().string();
            JSONObject jsonObj = new JSONObject(errorResponse);
            failureResponse.setErrorMessage(jsonObj.getString("message"));
        } else if (body.body() != null) {
            failureResponse.setErrorMessage(body.body().getMessage());
        }
        return failureResponse;
    }
}

