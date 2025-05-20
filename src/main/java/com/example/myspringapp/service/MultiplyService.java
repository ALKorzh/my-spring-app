package com.example.myspringapp.service;

import org.springframework.stereotype.Service;

@Service
public class MultiplyService {

    private static final int N = 5;

    public int multiply(int number) {
        return number * N;
    }

    public int parseAndMultiply(String numberStr) {
        try {
            int number = Integer.parseInt(numberStr);
            return multiply(number);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Неверный формат числа", e);
        }
    }
}
