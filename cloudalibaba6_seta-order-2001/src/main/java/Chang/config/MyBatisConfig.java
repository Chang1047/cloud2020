package Chang.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;


@Configuration
@MapperScan({"Chang.dao"})
public class MyBatisConfig {
}
