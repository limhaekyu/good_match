package com.example.good_match.global.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class ApiResponseDto<T> {

    private Integer statusCode;

    private String message;

    private T data;

    private Pagination pagination;

    // Data가 없는 ApiResponseDto
    public static <T> ApiResponseDto<T> of(Integer responseStatusCode, String responseMessage){
        return (ApiResponseDto<T>) ApiResponseDto.builder()
                .statusCode(responseStatusCode)
                .message(responseMessage)
                .build();
    }

    //Data가 있는 ApiResponseDto
    public static <T> ApiResponseDto<T> of(Integer responseStatusCode, String  responseMessage, T data ){
        return (ApiResponseDto<T>) ApiResponseDto.builder()
                .statusCode(responseStatusCode)
                .message(responseMessage)
                .data(data)
                .build();
    }

    //Pagination ApiResponseDto
    public static <T> ApiResponseDto<T> of(Integer responseStatusCode, String  responseMessage, T data, Pagination pagination){
        return (ApiResponseDto<T>)  ApiResponseDto.builder()
                .statusCode(responseStatusCode)
                .message(responseMessage)
                .data(data)
                .pagination(pagination)
                .build();
    }
}
