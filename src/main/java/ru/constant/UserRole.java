package ru.constant;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;


public enum UserRole implements GrantedAuthority {
    @JsonProperty("Администратор")
    ADMIN("Администратор"),
    @JsonProperty("Пользователь")
    USER("Пользователь");

    @Getter
    private String userRoleName;

    UserRole(String userRoleName) {
        this.userRoleName = userRoleName;
    }

    @Override
    public String getAuthority() {
        return name();
    }
}
