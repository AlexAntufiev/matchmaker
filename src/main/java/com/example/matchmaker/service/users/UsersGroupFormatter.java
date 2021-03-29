package com.example.matchmaker.service.users;

import com.example.matchmaker.domain.users.TechnicalInfo;
import com.example.matchmaker.domain.users.User;
import com.example.matchmaker.domain.users.UserInfo;
import com.example.matchmaker.domain.users.UsersGroup;
import org.springframework.stereotype.Service;

import javax.annotation.Nonnull;
import java.time.temporal.ChronoUnit;
import java.util.stream.Collectors;

/**
 * Сервис для формирования строковой информации о группе игроков
 */
@Service
public class UsersGroupFormatter {

    /**
     * Вывести информацию о группе игроков
     */
    @Nonnull
    public String format(@Nonnull UsersGroup usersGroup) {
        var users = usersGroup.getUsers();
        var skills = users.stream()
                .map(User::getUserInfo)
                .map(UserInfo::getSkill)
                .sorted()
                .collect(Collectors.toUnmodifiableList());
        var avgSkill = skills.stream()
                .reduce(Double::sum)
                .get() / (double) skills.size();

        var latencies = users.stream()
                .map(User::getTechnicalInfo)
                .map(TechnicalInfo::getLatency)
                .sorted()
                .collect(Collectors.toUnmodifiableList());
        var avgLatency = latencies.stream()
                .reduce(Double::sum).get() / (double) latencies.size();

        var millisecondsSpentInQueue = users.stream()
                .map(User::getTechnicalInfo)
                .map(TechnicalInfo::getCreatedTime)
                .map(it -> it.until(usersGroup.getCreatedTime(), ChronoUnit.MILLIS))
                .sorted()
                .collect(Collectors.toUnmodifiableList());
        var avgMillisecondsSpentInQueue = millisecondsSpentInQueue.stream()
                .reduce(Long::sum)
                .get() / (double) millisecondsSpentInQueue.size();

        var userNames = users.stream()
                .map(User::getUserInfo)
                .map(UserInfo::getName)
                .collect(Collectors.joining(", "));

        return new StringBuilder()
                .append("Sequence id=").append(usersGroup.getId()).append('\n')
                .append("Skill in group: ")
                        .append("min=").append(skills.get(0)).append(", ")
                        .append("max=").append(skills.get(skills.size()- 1)).append(", ")
                        .append("avg=").append(avgSkill).append(", ")
                        .append('\n')
                .append("Latency in group: ")
                        .append("min=").append(latencies.get(0)).append(", ")
                        .append("max=").append(latencies.get(latencies.size()- 1)).append(", ")
                        .append("avg=").append(avgLatency).append(", ")
                        .append('\n')
                .append("Time(ms) spent in queue: ")
                        .append("min=").append(millisecondsSpentInQueue.get(0)).append(", ")
                        .append("max=").append(millisecondsSpentInQueue.get(millisecondsSpentInQueue.size()- 1)).append(", ")
                        .append("avg=").append(avgMillisecondsSpentInQueue).append(", ")
                        .append('\n')
                .append("User names: ").append(userNames)
                .toString();
    }
}
