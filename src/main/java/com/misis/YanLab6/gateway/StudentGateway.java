package com.misis.YanLab6.gateway;


import com.misis.YanLab6.dto.StudentDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;


@Component
public class StudentGateway {
    private final MessageChannel messageChannel;
    @Autowired
    public StudentGateway(
            @Qualifier("studentOutboundChannel") MessageChannel  messageChannel
    ) {
        this.messageChannel = messageChannel;
    }
    public void send(StudentDto event) {
        messageChannel.send(MessageBuilder.withPayload(event).build());  }
}
