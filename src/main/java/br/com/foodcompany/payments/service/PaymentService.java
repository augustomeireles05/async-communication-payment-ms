package br.com.foodcompany.payments.service;

import br.com.foodcompany.payments.dto.PaymentDto;
import br.com.foodcompany.payments.model.Payment;
import br.com.foodcompany.payments.model.Status;
import br.com.foodcompany.payments.repository.PaymentRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository repository;

    private final ModelMapper mapper;

    private final RabbitMQService rabbitMQService;

    public Page<PaymentDto> getAll(Pageable pageable) {
        return repository.findAll(pageable).map(p -> mapper.map(p, PaymentDto.class));
    }

    public PaymentDto getById(Long id) {
        var payment = repository.findById(id)
                .orElseThrow(EntityNotFoundException::new);

        return mapper.map(payment, PaymentDto.class);
    }

    public PaymentDto createPayment(PaymentDto dto) {
        var payment = mapper.map(dto, Payment.class);
        payment.setStatus(Status.CREATED);
        repository.save(payment);

        rabbitMQService.sendMessage(payment);

        return mapper.map(payment, PaymentDto.class);
    }

    public PaymentDto updatePayment(PaymentDto dto, Long id) {
        var payment = mapper.map(dto, Payment.class);
        payment.setId(id);
        payment = repository.save(payment);
        return mapper.map(payment, PaymentDto.class);
    }

    public void deletePayment(Long id) {
        repository.deleteById(id);
    }

    public void confirmPayment(String orderId) {
        var payment = repository.findByOrderId(orderId)
                .orElseThrow(() -> new EntityNotFoundException("Payment not found for Order ID " + orderId));

        payment.setStatus(Status.CONFIRMED);
        repository.save(payment);

        rabbitMQService.sendMessage(payment);
    }
}
