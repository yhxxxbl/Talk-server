package net.yuan.web.Hello.push.service;

import net.yuan.web.Hello.push.bean.db.User;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.SecurityContext;

public class BaseService {
    //添加一个上下文注解，这个注解会给securityContext赋值
    //具体的值为拦截器中返回的上下文
    @Context
    protected SecurityContext securityContext;

    /**
     * 从上下文中直接获取信息
     * @return
     */
    protected User getself(){
        return (User) securityContext.getUserPrincipal();

    }
}
