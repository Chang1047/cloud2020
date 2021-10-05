package Chang.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j  //排队等待时查看控制台信息
public class FlowLimitController {

    @GetMapping("/testA")
    public  String testA(){
       /* try {   //用于线程数的直接模式
                    TimeUnit.SECONDS.sleep(800);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }*/
        return  "-----testA!";
    }
    @GetMapping("/testB")
    public  String testB(){
        //log.info(Thread.currentThread().getName()+"\t --testB");
        return  "-----testB!";
    }

    @GetMapping("/testD")
    public  String  testD(){
        /*try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
        log.info("test 测试RT！！！");*/


        log.info("test 测试异常比例！！！");
        int age=10/0;
        return "----testD!";
    }

    @GetMapping("/testE")
    public String testE(){
        log.info("test 测试异常数！！！");
        int age=10/0;
        return  "----testE!";
    }

    @GetMapping ("/testHotkey")
    @SentinelResource(value = "testHotkey",blockHandler = "deal_testHotkey")
    public String testHotkey(@RequestParam(value ="p1",required = false) String p1,
                             @RequestParam(value ="p2",required = false) String p2){

        return "-testHotkey";
    }
    public String deal_testHotkey(String p1, String p2, BlockException exception){
        return "----deal_testHotkey,┭┮﹏┭┮";//sentinel 系统默认的提示: Blocked by sentinel (flow limiting)

    }
}
