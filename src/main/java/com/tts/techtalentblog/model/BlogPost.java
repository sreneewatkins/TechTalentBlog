package com.tts.techtalentblog.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

/*
We use this annotation to designate a plain old Java object (POJO) class
as an entity so that we can use it with JPA services.
Annotations to the class that will tie the class to the JPA and database.
 */
@Entity
public class BlogPost {

    /*
    JPA will recognize it as the object’s ID and primary key.
    Allows the underlying database to set the value for the field.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String title;
    private String author;
    private String blogEntry;

    //constructor
    public BlogPost() {
        //no-argument constructor for our class, which is necessary for the JPA
    }
    public BlogPost(String title, String author, String blogEntry) {
        this.title = title;
        this.author = author;
        this.blogEntry = blogEntry;
    }

    //getters and setters
    public Long getId() { return id; }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getBlogEntry() {
        return blogEntry;
    }

    public void setBlogEntry(String blogEntry) {
        this.blogEntry = blogEntry;
    }

    @Override
    public String toString() {
        return "BlogPost{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", blogEntry='" + blogEntry + '\'' +
                '}';
    }
    //additionally, you should override hashcode and equals as well


}//end BlogPost class
