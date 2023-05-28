package com.example.ujian_2021133002;

public class articles {
    private String title;
    private String link;
    private String authors;
    private String publication;
    private String year;
    private String user_id;
    private String key;
    public articles(String title, String link, String authors, String publication, String year, String user_id, String key){
        this.title = title;
        this.link = link;
        this.authors = authors;
        this.publication = publication;
        this.year = year;
        this.setUser_id(user_id);
        this.setKey(key);

    }
    public String getTitle() { return title; }
    public void setTitle(String value) { this.title = value; }

    public String getLink() { return link; }
    public void setLink(String value) { this.link = value; }

    public String getAuthors() { return authors; }
    public void setAuthors(String value) { this.authors = value; }

    public String getPublication() { return publication; }
    public void setPublication(String value) { this.publication = value; }

    public String getYear() { return year; }
    public void setYear(String value) { this.year = value; }

    public String getUser_id() {return user_id;}

    public void setUser_id(String user_id) {this.user_id = user_id;}

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
