package com.uj.couchbase.model;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Response {
    private String status;
    private String document;
    private JsonNode data;
    private String message;

    public Response(String status, String document){
        this.status = status;
        this.document = document;
    }
}
