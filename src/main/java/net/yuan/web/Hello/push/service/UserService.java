package net.yuan.web.Hello.push.service;

import com.google.common.base.Strings;
import net.yuan.web.Hello.push.bean.Card.UserCard;
import net.yuan.web.Hello.push.bean.api.base.ResponseModel;
import net.yuan.web.Hello.push.bean.api.user.UpdateInfoModel;
import net.yuan.web.Hello.push.bean.db.User;
import net.yuan.web.Hello.push.factory.UserFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;

/**
 * 用户信息处理的Service
 */
//127.0.0.1/api/user

@Path("/user")
public class UserService extends BaseService {

    //用户信息修改接口
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public ResponseModel<UserCard> update(UpdateInfoModel model){
        if (!UpdateInfoModel.check(model)){
            return ResponseModel.buildParameterError();
        }
            User self = getself();
            //拿到个人信息
            //更新用户信息
            self = model.UpdateToUser(self);
            self = UserFactory.update(self);
            UserCard card = new UserCard(self, true);
            return ResponseModel.buildOk(card);
    }
}
