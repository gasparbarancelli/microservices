package com.gasparbarancelli.produtos.util;

import org.openapitools.jackson.nullable.JsonNullable;

import java.util.function.Consumer;

public class JsonUtil {

    public static <T> void changeIfPresent(JsonNullable<T> nullable, Consumer<T> consumer) {
        if (nullable.isPresent()) {
            consumer.accept(nullable.get());
        }
    }

}
