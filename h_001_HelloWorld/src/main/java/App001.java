import org.example.Student;
import org.example.Teacher;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class App001 {
    private static SessionFactory SESSION_FACTORY = null;

    @BeforeClass
    public void beforeTest() {
        StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure().build();
        SESSION_FACTORY = new MetadataSources(registry).buildMetadata().buildSessionFactory();
    }

    @AfterClass
    public static void afterClass() {
        App001.SESSION_FACTORY.close();
    }

    @Test
    public void test01() {
        Session session = App001.SESSION_FACTORY.openSession();
        session.beginTransaction();
        Student s = new Student("student1", 12);
        session.save(s);
        session.getTransaction().commit();
        session.close();
    }

    @Test
    public void test02() {
        Session session = App001.SESSION_FACTORY.openSession();
        session.beginTransaction();

        Teacher teacher = new Teacher("name", "title");
        session.save(teacher);

        session.getTransaction().commit();
        session.close();
    }




}
