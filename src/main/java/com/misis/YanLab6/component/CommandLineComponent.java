package com.misis.YanLab6.component;
import com.misis.YanLab6.dto.StudentDto;
import com.misis.YanLab6.gateway.StudentGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;


@Component
public class CommandLineComponent implements CommandLineRunner {  private final StudentGateway studentGateway;
    @Autowired
    public CommandLineComponent(StudentGateway studentGateway) {  this.studentGateway = studentGateway;
    }
    @Override
    public void run(String... args) throws Exception {
        StudentDto dto = new StudentDto();
        dto.setFullName("Yanushka A.V.");
        dto.setBirthday("21.06.2002");
        studentGateway.send(dto);
    }
}


