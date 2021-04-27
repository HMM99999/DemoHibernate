package org.example;

import javax.persistence.*;

@Entity
@Table(name = "teacher")
public class Teacher {

    private int id;
    private String name;
    private String title;

    public Teacher() {
    }

    public Teacher(String name, String title) {
        this.name = name;
        this.title = title;
    }

    @Id
    @GeneratedValue
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Transient
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
