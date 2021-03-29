package com.example.matchmaker.service.users;

import com.example.matchmaker.domain.users.SkillRange;
import com.example.matchmaker.domain.users.User;
import com.example.matchmaker.domain.users.UsersGroup;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Nonnull;
import java.time.OffsetDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Пул игроков, которые ожидают игры. Пул из себя представляем карту ренджа скиллов игроков к очереди игроков.
 * Игроки в очереди сортируются по мере добавления
 */
@Service
public class UsersPool {

    private static final Comparator<User> USER_COMPARATOR = Comparator.comparing(
            user -> user,
            (user1, user2) -> {
                double latencyDifference = user1.getTechnicalInfo().getLatency()
                        - user2.getTechnicalInfo().getLatency();
                long createdTimeDifference = user1.getTechnicalInfo().getCreatedTime()
                        .until(user2.getTechnicalInfo().getCreatedTime(), ChronoUnit.MILLIS);
                return (int) (latencyDifference + createdTimeDifference);
            });

    private final ConcurrentMap<SkillRange, BlockingQueue<User>> skillRangeToUsers;
    private final SkillRangeResolver skillRangeResolver;
    private static final AtomicLong USERS_GROUP_SEQUENCE = new AtomicLong();

    public UsersPool(SkillRangeResolver skillRangeResolver,
                     @Value("${users.groupSize}") int configGroupSize) {
        this.skillRangeResolver = skillRangeResolver;
        SkillRange[] ranges = skillRangeResolver.getRanges();
        this.skillRangeToUsers = new ConcurrentHashMap<>(ranges.length);
        for (SkillRange range : ranges) {
            skillRangeToUsers.put(range, new PriorityBlockingQueue<>(configGroupSize * 2 - 1, USER_COMPARATOR));
        }
    }

    /**
     * Добавить игрока в пул
     */
    public void add(@Nonnull User user) {
        Objects.requireNonNull(user, "user");
        SkillRange skillRange = skillRangeResolver.resolveRange(user.getUserInfo().getSkill());
        var users = Objects.requireNonNull(skillRangeToUsers.get(skillRange), "skillRangeToUsers.get(skillRange)");
        try {
            users.put(user);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Получить группу игроков.
     *
     * @return группу игроков, если запрошенное количество игроков со подходящими параметрами содержатся в пуле
     * или {@link Optional#empty()} если нельзя сформировать группу запрошенного размера
     */
    @Nonnull
    public Optional<UsersGroup> getUsersGroup(int groupSize) {

        var usersQueueOpt = skillRangeToUsers.values()
                .stream()
                .filter(it -> it.size() >= groupSize)
                .findFirst();

        if (usersQueueOpt.isEmpty()) {
            return Optional.empty();
        }

        var usersQueue = usersQueueOpt.get();
        var usersGroup = new ArrayList<User>(groupSize);
        for (int i = 0; i < groupSize; i++) {
            try {
                usersGroup.add(usersQueue.take());
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        return Optional.of(
                UsersGroup.builder()
                        .withId(USERS_GROUP_SEQUENCE.getAndIncrement())
                        .withUsers(usersGroup)
                        .withCreatedTime(OffsetDateTime.now())
                        .build());
    }
}
