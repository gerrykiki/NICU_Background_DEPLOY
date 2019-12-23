package deploy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import deploy.tcp.SocketTest;

import java.io.Console;
import java.io.IOException;
import java.net.CookieStore;
import java.net.HttpCookie;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.AbstractHttpClient;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

@SpringBootApplication
@EnableScheduling
public class DeployApplication {

	public static void main(String[] args) {
		// Thread th = new SocketTest();
		// th.start();
		// SpringApplication.run(DeployApplication.class, args);
		String loginUrl = "https://www.google.com";
        // 需登陆后访问的 Url
        HttpClient httpClient = new HttpClient();
 
        // 模拟登陆，按实际服务器端要求选用 Post 或 Get 请求方式
        PostMethod postMethod = new PostMethod(loginUrl);
 
        // 设置登陆时要求的信息，用户名和密码
        NameValuePair[] data = { new NameValuePair("loginName", "chzeze123"), new NameValuePair("loginPasswd", "**") };
        postMethod.setRequestBody(data);
        try {
            // 设置 HttpClient 接收 Cookie,用与浏览器一样的策略
            // httpClient.getParams().setCookiePolicy(CookiePolicy.BROWSER_COMPATIBILITY);
            int statusCode=httpClient.executeMethod(postMethod);
                             
            // 获得登陆后的 Cookie
			Cookie[] cookies = (Cookie[]) httpClient.getState().getCookies();
			System.out.println(cookies);
            StringBuffer tmpcookies = new StringBuffer();
            for (Cookie c : cookies) {
                tmpcookies.append(c.toString() + ";");
                System.out.println("cookies = "+c.toString());
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
	}

	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**").allowedOrigins("*");
			}
		};
	}
}
