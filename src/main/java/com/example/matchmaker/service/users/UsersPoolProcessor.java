package com.example.matchmaker.service.users;

import com.example.matchmaker.domain.users.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.Nonnull;
import java.util.Objects;

/**
 * Сервис, который осуществляет взаимодействие с пулом игроков
 */
@Service
public class UsersPoolProcessor {

    private static final Logger log = LoggerFactory.getLogger(UsersPoolProcessor.class);

    private final int configGroupSize;
    private final UsersPool usersPool;
    private final UsersGroupFormatter usersGroupFormatter;

    public UsersPoolProcessor(@Value("${users.groupSize}") int configGroupSize,
                              @Nonnull UsersPool usersPool,
                              @Nonnull UsersGroupFormatter usersGroupFormatter) {
        this.usersPool = Objects.requireNonNull(usersPool, "usersPool");
        this.configGroupSize = configGroupSize;
        this.usersGroupFormatter = Objects.requireNonNull(usersGroupFormatter, "usersGroupFormatter");
    }

    /**
     * Добавить игрока в пул
     */
    public void add(@Nonnull User user) {
        log.info("add(): user={}", user);

        usersPool.add(user);
    }

    /**
     * Запустить задачу, которая в определенное время будет проверять пул и по возможности создавать группы игроков
     */
    @Scheduled(initialDelay = 1000, fixedDelay = 1000)
    public void createTaskForCreateUsersGroup() {
        try {
            createUsersGroup();
        } catch (RuntimeException e) {
            log.error("CreateUsersGroup failed", e);
        }
    }

    private void createUsersGroup() {

        usersPool.getUsersGroup(configGroupSize)
                .ifPresent(usersGroup -> {
                    log.info("The users group are created: usersGroup={}", usersGroup);

                    String format = usersGroupFormatter.format(usersGroup);
                    System.out.println(format);
                });
    }
}
