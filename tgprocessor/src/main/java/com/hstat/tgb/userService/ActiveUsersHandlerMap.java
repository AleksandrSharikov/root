package com.hstat.tgb.userService;

import com.hstat.common.CommonConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAmount;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;


/**
 * Map of active  user with some methods of its maintain
 */
@Slf4j
@Service
public class ActiveUsersHandlerMap implements ActiveUsersHandler {

   // private final CommonConstants commonConstants;
    private final RestTemplate restTemplate;

    private final String url;
    private final UserActivities userActivities;
    private Map<Long, LocalDateTime> activeUsers = new ConcurrentHashMap<>();

    private final Duration delDelay = Duration.ofMinutes(15);
    private final Duration checkDelay = Duration.ofMinutes(20);
    private final int warnSize = 20;
    private LocalDateTime firstRecord = null;

    public ActiveUsersHandlerMap(/*CommonConstants commonConstants,*/ RestTemplateBuilder restTemplateBuilder, UserActivities userActivities) {
    //    this.commonConstants = commonConstants;
        this.restTemplate = restTemplateBuilder.build();
        this.userActivities = userActivities;
        this.url = CommonConstants.ServicePort.USER.getPort();
    }


    @Override
    public void add(long chatId){
        log.info(String.format("Ad used %d to active", chatId));
        activeUsers.put(chatId, LocalDateTime.now());
        if(firstRecord == null){
            firstRecord = LocalDateTime.now();
        } else if (LocalDateTime.now().minus(checkDelay).isAfter(firstRecord)){
            cleanTime();
        }
    }

    @Override
    public boolean isActive(long chatId){
        LocalDateTime act = activeUsers.replace(chatId, LocalDateTime.now());
        return act != null;
    }

    @Override
    public boolean askUser(long userId){
        return Boolean.TRUE.equals(restTemplate.getForObject(url + "/tg/check/" + userId, Boolean.class)) ;
    }

    @Override
    public void remove(long chatId){
        activeUsers.remove(chatId);
        log.info(String.format("Remove user %d from active", chatId));
        if (firstRecord != null && LocalDateTime.now().minus(checkDelay).isAfter(firstRecord)){
            cleanTime();
        }
    }

    private int cleanTime(){
        return cleanTime(delDelay);
    }
    private int cleanTime(TemporalAmount gap){
        log.info(String.format("Remove all users inactive for %s", gap.toString()));
        int size = activeUsers.size();
        Set<Long> fullSet = new HashSet<>(activeUsers.keySet());
        LocalDateTime current = LocalDateTime.now();

        activeUsers.keySet().removeIf(s -> activeUsers.get(s).isBefore(current.minus(gap)));

        fullSet.removeAll(activeUsers.keySet());
        fullSet.forEach(userActivities::deactiveId);

        firstRecord = activeUsers.values().stream().min(LocalDateTime::compareTo).orElse(current);

        log.info(String.format("removed %d users", size - activeUsers.size()));
        return size - activeUsers.size();
    }
}