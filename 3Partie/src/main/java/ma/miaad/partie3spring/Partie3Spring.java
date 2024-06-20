package ma.miaad.partie3spring;

import ma.miaad.partie3spring.entities.Payment;
import ma.miaad.partie3spring.entities.PaymentStatus;
import ma.miaad.partie3spring.entities.PaymentType;
import ma.miaad.partie3spring.entities.Student;
import ma.miaad.partie3spring.repository.PaymentRepository;
import ma.miaad.partie3spring.repository.StudentRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;


import java.time.LocalDate;
import java.util.Random;
import java.util.UUID;
@SpringBootApplication
public class Partie3Spring {

    public static void main(String[] args) {
        SpringApplication.run(Partie3Spring.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner(StudentRepository studentRepository, PaymentRepository paymentRepository){
        return args -> {
            studentRepository.save(Student.builder().id(UUID.randomUUID().toString())
                    .firstName("brahim").code("11111").programId("MIAAD")
                    .build());
            studentRepository.save(Student.builder().id(UUID.randomUUID().toString())
                    .firstName("elhamdi").code("11113").programId("MIAAD")
                    .build());
            studentRepository.save(Student.builder().id(UUID.randomUUID().toString())
                    .firstName("amine").code("112255").programId("TEST")
                    .build());
            studentRepository.save(Student.builder().id(UUID.randomUUID().toString())
                    .firstName("youssef").code("112266").programId("MMSS")
                    .build());
            PaymentType[] paymentTypes=PaymentType.values();
            Random random =new Random();
            studentRepository.findAll().forEach(st->{
                for (int i = 0; i <10 ; i++) {
                    int index =random.nextInt(paymentTypes.length);
                    Payment payment=Payment.builder()
                            .amount(1000+(int)(Math.random()*20000))
                            .type(paymentTypes[index])
                            .status(PaymentStatus.CREATED)
                            .date(LocalDate.now())
                            .student(st)
                            .build();
                    paymentRepository.save(payment);
                }
            });
        };
    }

}