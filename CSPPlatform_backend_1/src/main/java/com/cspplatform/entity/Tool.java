package com.cspplatform.entity;

public class Tool {
    private String name;
    private Integer value;

    public Tool(String name, Integer value) {
        this.name = name;
        this.value = value;
    }
    public Tool(){}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "Tool{" +
                "name='" + name + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}
