package net.yuan.web.Hello.push.service;

import com.google.common.base.Strings;
import net.yuan.web.Hello.push.bean.Card.UserCard;
import net.yuan.web.Hello.push.bean.api.account.AccountRspModel;
import net.yuan.web.Hello.push.bean.api.account.LoginModel;
import net.yuan.web.Hello.push.bean.api.account.RegisterModel;
import net.yuan.web.Hello.push.bean.api.base.ResponseModel;
import net.yuan.web.Hello.push.bean.db.User;
import net.yuan.web.Hello.push.factory.UserFactory;

import javax.smartcardio.Card;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/account")
public class AccountService {
    @POST
    @Path("/login")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public ResponseModel<AccountRspModel> login(LoginModel model) {
        if (!LoginModel.check(model)){
            return ResponseModel.buildParameterError();
        }
        User user=UserFactory.login(model.getAccount(),model.getPassword());
        if (user!=null){
            //如果有携带PushId
            if (Strings.isNullOrEmpty(model.getPushId())){
                return bind(user,model.getPushId());
            }
            AccountRspModel rspModel=new AccountRspModel(user);
            return ResponseModel.buildOk(rspModel);
        }else {
            //登录失败
            return ResponseModel.buildLoginError();
        }

    }
    @POST
    @Path("/register")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public ResponseModel<AccountRspModel> register(RegisterModel model) {
        if (!RegisterModel.check(model)){
            return ResponseModel.buildParameterError();
        }

        User user=UserFactory.findUserByPhone(model.getAccount().trim());
        if(user!=null){
            //ResponseModel中写好的静态方法
            return ResponseModel.buildHaveAccountError();
        }

        user=UserFactory.findUserByName(model.getName().trim());
        if(user!=null){
            return ResponseModel.buildHaveNameError();
        }

        //开始注册
        user = UserFactory.register(model.getAccount(), model.getPassword(), model.getPassword());
        if (user != null) {

            //如果有携带PushId
            if (Strings.isNullOrEmpty(model.getPushId())){
                return bind(user,model.getPushId());
            }
            //返回当前账户
            AccountRspModel rspModel=new AccountRspModel(user);
            return ResponseModel.buildOk(rspModel);
        }else {
            //返回写好的静态方法注册错误
            return ResponseModel.buildRegisterError();
        }
    }

        @POST
        @Path("/bind/{pushId}")
        @Consumes(MediaType.APPLICATION_JSON)
        @Produces(MediaType.APPLICATION_JSON)
        //从请求头中获取token字段
        //pushid从url地址中获取
        public ResponseModel<AccountRspModel> bing(@HeaderParam("token") String token,@PathParam("pushId") String pushId) {
            if (Strings.isNullOrEmpty(pushId)
                    ||Strings.isNullOrEmpty(token)){
                //返回参数异常
                return ResponseModel.buildParameterError();
            }
            //拿到自己的个人信息
            User user=UserFactory.findTokenByPhone(token);
            if (user!=null){
                return bind(user,pushId);
            }else {
                //登录失败
                return ResponseModel.buildAccountError();
            }

        }

    /**
     * 绑定的操作
     * @param self  自己
     * @param pushId
     * @return  user
     */

        private ResponseModel<AccountRspModel> bind(User self,String pushId){

               User user=UserFactory.bindPushId(self,pushId);
                if (user==null){
                    return ResponseModel.buildServiceError();
                }
            AccountRspModel rspModel=new AccountRspModel(user,true);
            return ResponseModel.buildOk(rspModel);
        }
}