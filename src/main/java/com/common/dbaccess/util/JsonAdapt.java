package com.common.dbaccess.util;

import java.lang.reflect.Type;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class JsonAdapt implements JsonSerializer<Integer>, JsonDeserializer<Integer> {

	@Override
	public Integer deserialize(JsonElement json, Type arg1, JsonDeserializationContext arg2) {
		try {
			if (json.getAsString().equals("") || json.getAsString().equals("null")) {
				return 0;
			}
			return json.getAsInt();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public JsonElement serialize(Integer src, Type arg1, JsonSerializationContext arg2) {
		return new JsonPrimitive(src);
	}

}
