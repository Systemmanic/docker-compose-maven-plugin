package com.dkanejs.maven.plugins.docker.compose;

import lombok.Getter;

public enum Command {
    UP("up"), DOWN("down");

    @Getter
    private String value;

    Command(String value) {
        this.value = value;
    }
}
