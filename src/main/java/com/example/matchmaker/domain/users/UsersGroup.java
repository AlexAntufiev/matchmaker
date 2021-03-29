package com.example.matchmaker.domain.users;

import javax.annotation.Nonnull;
import java.time.OffsetDateTime;
import java.util.Collection;

import static java.util.Objects.requireNonNull;

public class UsersGroup {

    /**
     * Монотонно возрастающий идентификатор группы игроков
     */
    private final long id;
    /**
     * Список игроков
     */
    @Nonnull
    private final Collection<User> users;
    /**
     * Время создания группы игроков
     */
    @Nonnull
    private final OffsetDateTime createdTime;

    private UsersGroup(long id, @Nonnull Collection<User> users, @Nonnull OffsetDateTime createdTime) {
        this.id = id;
        this.users = requireNonNull(users, "users");
        this.createdTime = requireNonNull(createdTime, "createdTime");
    }

    /**
     * Создает новый объект билдера для {@link UsersGroup}
     */
    @Nonnull
    public static Builder builder() {
        return new Builder();
    }

    public long getId() {
        return id;
    }

    @Nonnull
    public Collection<User> getUsers() {
        return requireNonNull(users);
    }

    @Nonnull
    public OffsetDateTime getCreatedTime() {
        return createdTime;
    }

    @Override
    public String toString() {
        return "UsersGroup{" +
                "id=" + id +
                ", users=" + users +
                ", createdTime=" + createdTime +
                '}';
    }

    /**
     * Билдер для {@link UsersGroup}
     */
    public static final class Builder {
        private long id;
        private Collection<User> users;
        private OffsetDateTime createdTime;

        private Builder() {
        }

        public Builder withId(long id) {
            this.id = id;
            return this;
        }

        public Builder withUsers(@Nonnull Collection<User> users) {
            this.users = users;
            return this;
        }

        public Builder withCreatedTime(@Nonnull OffsetDateTime createdTime) {
            this.createdTime = createdTime;
            return this;
        }

        /**
         * Собрать объект
         */
        @Nonnull
        public UsersGroup build() {
            return new UsersGroup(id, users, createdTime);
        }
    }
}
