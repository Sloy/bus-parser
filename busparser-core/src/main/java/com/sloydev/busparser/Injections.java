package com.sloydev.busparser;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.sloydev.busparser.submodules.api.internal.SingleElementToListDeserializer;
import com.sloydev.busparser.submodules.api.internal.model.SeccionApiModel;
import com.sloydev.jsonadapters.JsonAdapter;
import com.sloydev.jsonadapters.gson.GsonAdapter;

import java.lang.reflect.Type;
import java.util.List;

public class Injections {

    public static JsonAdapter getJsonAdapter() {
        Type myOtherClassListType = new TypeToken<List<SeccionApiModel>>() {
        }.getType();
        SingleElementToListDeserializer<SeccionApiModel> adapter = new SingleElementToListDeserializer(SeccionApiModel.class);
        Gson gson = new GsonBuilder()
          .registerTypeAdapter(myOtherClassListType, adapter)
          .create();
        return new GsonAdapter(gson);
    }
}
