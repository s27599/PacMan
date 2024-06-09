package model;

import controler.MyTimer;


import java.io.Serializable;

public class Player implements Serializable, Comparable<Player>{

    private String name;
    private MyTimer time;

    public Player(String name, MyTimer time) {
        this.name = name;
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public MyTimer getTime() {
        return time;
    }

    @Override
    public String toString() {
        return  "name: " + name + " Time: "+time;
    }

    @Override
    public int compareTo(Player o) {
        return Long.compare(this.time.toMillis(),o.time.toMillis());
    }
}