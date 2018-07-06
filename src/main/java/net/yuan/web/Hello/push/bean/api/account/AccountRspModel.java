package net.yuan.web.Hello.push.bean.api.account;

import com.google.gson.annotations.Expose;
import net.yuan.web.Hello.push.bean.Card.UserCard;
import net.yuan.web.Hello.push.bean.db.User;

/**
 * 账户返回的model
 */
public class AccountRspModel {
    @Expose
    private UserCard userCard;//用户基本信息
    @Expose
    private String account;//当前登录的账号
    //当前的登录成功后获取的token。
    // 可以通过token获取用户的所有信息
    @Expose
    private String token;
    @Expose
    private boolean isBind;//是否绑定

    public AccountRspModel(User user) {
      this(user,false);
    }


    public AccountRspModel(User user, boolean isbind){
        this.userCard=new UserCard(user);
        this.account=user.getPhone();
        this.token=user.getToken();
        this.isBind=isbind;
    }

    public UserCard getUserCard() {
        return userCard;
    }

    public void setUserCard(UserCard userCard) {
        this.userCard = userCard;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public boolean isBind() {
        return isBind;
    }

    public void setBind(boolean bind) {
        isBind = bind;
    }
}
