package com.soliduslabs.homework.Problem1.coverter;

import com.google.common.hash.Hashing;
import com.soliduslabs.homework.Problem1.model.Message;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Service
public class MessageConverter {
    private Map<String, Message> sha256ToMessageMap = new ConcurrentHashMap<>();


    public String convertMessageToSHA256(Message message){
        log.info("convert message to sha256 string");
        String sha256hex = Hashing.sha256()
                .hashString(message.getMessage(), StandardCharsets.UTF_8)
                .toString();
        sha256ToMessageMap.put(sha256hex,message);
        return sha256hex;
    }

    public Message convertSHA256ToMessage(String sha256hex){
        log.info("trying convert sha256 string to a message");
        return sha256ToMessageMap.get(sha256hex);
    }

}
