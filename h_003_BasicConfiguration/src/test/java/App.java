import org.example.Student;
import org.example.Teacher;
import org.example.TeacherType;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Date;

public class App {

    private static SessionFactory SESSION_FACTORY = null;

    @BeforeClass
    public static void beforeClass() {
        StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure().build();
        SESSION_FACTORY = new MetadataSources(registry).buildMetadata().buildSessionFactory();
    }

    @AfterClass
    public static void afterClass(){
        App.SESSION_FACTORY.close();
    }

    @Test
    public void test01() {
        Session session = App.SESSION_FACTORY.openSession();
        session.beginTransaction();
        Student s = new Student("student1", 12);
        session.save(s);
        session.getTransaction().commit();
        session.close();
    }

    @Test
    public void test02() {
        Session session = App.SESSION_FACTORY.openSession();
        session.beginTransaction();

        Teacher teacher = new Teacher("t1", "tt", new Date(), TeacherType.A);
        session.save(teacher);

        session.getTransaction().commit();
        session.close();
    }
}
