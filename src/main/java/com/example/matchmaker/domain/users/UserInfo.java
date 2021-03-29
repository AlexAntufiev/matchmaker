package com.example.matchmaker.domain.users;

import javax.annotation.Nonnull;

import static java.util.Objects.requireNonNull;

public class UserInfo {

    /**
     * Имя игрока
     */
    @Nonnull
    private final String name;
    /**
     * Скилл игрока
     */
    @Nonnull
    private final Double skill;

    private UserInfo(@Nonnull String name, @Nonnull Double skill) {
        this.name = requireNonNull(name, "name");
        this.skill = requireNonNull(skill, "skill");
    }

    /**
     * Создает новый объект билдера для {@link UserInfo}
     */
    @Nonnull
    public static Builder builder() {
        return new Builder();
    }

    @Nonnull
    public String getName() {
        return name;
    }

    @Nonnull
    public Double getSkill() {
        return skill;
    }

    @Override
    public String toString() {
        return "UserInfo{" +
                "name='" + name + '\'' +
                ", skill=" + skill +
                '}';
    }

    /**
     * Билдер для {@link UserInfo}
     */
    public static final class Builder {
        private String name;
        private Double skill;

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

        /**
         * Собрать объект
         */
        @Nonnull
        public UserInfo build() {
            return new UserInfo(name, skill);
        }
    }
}
