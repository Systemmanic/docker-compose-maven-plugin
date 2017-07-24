package com.dkanejs.maven.plugins;

class DockerComposeException extends Exception {

    DockerComposeException() {
        super();
    }

    DockerComposeException(String message) {
        super(message);
    }

    DockerComposeException(String message, Throwable cause) {
        super(message, cause);
    }

    DockerComposeException(Throwable cause) {
        super(cause);
    }

    DockerComposeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
