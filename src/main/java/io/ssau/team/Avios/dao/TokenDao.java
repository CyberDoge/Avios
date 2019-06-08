package io.ssau.team.Avios.dao;

import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

@Repository
public class TokenDao {
    private static final Map<String, String> tokens = new HashMap<>();

    @PostConstruct
    public void init() {
        tokens.put("123", "User");
        tokens.put("aaa", "Admin");
        tokens.put("0", "simple");
        tokens.put("ae49a597-c0f3-496f-bd6b-c1770413068c", "first");
        tokens.put("9803efd9-955d-4b2c-b4d9-12a86aac65a2", "second");
    }

    public boolean contains(String token) {
        return tokens.containsKey(token);
    }

    public String get(String token) {
        return tokens.get(token);
    }

    public String create(String token, String username){
        tokens.put(token, username);
        return username;
    }
}
