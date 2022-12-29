package com.work.props;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class JmsData {

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("message")
    @Expose
    private String message;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "JmsData{" +
                "name='" + name + '\'' +
                ", id=" + id +
                ", message='" + message + '\'' +
                '}';
    }
}