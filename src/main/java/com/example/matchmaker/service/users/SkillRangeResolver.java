package com.example.matchmaker.service.users;

import com.example.matchmaker.domain.users.SkillRange;
import org.springframework.stereotype.Service;

import javax.annotation.Nonnull;
import java.util.Arrays;

/**
 * Сервис для формирования и взаимодействия с ренджами скиллов игроков
 */
@Service
public class SkillRangeResolver {

    /**
     * Минимальный скилл игрока
     */
    private static final Double MIN_SKILL = 0.0;
    /**
     * Максимальный скилл игрока
     */
    private static final Double MAX_SKILL = 10.0;
    /**
     * Количество участков, на которые разбито всевозможные значения скиллов игроков
     */
    private static final int RANGES_COUNT = 10;

    private final SkillRange[] ranges;

    public SkillRangeResolver() {
        ranges = new SkillRange[RANGES_COUNT];
        double rangeSkillStep = MAX_SKILL - MIN_SKILL / (double) RANGES_COUNT;
        double startSkillStep = MIN_SKILL;
        for (int i = 0; i < RANGES_COUNT; i++) {
            double endSkillStep = startSkillStep + rangeSkillStep;
            ranges[i] = new SkillRange(startSkillStep, endSkillStep);
            startSkillStep = endSkillStep;
        }
    }

    /**
     * Получить массив ренджей скиллов
     */
    @Nonnull
    public SkillRange[] getRanges() {
        return ranges;
    }

    /**
     * По величение скилла получить соответствующий участок разбиения из общего количества всевозможных скиллов
     */
    @Nonnull
    public SkillRange resolveRange(@Nonnull Double skill) {
        return Arrays.stream(ranges)
                .filter(it -> it.contains(skill))
                .findFirst()
                .orElseThrow();
    }

}
