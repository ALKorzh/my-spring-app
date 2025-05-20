package com.example.myspringapp.service;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MultiplyServiceTest {

    private final MultiplyService service = new MultiplyService();

    @Test
    void multiply_shouldReturnProduct() {
        assertEquals(25, service.multiply(5));
        assertEquals(0, service.multiply(0));
        assertEquals(-10, service.multiply(-2));
    }

    @Test
    void parseAndMultiply_validInput_shouldReturnResult() {
        assertEquals(15, service.parseAndMultiply("3"));
    }

    @Test
    void parseAndMultiply_invalidInput_shouldThrowException() {
        assertThrows(IllegalArgumentException.class, () -> service.parseAndMultiply("abc"));
        assertThrows(IllegalArgumentException.class, () -> service.parseAndMultiply(""));
    }
}
