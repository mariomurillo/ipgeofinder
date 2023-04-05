package com.example.ipgeofinder.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@Builder
public class IpInfo {

    @JsonProperty("country_code")
    private String countryCode;

    private String region;

    private String city;

    private String isp;
}
