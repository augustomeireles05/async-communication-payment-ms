package br.com.foodcompany.payments.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "payments")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false, length = 20, unique = true)
    private Long id;

    @NotNull
    @Positive
    @Column(name = "VALUE", nullable = false)
    private BigDecimal value;

    @NotBlank
    @Size(max = 100)
    @Column(name = "NAME", length = 100)
    private String name;

    @NotBlank
    @Size(max = 19)
    @Column(name = "NUMBER", length = 19)
    private String number;

    @NotBlank
    @Size(max = 7)
    @Column(name = "EXPIRATION", nullable = false, length = 7)
    private String expiration;

    @NotBlank
    @Size(min = 3, max = 3)
    @Column(name = "CODE", length = 3)
    private String code;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS", nullable = false)
    private Status status;

    @NotNull
    @Column(name = "ORDER_ID", nullable = false)
    private String orderId;

    @NotNull
    @Column(name = "PAYMENT_TYPE_ID", nullable = false)
    private Long PaymentTypeId;

}
