package com.example.ipgeofinder.service;

import com.example.ipgeofinder.domain.IpInfo;
import com.example.ipgeofinder.exception.IpGeoFinderException;

public interface InfoService<T> {
    T getInfo(String data) throws IpGeoFinderException;
}
