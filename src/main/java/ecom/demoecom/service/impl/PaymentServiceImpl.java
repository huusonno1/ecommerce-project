package ecom.demoecom.service.impl;

import ecom.demoecom.entity.Payment;
import ecom.demoecom.repo.PaymentRepository;
import ecom.demoecom.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentServiceImpl implements PaymentService {
    @Autowired
    private PaymentRepository paymentRepository;
    @Override
    public void savePayment(Payment payment) {
        paymentRepository.save(payment);
    }
}
