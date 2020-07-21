package com.sample.springjwt.exceptions;

public class AppMsg {

    private String code;
    private String message;

    private AppMsg(Builder builder) {
        this.code = builder.code;
        this.message = builder.message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public static class Builder {
        private String code;
        private String message;

        public Builder(String code, String message) {
            this.code = code;
            this.message = message;
        }

        public Builder code(String code) {
            this.code = code;
            return this;
        }

        public Builder message(String message) {
            this.message = message;
            return this;
        }

        public AppMsg build() {
            return new AppMsg(this);
        }
    }

    @Override
    public String toString() {
        return code + ":" + message;
    }
}
