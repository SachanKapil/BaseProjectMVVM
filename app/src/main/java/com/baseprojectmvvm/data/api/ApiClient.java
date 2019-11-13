package com.baseprojectmvvm.data.api;

import com.baseprojectmvvm.data.model.BaseResponse;
import com.baseprojectmvvm.data.model.onboarding.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

interface ApiClient {

    @POST("login")
    Call<BaseResponse<User>> login(@Body User user);


    @POST("signup")
    Call<BaseResponse<User>> signUp(@Body User user);


//    @GET("/api/user/user-event")
//    Call<BaseResponse<EventsResponse>> getEvents(@QueryMap HashMap<String, Object> eventDetails);

//    @GET("/api/user/my-booking")
//    Call<BaseResponse<MyBookingResponse>> getMyBookings(@Query("page") long page);

//    @GET("/api/user/my-booking")
//    Call<BaseResponse<MyBookingResponse>> getInviteToEvents(@Query("page") int page,
//                                                            @Query("match_id") int matchId,
//                                                            @Query("status") String status);


//    @GET("/api/user/accept-invitation/{invitationId}")
//    Call<BaseResponse<InviteSentReceivedDetails>> sendAcceptInviteRequest(@Path("invitationId") int id);


//    @GET("api/user/get-saved-card")
//    Call<BaseResponse<ArrayList<SavedCardsResponse>>> getSavedCard();

}