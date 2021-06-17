import org.example.Group;
import org.example.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class App016 {

    private static SessionFactory sf = null;

    @BeforeClass
    public static void beforeClass() {
        StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure().build();
        sf = new MetadataSources(registry).buildMetadata().buildSessionFactory();
    }

    @AfterClass
    public static void afterClass() {
        sf.close();
    }

    @Test
    public void test01_SaveUser() {
        Group group = new Group("g1");
        User user = new User("u1", group);
        Session session = sf.getCurrentSession();
        session.beginTransaction();
        session.save(user);
        session.save(group);
        session.getTransaction().commit();
    }

}
