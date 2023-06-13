package com.misis.YanLab6.activator;

import com.misis.YanLab6.dto.StudentDto;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;


@Component
public class StudentActivator {
    @ServiceActivator(inputChannel = "studentInboundChannel")


    public void activate(Message<StudentDto> event) {
        System.out.println(event.getPayload().getFullName());  System.out.println(event.getPayload().getBirthday());  }
}
