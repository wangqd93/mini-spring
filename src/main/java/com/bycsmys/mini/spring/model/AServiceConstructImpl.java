package com.bycsmys.mini.spring.model;

public class AServiceConstructImpl implements AServiceConstruct{


    private String name;
    private int level;

    public AServiceConstructImpl(String name, int level) {
        this.name = name;
        this.level = level;
    }
}
