import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.boot.spi.MetadataImplementor;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.tool.hbm2ddl.SchemaExport;
import org.hibernate.tool.schema.TargetType;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.EnumSet;

public class App005 {

//    private static SessionFactory sf = null;
//
//    @BeforeClass
//    public static void beforeClass() {
//        StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure().build();
//        sf = new MetadataSources(registry).buildMetadata().buildSessionFactory();
//    }
//
//    @AfterClass
//    public static void afterClass(){
//        App.sf.close();
//    }


    @Test
    public void test01() {
        ServiceRegistry registry = new StandardServiceRegistryBuilder().configure().build();
        Metadata metadata = new MetadataSources(registry).buildMetadata();
        SchemaExport export = new SchemaExport();
        export.create(EnumSet.of(TargetType.DATABASE), metadata);
    }


}
