package br.com.microservices.orchestrated.orderservice.core.service;

import br.com.microservices.orchestrated.orderservice.config.exceptions.ValidationException;
import br.com.microservices.orchestrated.orderservice.core.document.Event;
import br.com.microservices.orchestrated.orderservice.core.dto.EventFilters;
import br.com.microservices.orchestrated.orderservice.core.repository.EventRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

import static org.springframework.util.ObjectUtils.isEmpty;


@Slf4j
@Service
@AllArgsConstructor
public class EventService {
    private final EventRepository repository;

    public void notifyEnding(Event event) {
        event.setOrderId(event.getOrderId());
        event.setCreatedAt(LocalDateTime.now());
        save(event);
        log.info("Order {} with saga notify! TransactionId: {}", event.getOrderId(), event.getTransactionId());
    }

    public Event save(Event event) {
        return repository.save(event);
    }

    public List<Event> findAll() {
        return repository.findAllByOrderByCreatedAtDesc();
    }

    public Event findByFilters(EventFilters filters) {
        validationFilters(filters);
        if(!isEmpty(filters.getOrderId())) {
            return findByOrderId(filters.getOrderId());
        } else {
            return findByTransactionId(filters.getTransactionId());
        }
    }

    private Event findByOrderId(String orderId) {
        return repository.findTop1ByOrderIdOrderByCreatedAtDesc(orderId)
                .orElseThrow(() -> new ValidationException("Event not found by orderId."));
    }

    private Event findByTransactionId(String transactionId) {
        return repository.findTop1TransactionIdOrderByCreatedAtDesc(transactionId)
                .orElseThrow(() -> new ValidationException("Event not found by transactionId."));
    }

    private void validationFilters(EventFilters filters) {
        if(isEmpty(filters.getOrderId()) && isEmpty(filters.getTransactionId())) {
            throw new ValidationException("OrderID or TransactionId must be informed.");
        }
    }
}
