package com.lkj.restfulwebservice.user;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserService {

    private static List<User> users = new ArrayList<>();

    private static int usersCount = 3;

    static {
        users.add(new User(1, "lkj1'", new Date()));
        users.add(new User(2, "lkj2'", new Date()));
        users.add(new User(3, "lkj3'", new Date()));
    }

    public List<User> findAll() {
        return users;
    }

    public User findOne(Integer id) {
        for(User user : users) {
            if(user.getId().equals(id)) {
                return user;
            }
        }
        return null;
    }

    public User save(User user) {
        if(user.getId() == null) {
            user.setId(++usersCount);
        }

        users.add(user);
        return user;
    }

    public User deleteById(Integer id) {
        Iterator<User> iterator = users.iterator();

        while(iterator.hasNext()) {
            User user = iterator.next();

            if(user.getId().equals(id)) {
                iterator.remove();
                return user;
            }
        }

        return null;
    }
}
