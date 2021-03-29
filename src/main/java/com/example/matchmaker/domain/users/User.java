package com.example.matchmaker.domain.users;

import javax.annotation.Nonnull;

import static java.util.Objects.requireNonNull;

public class User {

    /**
     * Личная информация об игроке
     */
    @Nonnull
    private final UserInfo userInfo;
    /**
     * Техническая информация об игроке
     */
    @Nonnull
    private final TechnicalInfo technicalInfo;


    private User(@Nonnull UserInfo userInfo, @Nonnull TechnicalInfo technicalInfo) {
        this.userInfo = requireNonNull(userInfo, "userInfo");
        this.technicalInfo = requireNonNull(technicalInfo, "technicalInfo");
    }

    /**
     * Создает новый объект билдера для {@link User}
     */
    @Nonnull
    public static Builder builder() {
        return new Builder();
    }

    @Nonnull
    public UserInfo getUserInfo() {
        return userInfo;
    }

    @Nonnull
    public TechnicalInfo getTechnicalInfo() {
        return technicalInfo;
    }

    @Override
    public String toString() {
        return "User{" +
                "userInfo=" + userInfo +
                ", technicalInfo=" + technicalInfo +
                '}';
    }

    /**
     * Билдер для {@link User}
     */
    public static final class Builder {
        private UserInfo userInfo;
        private TechnicalInfo technicalInfo;

        private Builder() {
        }

        public Builder withUserInfo(@Nonnull UserInfo userInfo) {
            this.userInfo = userInfo;
            return this;
        }

        public Builder withTechnicalInfo(@Nonnull TechnicalInfo technicalInfo) {
            this.technicalInfo = technicalInfo;
            return this;
        }

        /**
         * Собрать объект
         */
        @Nonnull
        public User build() {
            return new User(userInfo, technicalInfo);
        }
    }
}
