package net.yuan.web.Hello.push.service;

import net.yuan.web.Hello.push.bean.Card.UserCard;
import net.yuan.web.Hello.push.bean.api.account.RegisterModel;
import net.yuan.web.Hello.push.bean.api.base.ResponseModel;
import net.yuan.web.Hello.push.bean.db.User;
import net.yuan.web.Hello.push.factory.UserFactory;

import javax.smartcardio.Card;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/account")
public class AccountService {
    @POST
    @Path("/register")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public ResponseModel<> register(RegisterModel model) {

        User user=UserFactory.findUserByPhone(model.getAccount().trim());
        if(user!=null){
            UserCard card=new  UserCard();
            card.setName("账户已经存在");
            return card;
        }

        user=UserFactory.findUserByName(model.getName().trim());
        if(user!=null){
            UserCard card=new  UserCard();
            card.setName("name已经存在");
            return card;
        }

        user = UserFactory.register(model.getAccount(), model.getPassword(), model.getPassword());
        if (user != null) {
            UserCard card = new UserCard();
            card.setName(user.getName());
            card.setSex(user.getSex());
            card.setPhone(user.getPhone());
            card.setFollow(true);
            card.setModiftAt(user.getUpdateAt());
            return card;
        }
        return null;
    }
}