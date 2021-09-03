package Chang.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class ApplicationContextConfig {
    @Bean//相当于applicationContext.xml <bean id="" class="">
    //@LoadBalanced//使用LoadBalanced注解赋予RestTemplate负载均衡的能力
    public RestTemplate getRestTemple(){
        return new RestTemplate();
    }
}
