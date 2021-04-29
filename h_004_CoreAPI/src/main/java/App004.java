import org.example.Student;
import org.example.Teacher;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.query.Query;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Date;

public class App004 {

    private static SessionFactory sf = null;

    @BeforeClass
    public static void beforeClass() {
        StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure().build();
        sf = new MetadataSources(registry).buildMetadata().buildSessionFactory();
    }

    @AfterClass
    public static void afterClass(){
        App004.sf.close();
    }

    /**
     * create data by default session
     */
    @Test
    public void test01() {
        Teacher t = new Teacher("t2", null, new Date());
        Session session = sf.openSession();
        session.beginTransaction();
        session.save(t);
        session.getTransaction().commit();
        session.close();
    }

    /**
     * create data by current session
     */
    @Test
    public void test02() {
        Teacher t = new Teacher("t2", null, new Date());
        Session s1 = sf.getCurrentSession();
        s1.beginTransaction();
        s1.save(t);

        Session s2 = sf.getCurrentSession();
        System.out.println("before commit, get same session, " + (s1 == s2));
        s1.getTransaction().commit();

        Session s3 = sf.getCurrentSession();
        System.out.println("after commit， get different session, " + (s1 == s3));
    }

    /**
     * the different of tree status
     * 1、exist ID?
     * 2、ID is in the database?
     * 3、exist in the memory (cache)?
     * transient：   exist object in memory, not in cache, no ID
     * persistent：  object exist in memory, in cache, has ID
     * detached：    object exist in memory, not in cache, has ID
     */
    @Test
    public void test03() {
        Teacher t = new Teacher("t3", null, new Date());

        Session session = sf.getCurrentSession();
        session.beginTransaction();

        session.save(t);

        System.out.println(t.getId());
        session.getTransaction().commit();
        System.out.println(t.getId());
    }

    @Test
    public void test04() {
        Teacher t = new Teacher("t4", null, new Date());
        System.out.println(t.getId());

        Session session = sf.getCurrentSession();
        session.beginTransaction();
        session.save(t);
        System.out.println(t.getId());
        session.getTransaction().commit();


        Session session2 = sf.getCurrentSession();
        session2.beginTransaction();
        session2.delete(t);
        session2.getTransaction().commit();
        System.out.println(t.getId());

        this.test05_load();
    }

    /**
     * load： The proxy object is returned, and the SQL statement is not issued until the contents of the object are actually used
     */
    @Test
    public void test05_load() {
        Session session = sf.getCurrentSession();
        session.beginTransaction();
        Teacher t = session.load(Teacher.class, 1);
        System.out.println(t);
//        System.out.println(t.getId());
        session.getTransaction().commit();
    }

    /**
     * get: Loads data from the database with no delay
     */
    @Test
    public void test06_get() {
        Session session = sf.getCurrentSession();
        session.beginTransaction();
        Teacher t = session.get(Teacher.class, 1);
        System.out.println(t);
        session.getTransaction().commit();
    }

    /**
     * when updating the 'detached' status object, it status will be transfer to 'persistent'
     */
    @Test
    public void test07_update() {
        Session session = sf.getCurrentSession();
        session.beginTransaction();
        Teacher t = session.get(Teacher.class, 3);
        session.getTransaction().commit();

        t.setName("t3");

        Session session2 = sf.getCurrentSession();
        session2.beginTransaction();
        session2.update(t);
        session2.getTransaction().commit();
    }

    /**
     * it will go wrong when updating the 'transient' status object
     * 如果更新自己设定id的transient对象可以（数据库有对应记录）
     */
    @Test
    public void test08_update() {
        Teacher t = new Teacher("t8", null, new Date());
        Session session = sf.getCurrentSession();
        session.beginTransaction();
        session.update(t);
        session.getTransaction().commit();
    }

    /**
     * object in 'persistent' state will be updated whenever different field values are set
     */
    @Test
    public void test09_update() {
        Session session = sf.getCurrentSession();
        session.beginTransaction();
        Teacher t = session.get(Teacher.class, 3);
        System.out.println(t);
        t.setName("t09");
        session.getTransaction().commit();
    }

    /**
     * it will be dynamic update modified field when you configure 'dynamic-update' in XML file
     */
    @Test
    public void test10_update() {
        Session session = sf.getCurrentSession();
        session.beginTransaction();
        Student student = session.get(Student.class, 1);
        student.setName("s10");
        session.getTransaction().commit();
    }

    /**
     * it will send SQL statement that updates all fields when you update field in different session
     */
    @Test
    public void test11_update() {
        Session session1 = sf.getCurrentSession();
        session1.beginTransaction();
        Student s = session1.get(Student.class, 1);
        s.setName("t11_1");
        session1.getTransaction().commit();

        s.setName("t11_2");
        Session session2 = sf.getCurrentSession();
        session2.beginTransaction();
        session2.update(s);
        session2.getTransaction().commit();
    }

    /**
     * it's recommended that you are use HQL statements to modify some fields
     */
    @Test
    public void test12_update() {
        Session session = sf.getCurrentSession();
        session.beginTransaction();
        Query query = session.createQuery("update Student s set s.name='t12' where s.id=1");
        query.executeUpdate();
        session.getTransaction().commit();
    }

    /**
     * ①.对于刚创建的一个对象，如果session中和数据库中都不存在该对象，那么该对象就是瞬时对象(Transient)
     * ②.瞬时对象调用save方法，或者离线对象调用update方法可以使该对象变成持久化对象，如果对象是持久化对象时，那么对该对象的任何修改，
     *    都会在提交事务时才会与之进行比较，如果不同，则发送一条update语句，否则就不会发送语句
     * ③.离线对象就是，数据库存在该对象，但是该对象又没有被session所托管
     */
    @Test
    public void test12_save_update() {
        Teacher t = new Teacher("t12", null, new Date());
        System.out.println(t.getId());

        Session session1 = sf.getCurrentSession();
        session1.beginTransaction();
        session1.saveOrUpdate(t);
        session1.getTransaction().commit();

        System.out.println(t.getId());
        t.setName("t12_1");

        Session session2 = sf.getCurrentSession();
        session2.beginTransaction();
        session2.saveOrUpdate(t);
        session2.getTransaction().commit();
    }

    /**
     * either get or load will query in the cache(level 1 cache),
     * and if it doesn't found, it will query in the database.
     * clear() used to force Level 1 cache clean up
     */
    @Test
    public void test_clear() {
        Session session = sf.getCurrentSession();
        session.beginTransaction();
        Teacher t = session.load(Teacher.class, 1);
        System.out.println(t);

        session.clear();
        System.out.println(t);

        Teacher t1 = session.load(Teacher.class, 1);
        System.out.println(t1);
        session.getTransaction().commit();
    }

    /**
     * flush()可以强制从内存到数据库的同步
     * FlushMode的模式,用于调节性能(不需要研究)
     */
    @Test
    public void testFlush() {
        Session session = sf.getCurrentSession();
        session.beginTransaction();
        Teacher t = session.load(Teacher.class, 1);
        t.setName("t1");
        System.out.println(t);
        session.flush();
        t.setName("t2");
        System.out.println(t);
        session.getTransaction().commit();
    }

    /**
     * 用程序来建表 第1个boolean类型是说是否覆盖原有的表，第2个boolean是说，是否新建表
     */
    @Test
    public void testSchemaExport() {
//        new SchemaExport(new Configuration().configure()).create(true, true);
    }


}



























