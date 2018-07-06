package net.yuan.web.Hello.push.factory;

import com.google.common.base.Strings;
import net.yuan.web.Hello.push.bean.db.User;
import net.yuan.web.Hello.push.utils.Hib;
import net.yuan.web.Hello.push.utils.TextUtil;
import org.hibernate.Session;

import java.util.List;
import java.util.UUID;

public class UserFactory {
    //通过token找到User
    public static User findTokenByPhone(String token){
        return Hib.query(session -> (User) session
                .createQuery("from User where token=:token")
                .setParameter("token",token)
                .uniqueResult());
    }
    //通过pHone找到User
    public static User findUserByPhone(String phone){
        return Hib.query(session -> (User) session
                .createQuery("from User where phone=:inphone")
                .setParameter("inphone",phone)
                .uniqueResult());
    }
//通过name找到User
    public static User findUserByName(String name){
        return Hib.query(session -> (User) session
                .createQuery("from User where name=:name")
                .setParameter("name",name)
                .uniqueResult());
    }
    //给当前的账户绑定pushId
    public static User bindPushId(User user, String pushId) {
        if (Strings.isNullOrEmpty(pushId))
            return null;

        // 第一步，查询是否有其他账户绑定了这个设备
        // 取消绑定，避免推送混乱
        // 查询的列表不能包括自己
        Hib.query(session -> {
            @SuppressWarnings("unchecked")
            List<User> userList = (List<User>) session
                    .createQuery("from User where lower(pushId)=:pushId and id!=:userId")
                    .setParameter("pushId", pushId.toLowerCase())
                    .setParameter("userId", user.getId())
                    .list();

            for (User u : userList) {
                // 更新为null
                u.setPushId(null);
                session.saveOrUpdate(u);
            }
        });

        if (pushId.equalsIgnoreCase(user.getPushId())) {
            // 如果当前需要绑定的设备Id，之前已经绑定过了
            // 那么不需要额外绑定
            return user;
        } else {
            // 如果当前账户之前的设备Id，和需要绑定的不同
            // 那么需要单点登录，让之前的设备退出账户，
            // 给之前的设备推送一条退出消息
            if (Strings.isNullOrEmpty(user.getPushId())) {
                // TODO 推送一个退出消息
            }

            // 更新新的设备Id
            return  Hib.query(session -> {
                session.saveOrUpdate(user);
                return user;
            });
        }
    }
//使用账户密码进行登录
    public static User login(String account,String password){
       final String accountStr=account.trim();
        //密码存储时采用了加密操作，当寻找时也要进行同样的操作，才能匹配
      final   String encodePassword=encodePassword(password);
        User user=Hib.query(session -> (User) session
                  .createQuery("from User where phone=:phone and password=:password")
                  .setParameter("phone",accountStr)
                  .setParameter("password",encodePassword)
                  .uniqueResult());
        if (user!=null){
            //对User进行登陆然后返回
            user=login(user);
        }
        return user;
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

        User user = createUser(account,password,name);
        user.setName(name);
        if (user!=null){
            user=login(user);
        }
        return user;
    }

    /**
     * 注册时的新建账户方法
     * @param account  手机
     * @param password  加密后的密码
     * @param name  用户名
     * @return  返回一个用户
     */
    private static User createUser(String account,String password,String name){
        User user = new User();
        user.setName(name);
        user.setPassword(password);
        user.setPhone(account);

        //数据库存储
        return Hib.query(session -> {
            session.save(user);
            return user;
        });
    }

    /**
     * 登陆操作  本质上是对Token进行操作
     * @param user
     * @return
     */
    private static User login(User user){
        //使用一个随机的UUIOD值充当token
        String newToken= UUID.randomUUID().toString();
        //进行一次base64格式化
        newToken=TextUtil.encodeBase64(newToken);
        user.setToken(newToken);
        return  Hib.query(session -> {
            session.saveOrUpdate(user);
            return user;
        });
    }

    /**
     * 对密码进行加密操作
     * @param password
     * @return
     */
    private static String  encodePassword(String password){
        password=password.trim();//去空格
        password=TextUtil.getMD5(password);//进行MD5非对称加密，加盐更安全，盐也要存储
        //在进行一次对称的Base64加密，可以加盐
        return TextUtil.encodeBase64(password);
    }


}