package net.yuan.web.Hello.push.provider;

import com.google.common.base.Strings;
import net.yuan.web.Hello.push.bean.api.base.ResponseModel;
import net.yuan.web.Hello.push.bean.db.User;
import net.yuan.web.Hello.push.factory.UserFactory;
import org.glassfish.jersey.server.ContainerRequest;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.security.Principal;

/**
 * 用于所有的新旧接口的过滤和拦截
 */
@Provider
public class AuthRequestFilter implements ContainerRequestFilter {
    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        String relationPath= ((ContainerRequest)requestContext).getPath(false);
        //检查是否为登录注册接口，如果是 直接返回
        if (relationPath.startsWith("account/login")
                ||relationPath.startsWith("account/register")){
            return;
        }
        //从Headers中找到第一个token
        String token=requestContext.getHeaders().getFirst("token");
        if (!Strings.isNullOrEmpty(token)){
            final User self=UserFactory.findTokenByPhone(token);
            if (self!=null){
                //给当前请求添加一个SecurityContext
                requestContext.setSecurityContext(new SecurityContext() {
                    @Override
                    public Principal getUserPrincipal() {
                        //User实现Principal接口
                        return self;
                    }

                    @Override
                    public boolean isUserInRole(String role) {
                        //可以在这里写入用户的权限。role是权限名
                        return false;
                    }

                    @Override
                    public boolean isSecure() {
                        return false;
                    }

                    @Override
                    public String getAuthenticationScheme() {
                        return null;
                    }
                });
                //写入上下文后就返回
                return;
            }
        }
        //直接当回一个账户需要登录的Model
        ResponseModel model=ResponseModel.buildAccountError();
        //停止一个请求的继续下发
        Response response=Response.status(Response.Status.OK).entity(model).build();
        requestContext.abortWith(response);
    }
}
