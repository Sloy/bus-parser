package com.sloydev.busparser.api;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class SingleElementToListDeserializer<T> implements JsonDeserializer<List<T>> {

private final Class<T> clazz;

public SingleElementToListDeserializer(Class<T> clazz) {
    this.clazz = clazz;
}

public List<T> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
    List<T> resultList = new ArrayList<>();
    if (json.isJsonArray()) {
        for (JsonElement e : json.getAsJsonArray()) {
            resultList.add(context.<T>deserialize(e, clazz));
        }
    } else if (json.isJsonObject()) {
        resultList.add(context.<T>deserialize(json, clazz));
    } else {
        throw new RuntimeException("Unexpected JSON type: " + json.getClass());
    }
    return resultList;
    }
}