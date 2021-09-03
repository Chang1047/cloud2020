package Chang.service;

import cn.hutool.core.util.IdUtil;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class PaymentService {
    //正常访问，ok
    public String paymentInfo_OK(Integer id){
        return "线程池:"+Thread.currentThread().getName()+"paymentInfo_OK,id:"+id+"\t"+"O(∩_∩)O哈哈~";
    }

    //表示当需要发生服务降级时,调用此方法,相当于降级到此方法(paymentInfo_TimeOutHandler)
    @HystrixCommand(fallbackMethod = "paymentInfo_TimeOutHandler", commandProperties ={
            @HystrixProperty(name="execution.isolation.thread.timeoutInMilliseconds", value="3000")
    })//这里表示,调用此方法的线程,最多只等待?秒中,如果?秒钟没有响应,触发降级,调用指定方法
    public String paymentInfo_TimeOut(Integer id){//这个方法是测试熔断的方法,这里会超时?秒,测试是否熔断
        int timeNumber=5;
        //int age=10/0;
       try {
                   TimeUnit.SECONDS.sleep(timeNumber);
               } catch (InterruptedException e) {
                   e.printStackTrace();
               }
        return "线程池:"+Thread.currentThread().getName()+"paymentInfo_TimeOut,id:"+id+"\t"+"O(∩_∩)O哈哈~"+"耗时(秒):"+timeNumber;
    }

    public String paymentInfo_TimeOutHandler (Integer id) {//表示当需要发生服务降级时,调用此方法,相当于降级到此方法
        return "/(ToT)/调用支付接口超时或异常: \t" + "\t当前线程池名字" + Thread.currentThread().getName();
    }


//熔断超时线程池满会启动服务降级
    /**
     * 服务熔断 超时、异常、都会触发熔断
     * 1、默认是最近10秒内收到不小于10个请求，<br/>
     * 2、并且有60%是失败的<br/>
     * 3、就开启断路器<br/>
     * 4、开启后所有请求不再转发，降级逻辑自动切换为主逻辑，减小调用方的响应时间<br/>
     * 5、经过一段时间（时间窗口期，默认是5秒），断路器变为半开状态，会让其中一个请求进行转发。<br/>
     * &nbsp;&nbsp;5.1、如果成功，断路器会关闭，<br/>
     * &nbsp;&nbsp;5.2、若失败，继续开启。重复4和5<br/>
     */
    @HystrixCommand(fallbackMethod = "paymentCircuitBreakerFallback", commandProperties = {
            @HystrixProperty(name = "circuitBreaker.enabled", value = "true"),/* 是否开启断路器*/
            @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "10"),// 请求次数
            @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "10000"), // 时间窗口期
            @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "60"),// 失败率达到多少后跳闸
//            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "5000")// 超时处理
    })//时间窗口期是指保险丝开启后经过的一段时间再转换为半开状态(open后的时间)
    public String paymentCircuitBreaker(Integer id) {
        if (id < 0) {
            throw new RuntimeException("******id 不能负数");
        }
        //测试异常
//        int age = 10 / 0;
//        int second = 3;
//        try {
//            TimeUnit.SECONDS.sleep(second);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }


        String serialNumber = IdUtil.simpleUUID();//等价于UUID.randomUUID().toString();
        return Thread.currentThread().getName() + "\t" + "调用成功，流水号: " + serialNumber;
    }


    /**
     * paymentCircuitBreaker 方法的 fallback，<br/>
     * 当断路器开启时，主逻辑熔断降级，该 fallback 方法就会替换原 paymentCircuitBreaker 方法，处理请求
     */
    public String paymentCircuitBreakerFallback(Integer id) {
        return Thread.currentThread().getName() + "\t" + "id 不能负数或超时或自身错误，请稍后再试，/(ㄒoㄒ)/~~   id: " + id;
    }

}
