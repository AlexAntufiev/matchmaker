package com.example.matchmaker.api.users;

import com.example.matchmaker.domain.users.TechnicalInfo;
import com.example.matchmaker.domain.users.User;
import com.example.matchmaker.domain.users.UserInfo;
import com.example.matchmaker.service.users.UsersPoolProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Nonnull;
import javax.validation.Valid;
import java.time.OffsetDateTime;
import java.util.Objects;

/**
 * Обработчик запросов игроков в пуле
 */
@RequestMapping(
        value = "/users/",
        produces = MediaType.APPLICATION_JSON_VALUE,
        consumes = MediaType.APPLICATION_JSON_VALUE)
@RestController
public class UsersController {

    private static final Logger log = LoggerFactory.getLogger(UsersController.class);

    private final UsersPoolProcessor usersPoolProcessor;

    public UsersController(@Nonnull UsersPoolProcessor usersPoolProcessor) {
        this.usersPoolProcessor = Objects.requireNonNull(usersPoolProcessor, "usersPoolProcessor");
    }

    /**
     * Обработать запрос на добавление игрока в пул
     */
    @PostMapping("/")
    public ResponseEntity<Void> addUsers(@RequestBody @Valid AddUserRequest addUserRequest) {
        log.info("addUsers(): addUserRequest={}", addUserRequest);

        usersPoolProcessor.add(User.builder()
                .withUserInfo(UserInfo.builder()
                        .withName(addUserRequest.getName())
                        .withSkill(addUserRequest.getSkill())
                        .build())
                .withTechnicalInfo(TechnicalInfo.builder()
                        .withLatency(addUserRequest.getLatency())
                        .withCreatedTime(OffsetDateTime.now())
                        .build())
                .build());

        return ResponseEntity.ok().build();
    }
}
