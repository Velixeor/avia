package org.example.aviafly.dto;


import lombok.Builder;
import org.example.aviafly.entity.Option;

import java.math.BigDecimal;


@Builder
public record OptionDTO(
        Integer id,
        BigDecimal price,
        Boolean available
) {
    public Option toEntity() {
        return new Option(null, price, available);
    }

    public static OptionDTO fromEntity(Option option) {
        return OptionDTO.builder()
                .id(option.getId())
                .price(option.getPrice())
                .available(option.getAvailable())
                .build();
    }
}
