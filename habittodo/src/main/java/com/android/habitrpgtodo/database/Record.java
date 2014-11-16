package com.android.habitrpgtodo.database;

/**
 * Created by karthik on 11/16/14.
 */
public class Record {
    public String id, name;
    public Boolean completed;

    //TODO Create Globals
    public Record(String id, String name, Boolean completed) {
        this.id = id;
        this.name = name;
        this.completed = completed;
    }

    public String toString() {
        return this.id + " " + this.name + " " + this.completed;
    }
}