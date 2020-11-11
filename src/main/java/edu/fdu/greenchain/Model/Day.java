package edu.fdu.greenchain.Model;

public class Day {

    private String date;
    private int id;

    public Day() {
    }

    public Day(String date) {
        this.date = date;
    }

    public Day(int id) {
        this.id = id;
    }

    public Day(int id, String date) {
        this.id = id;
        this.date = date;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
