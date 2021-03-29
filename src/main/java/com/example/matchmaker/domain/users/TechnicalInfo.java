package com.example.matchmaker.domain.users;

import javax.annotation.Nonnull;

import java.time.OffsetDateTime;

import static java.util.Objects.requireNonNull;

public class TechnicalInfo {

    /**
     * Задержка сигнала у игрока
     */
    @Nonnull
    private final Double latency;
    /**
     * Время добавления игрока в пулл
     */
    @Nonnull
    private final OffsetDateTime createdTime;

    private TechnicalInfo(@Nonnull Double latency, @Nonnull OffsetDateTime createdTime) {
        this.latency = requireNonNull(latency, "latency");
        this.createdTime = requireNonNull(createdTime, "createdTime");
    }

    /**
     * Создает новый объект билдера для {@link TechnicalInfo}
     */
    @Nonnull
    public static Builder builder() {
        return new Builder();
    }

    @Nonnull
    public Double getLatency() {
        return latency;
    }

    @Nonnull
    public OffsetDateTime getCreatedTime() {
        return createdTime;
    }

    @Override
    public String toString() {
        return "TechnicalInfo{" +
                "latency=" + latency +
                ", createdTime=" + createdTime +
                '}';
    }

    /**
     * Билдер для {@link TechnicalInfo}
     */
    public static final class Builder {
        private Double latency;
        private OffsetDateTime createdTime;

        private Builder() {
        }

        public Builder withLatency(@Nonnull Double latency) {
            this.latency = latency;
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
        public TechnicalInfo build() {
            return new TechnicalInfo(latency, createdTime);
        }
    }
}
