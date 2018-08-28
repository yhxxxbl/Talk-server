package net.yuan.web.Hello.push;

import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
import net.yuan.web.Hello.push.provider.AuthRequestFilter;
import net.yuan.web.Hello.push.provider.GsonProvider;
import net.yuan.web.Hello.push.service.AccountService;
import org.glassfish.jersey.server.ResourceConfig;
import sun.misc.Resource;

import java.util.logging.Logger;

public class Application extends ResourceConfig {
    public Application(){
        //注册逻辑处理的包名
        packages(AccountService.class.getPackage().getName());
        //注册全局请求拦截器
        register(AuthRequestFilter.class);
        //注册Json解析器
        //register(JacksonJsonProvider.class);
        register(GsonProvider.class);
        //注册打印日志信息
        register(Logger.class);
    }
}
