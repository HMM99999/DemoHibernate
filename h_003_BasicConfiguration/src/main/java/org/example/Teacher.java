package org.example;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "teacher")
public class Teacher {
    private int id;
    private String name;
    private String title;
    private Date date;
    private TeacherType type;

    public Teacher() {
    }

    public Teacher(String name, String title, Date date, TeacherType type) {
        this.name = name;
        this.title = title;
        this.date = date;
        this.type = type;
    }

    @Id
    @GeneratedValue
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    //alias the field '_name'
    @Column(name = "_name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    //transient, the field does not write to the database.
    @Transient
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    //default timestamp
    @Temporal(value = TemporalType.TIMESTAMP)
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    //ENUMERATION.STRING:character string; ENUMERATION.ORDINAL:the index of an enumerated type
    @Enumerated(EnumType.STRING)
    public TeacherType getType() {
        return type;
    }

    public void setType(TeacherType type) {
        this.type = type;
    }
}
