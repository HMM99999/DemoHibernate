package org.example;

import javax.persistence.*;

@Entity
@Table(name = "t_user")
public class User {
    private int id;
    private String name;
    private Group group;

    public User(String name, Group group) {
        this.name = name;
        this.group = group;
    }

    //在User上设置cascade，这样可以在添加u的同时关联到g，CascadeType.ALL:表示增删改都会级联
    @ManyToOne(cascade = {CascadeType.ALL}, fetch = FetchType.EAGER)
    @JoinColumn(name = "g_id")
    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
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
}
