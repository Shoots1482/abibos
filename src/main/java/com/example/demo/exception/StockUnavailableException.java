package com.example.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class StockUnavailableException extends RuntimeException {

    private Long productId;
    private Integer requestedQuantity;
    private Integer availableQuantity;

    public StockUnavailableException(Long productId, Integer requestedQuantity, Integer availableQuantity) {
        super(String.format("Product with id %d has only %d items in stock, but %d were requested", 
                productId, availableQuantity, requestedQuantity));
        this.productId = productId;
        this.requestedQuantity = requestedQuantity;
        this.availableQuantity = availableQuantity;
    }

    public StockUnavailableException(String message) {
        super(message);
    }

    public Long getProductId() {
        return productId;
    }

    public Integer getRequestedQuantity() {
        return requestedQuantity;
    }

    public Integer getAvailableQuantity() {
        return availableQuantity;
    }
} 