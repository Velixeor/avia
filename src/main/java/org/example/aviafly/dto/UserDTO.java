package org.example.aviafly.dto;


import lombok.Builder;
import org.example.aviafly.entity.User;


@Builder
public record UserDTO(
        Integer id,
        String name,
        String mail
) {
    public User toEntity() {
        return new User(null, name, mail);
    }
    public static UserDTO fromEntity(User user) {
        return UserDTO.builder()
                .id(user.getId())
                .name(user.getName())
                .mail(user.getMail())
                .build();
    }

}
