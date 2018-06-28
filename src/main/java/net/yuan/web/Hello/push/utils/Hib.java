package net.yuan.web.Hello.push.utils;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;


public class Hib {
    // 全局SessionFactory
    private static SessionFactory sessionFactory;

    static {
        // 静态初始化sessionFactory
        init();
    }

    private static void init() {
        // 从hibernate.cfg.xml文件初始化
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure() // configures settings from hibernate.cfg.xml
                .build();
        try {
            // build 一个sessionFactory
            sessionFactory = new MetadataSources(registry)
                    .buildMetadata()
                    .buildSessionFactory();
        } catch (Exception e) {
            e.printStackTrace();
            // 错误则打印输出，并销毁
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }

    /**
     * 获取全局的SessionFactory
     *
     * @return SessionFactory
     */
    public static SessionFactory sessionFactory() {
        return sessionFactory;
    }

    /**
     * 从SessionFactory中得到一个Session会话
     *
     * @return Session
     */
    public static Session session() {
        return sessionFactory.getCurrentSession();
    }

    /**
     * 关闭sessionFactory
     */
    public static void closeFactory() {
        if (sessionFactory != null) {
            sessionFactory.close();
        }
    }

    public interface QueryOnly{
        void query(Session session);
    }

    public static void query(QueryOnly query){
        //重开一个session；
        Session session=sessionFactory().openSession();
        //将transaction保存起来
        final Transaction transaction=session.beginTransaction();

        try {
            //调用传递进来的接口
            //并调用接口中的方法将Session传递出去
            query.query(session);
            transaction.commit();
        }catch (Exception e) {
            e.printStackTrace();
            try {
                //如果失败进行回滚操作
                transaction.rollback();
            } catch (RuntimeException e1) {
                e1.printStackTrace();
            }
        } finally {
            //无论成功与否都需要关闭sssion
            session.close();
        }
    }

    //用于用户实际操作的一个接口，具有返回值。
    public interface Query<T>{
        T query(Session session);
    }
    //简化session事务操作的工具方法、具有返回值
    public static<T> T query(Query<T> query){
        //重开一个session；
        Session session=sessionFactory().openSession();
        //将transaction保存起来
        final Transaction transaction=session.beginTransaction();

        T t=null;
        try {
            //调用传递进来的接口
            //并调用接口中的方法将Session传递出去
            t=query.query(session);//给T赋值
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            try {
                //如果失败进行回滚操作
                transaction.rollback();
            } catch (RuntimeException e1) {
                e1.printStackTrace();
            }
        } finally {
            //无论成功与否都需要关闭sssion
            session.close();
        }
        return t;
    }
}
