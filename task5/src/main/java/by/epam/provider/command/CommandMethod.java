package by.epam.provider.command;

import lombok.Getter;

public enum CommandMethod {

    GET("GET"),
    POST("POST"),
    PUT("PUT"),
    DELETE("DELETE");

    @Getter
    private String value;

    CommandMethod(String valueNew) {
        this.value = valueNew;
    }
}
