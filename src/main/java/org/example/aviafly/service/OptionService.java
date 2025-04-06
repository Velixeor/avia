package org.example.aviafly.service;


import lombok.RequiredArgsConstructor;
import org.example.aviafly.dto.OptionDTO;
import org.example.aviafly.entity.Option;
import org.example.aviafly.exception.BusinessException;
import org.example.aviafly.exception.NotFoundException;
import org.example.aviafly.repository.OptionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class OptionService {

    private final OptionRepository optionRepository;

    @Transactional
    public OptionDTO createOption(OptionDTO optionDTO) {
        Option option = optionRepository.save(optionDTO.toEntity());
        return OptionDTO.fromEntity(option);
    }

    @Transactional
    public OptionDTO toggleOptionAvailability(Integer optionId, Boolean available) {
        Option option = optionRepository.findById(optionId)
                .orElseThrow(() -> new NotFoundException(Option.class, optionId));

        option.setAvailable(available);
        return OptionDTO.fromEntity(optionRepository.save(option));
    }

    @Transactional(readOnly = true)
    public Option getAvailableOptionById(Integer optionId) {
        Option option = optionRepository.findById(optionId)
                .orElseThrow(() -> new NotFoundException(Option.class, optionId));
        if (!option.getAvailable()) {
            throw new BusinessException("Option with id " + optionId + " is not available.");
        }
        return option;
    }
}
