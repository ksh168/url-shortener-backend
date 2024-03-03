package com.urlshortener.api.ResponseObject;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
// @Component
@AllArgsConstructor
public class ResponseObject {
    private Boolean isSuccess;
    private String msg;
    private Integer id;

    // no need of this after @AllArgsConstructor
    // private ResponseObject(Boolean isSuccess, String msg, Integer id, String
    // error) {
    // this.isSuccess = isSuccess;
    // this.msg = msg;
    // this.id = id;
    // this.error = error;
    // }
}