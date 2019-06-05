package com.example.android.news;

public class News {
    private String sectionName;
    private String title;
    private String author;
    private String publicationDate;
    private String webURL;

    public News(String title, String sectionName, String author, String publicationDate, String webURL) {
        this.sectionName = sectionName;
        this.title = title;
        this.author = author;
        this.publicationDate = publicationDate;
        this.webURL = webURL;
    }

    public String getWebURL() {
        return webURL;
    }

    public String getPublicationDate() {
        return publicationDate;
    }

    public String getAuthor() {
        return author;
    }

    public String getTitle() {
        return title;
    }

    public String getSectionName() {
        return sectionName;
    }
}
