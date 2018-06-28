package net.yuan.web.Hello.push.factory;

import net.yuan.web.Hello.push.bean.db.User;
import net.yuan.web.Hello.push.utils.Hib;
import net.yuan.web.Hello.push.utils.TextUtil;
import org.hibernate.Session;

public class UserFactory {

    public static User findUserByPhone(String phone){
        return Hib.query(session -> (User) session
                .createQuery("from User where phone=:inphone")
                .setParameter("inphone",phone)
                .uniqueResult());
    }

    public static User findUserByName(String name){
        return Hib.query(session -> (User) session
                .createQuery("from User where name=:name")
                .setParameter("name",name)
                .uniqueResult());
    }
    /**
     * 用户注册
     * 注册的操作需要写入数据库，并返回数据库中的User信息
     *
     * @param account
     * @param password
     * @param name
     * @return
     */
    public static User register(String account, String password, String name) {
        //去除账户中的首位空格
        account=account.trim();
        //对密码进行加密
        password=encodePassword(password);//处理密码
        User user = new User();
        user.setName(name);
        user.setPassword(password);
        user.setPhone(account);

        //进行数据库操作
        //首先创建一个会话
        Session session = Hib.session();
        //开启一个事务
        session.beginTransaction();
        //保存用户信息
        // return user;
        try {
            session.save(user);
            //结束：得到事务并提交
            session.getTransaction().commit();
            return user;
        } catch (Exception e) {
            //失败情况回滚事务
            session.getTransaction().rollback();
            return null;
        }
    }
    private static String  encodePassword(String password){
        password=password.trim();//去空格
        password=TextUtil.getMD5(password);//进行MD5非对称加密，加盐更安全，盐也要存储
        //在进行一次对称的Base64加密，可以加盐
        return TextUtil.encodeBase64(password);
    }
}
