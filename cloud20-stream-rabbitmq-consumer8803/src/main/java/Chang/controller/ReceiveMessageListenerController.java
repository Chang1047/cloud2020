package Chang.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

@Component
@EnableBinding(Sink.class)//启用绑定,就是表示当前类是sink,负责介绍channel发送过来的数据进行消费
public class ReceiveMessageListenerController {

    @Value("${server.port}")
    private  String serverPort;

    @StreamListener(Sink.INPUT)//这里表示监听sink的input,而input我们在配置文件中配置了,绑定在一个指定Exchange上获取数据
    public void input(Message<String> message){
        System.out.println("消费者1号，---接收到信息："+message.getPayload()+"\t port:"+serverPort);

    }
}
