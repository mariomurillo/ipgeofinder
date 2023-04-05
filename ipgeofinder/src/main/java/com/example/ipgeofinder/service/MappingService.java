package com.example.ipgeofinder.service;

import java.util.List;

public interface MappingService<T> {

    List<T> mapBlockToEntity(String[][] block, int count);
}
