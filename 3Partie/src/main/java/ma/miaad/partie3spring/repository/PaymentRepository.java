package ma.miaad.partie3spring.repository;

import ma.miaad.partie3spring.entities.Payment;
import ma.miaad.partie3spring.entities.PaymentStatus;
import ma.miaad.partie3spring.entities.PaymentType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PaymentRepository extends JpaRepository<Payment,Long> {
    List<Payment> findByStudentCode(String code);
    List <Payment> findByStatus(PaymentStatus status);
    List<Payment> findByType(PaymentType type);

}
