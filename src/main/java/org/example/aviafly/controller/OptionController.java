package org.example.aviafly.controller;


import lombok.RequiredArgsConstructor;
import org.example.aviafly.dto.OptionDTO;
import org.example.aviafly.service.OptionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/options")
@RequiredArgsConstructor
public class OptionController {

    private final OptionService optionService;

    @PutMapping("/{optionId}/toggle")
    public ResponseEntity<OptionDTO> toggleOption(@PathVariable Integer optionId,
                                                  @RequestParam Boolean available) {
        return ResponseEntity.ok(optionService.toggleOptionAvailability(optionId, available));
    }
}
