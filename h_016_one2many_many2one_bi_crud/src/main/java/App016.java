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

    private static SessionFactory SF = null;

    @BeforeClass
    public static void beforeClass() {
        StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure().build();
        SF = new MetadataSources(registry).buildMetadata().buildSessionFactory();
    }

    @AfterClass
    public static void afterClass() {
        SF.close();
    }

    @Test
    public void test01_SaveUser() {
        Group group = new Group("g1");
        User user = new User("u1", group);
        Session session = SF.getCurrentSession();
        session.beginTransaction();
//        session.save(group);
        session.save(user);//在User上设置cascade，这样可以在添加u的同时关联到g，CascadeType.ALL:表示增删改都会级联
        session.getTransaction().commit();
    }

    @Test
    public void test02_SaveGroup() {
        Group group = new Group("g1");
        group.getUsers().add(new User("u1", group));
        group.getUsers().add(new User("u2", group));
        Session session = SF.getCurrentSession();
        session.beginTransaction();
        session.save(group);//在Group上设置cascade，这样可以在添加g的同时关联到u，CascadeType.ALL:表示增删改都会级联
        session.getTransaction().commit();
    }

    @Test
    public void test03_GetUser() {
        Session session = SF.getCurrentSession();
        session.beginTransaction();
        //在取出User数据的同时也会取出Group的数据，可以取出one的数据，fetch默认值EAGER
        //如果不想取出one一方的数据，可以设置fetch为LAZY
        User user = session.get(User.class, 2);
        //设置fetch为LAZY的时候，如果用到Group的数据，会再发一条查询数据，取出Group
        System.out.println(user.getGroup().getName());
        session.getTransaction().commit();
    }

    @Test
    public void test04_GetGroup() {
        Session s = SF.getCurrentSession();
        s.beginTransaction();
        //在取出Group数据的同时不会取出User的数据，不会取出many一方的数据，fetch默认值LAZY
        //如果想取出many一方的数据,可以设置fetch为EAGER
        Group g = s.load(Group.class, 1);
        s.getTransaction().commit();
        for (User u : g.getUsers()) {
            System.out.println(u.getName());
        }
    }


    /**
     *
     * 1、 User的fetch为LAZY的时候，会先发一条sql语句取出User，再发出一条sql语句取出Group和User,
     *  原因是User的fetch为LAZY，会发出User的查询，Group的fetch也为EAGER，会在发出GROUP和User的查询
     *
     * 2、User的fetch为EAGER的时候，会先发出一条sql语句取出Group和User,再发一条sql语句取出User,
     *  原因是User的fetch为EAGER，会发出User和Group的级联，Group的fetch也为EAGER，会在发出User的查询，
     *
     *  因此，需要注意：不要在双向关联中都设置EAGER
     */
    @Test
    public void test05_LoadUser() {
        Session s = SF.getCurrentSession();
        User u = s.load(User.class, 1);
        System.out.println(u.getGroup().getName());
        s.getTransaction().commit();
    }


}
