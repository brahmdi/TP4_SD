package ma.miaad.partie3spring.dtos;

import ma.miaad.partie3spring.entities.PaymentType;

import lombok.*;

import java.time.LocalDate;

@NoArgsConstructor @AllArgsConstructor @Getter @Setter @ToString @Builder
public class NewPaymentDTO {
    private LocalDate date;
    private double amount;
    private PaymentType type;
    private String studentCode;
}
