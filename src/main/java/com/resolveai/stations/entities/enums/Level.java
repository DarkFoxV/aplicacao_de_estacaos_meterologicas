package com.resolveai.stations.entities.enums;

import lombok.Getter;
@Getter
public enum Level  {
    INFO("info"),
    WARN("warn"),
    ERROR("error");

    private final String level;

    Level(String role) {
        this.level = role;
    }

}
