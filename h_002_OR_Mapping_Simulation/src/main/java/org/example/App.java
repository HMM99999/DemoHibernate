package org.example;

public class App {
    public static void main(String[] args) throws Exception {
        Student s = new Student();
        s.setName("zhangsan");
        s.setAge(12);
        Session session = new Session();
        session.save(s);
    }
}
