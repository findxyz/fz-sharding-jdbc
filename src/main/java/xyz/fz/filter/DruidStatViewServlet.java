package xyz.fz.filter;

import com.alibaba.druid.support.http.StatViewServlet;

import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;

@WebServlet(urlPatterns = "/druid/*",
        initParams = {
                @WebInitParam(name = "allow", value = "192.168.1.21,127.0.0.1"),
                @WebInitParam(name = "deny", value = ""),
                @WebInitParam(name = "loginUsername", value = "admin"),
                @WebInitParam(name = "loginPassword", value = "admin"),
                @WebInitParam(name = "resetEnable", value = "false")
        }
)
public class DruidStatViewServlet extends StatViewServlet {

}
