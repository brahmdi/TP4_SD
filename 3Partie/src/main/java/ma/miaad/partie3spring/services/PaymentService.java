package ma.miaad.partie3spring.services;

import ma.miaad.partie3spring.dtos.NewPaymentDTO;
import ma.miaad.partie3spring.entities.Payment;

import ma.miaad.partie3spring.entities.PaymentStatus;
import ma.miaad.partie3spring.entities.Student;
import ma.miaad.partie3spring.repository.PaymentRepository;
import ma.miaad.partie3spring.repository.StudentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
@Transactional
public class PaymentService {
    private StudentRepository studentRepository;
    private PaymentRepository paymentRepository;


    public PaymentService(StudentRepository studentRepository, PaymentRepository paymentRepository) {
        this.studentRepository = studentRepository;
        this.paymentRepository = paymentRepository;
    }
    public Payment savePayment(MultipartFile file, NewPaymentDTO NewPaymentDTO) throws IOException {
        Path folderPath = Paths.get(System.getProperty("user.home"),"MIAAD-data","payments");
        if (!Files.exists(folderPath)){
            Files.createDirectories(folderPath);
        }
        String fileName= UUID.randomUUID().toString();
        Path filePath = Paths.get(System.getProperty("user.home"),"MIAAD-data","payments",fileName+".pdf");
        Files.copy(file.getInputStream(),filePath);
        Student student=studentRepository.findByCode(NewPaymentDTO.getStudentCode());
        Payment payment=Payment.builder()
                .date(NewPaymentDTO.getDate()).type(NewPaymentDTO.getType()).student(student)
                .amount(NewPaymentDTO.getAmount())
                .file(filePath.toUri().toString())
                .status(PaymentStatus.CREATED)
                .build();
        return paymentRepository.save(payment);
    }
    public Payment updatePaymentStatus( PaymentStatus status,  Long id){
        Payment payment=paymentRepository.findById(id).get();
        payment.setStatus(status);
        return paymentRepository.save(payment);
    }
    public byte[] getPaymentFile ( Long paymentId) throws IOException {
        Payment payment=paymentRepository.findById(paymentId).get();
        return Files.readAllBytes(Path.of(URI.create(payment.getFile())));
    }
}
