package com.example.matchmaker.api.users;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.annotation.Nonnull;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

import static java.util.Objects.requireNonNull;

/**
 * Запрос на добавление игркова в пул
 */
public class AddUserRequest {

    /**
     * Имя игрока
     */
    @NotBlank
    private final String name;
    /**
     * Скилл игрока
     */
    @NotNull
    @DecimalMax(value = "10.0")
    @DecimalMin(value = "0.0")
    private final Double skill;
    /**
     * Задержка сигнала у игрока
     */
    @NotNull
    @PositiveOrZero
    private final Double latency;

    @JsonCreator
    private AddUserRequest(@Nonnull @JsonProperty("name") String name,
                           @Nonnull @JsonProperty("skill") Double skill,
                           @Nonnull @JsonProperty("latency") Double latency) {
        this.name = requireNonNull(name, "name");
        this.skill = requireNonNull(skill, "skill");
        this.latency = requireNonNull(latency, "latency");
    }

    /**
     * Создает новый объект билдера для {@link AddUserRequest}
     */
    @Nonnull
    public static Builder builder() {
        return new Builder();
    }

    @Nonnull
    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @Nonnull
    @JsonProperty("skill")
    public Double getSkill() {
        return skill;
    }

    @Nonnull
    @JsonProperty("latency")
    public Double getLatency() {
        return latency;
    }

    @Override
    public String toString() {
        return "AddUserRequest{" +
                "name='" + name + '\'' +
                ", skill=" + skill +
                ", latency=" + latency +
                '}';
    }

    /**
     * Билдер для {@link AddUserRequest}
     */
    public static final class Builder {
        private String name;
        private Double skill;
        private Double latency;

        private Builder() {
        }

        public Builder withName(@Nonnull String name) {
            this.name = name;
            return this;
        }

        public Builder withSkill(@Nonnull Double skill) {
            this.skill = skill;
            return this;
        }

        public Builder withLatency(@Nonnull Double latency) {
            this.latency = latency;
            return this;
        }

        /**
         * Собрать объект
         */
        @Nonnull
        public AddUserRequest build() {
            return new AddUserRequest(name, skill, latency);
        }
    }
}
