package Chang.service.Impl;

import Chang.service.IMessageProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;

import javax.annotation.Resource;
import java.util.UUID;

@EnableBinding(Source.class) //定义消息推送的管道，表示当前这个类是source,负责生产消息,并且发送给channel
@Slf4j
public class IMessageProviderImpl implements IMessageProvider {
    @Resource //channel,我们将消息发送个channel
    private MessageChannel output;  //消息发送管道

    @Override
    public String send() {
        String serial= UUID.randomUUID().toString();
        output.send(MessageBuilder.withPayload(serial).build());//build方法会构建一个Message类
        log.info("****serial:"+serial);
        return null;
    }
}
