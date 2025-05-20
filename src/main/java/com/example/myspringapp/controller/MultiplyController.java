package com.example.myspringapp.controller;

import com.example.myspringapp.service.MultiplyService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class MultiplyController {

    private final MultiplyService multiplyService;

    public MultiplyController(MultiplyService multiplyService) {
        this.multiplyService = multiplyService;
    }

    @GetMapping("/multiply")
    public String showForm() {
        return "inputForm";
    }

    @PostMapping("/result")
    public String showResult(@RequestParam("number") String numberStr,
                             Model model,
                             RedirectAttributes redirectAttributes) {
        try {
            int result = multiplyService.parseAndMultiply(numberStr);
            model.addAttribute("number", Integer.parseInt(numberStr));
            model.addAttribute("result", result);
            return "result";
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("error", "Пожалуйста, введите корректное число.");
            return "redirect:/multiply";
        }
    }

    @GetMapping("/result")
    public String redirectToForm() {
        return "redirect:/multiply";
    }
}
