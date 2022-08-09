package com.example.rediscachespring.controller;

import com.example.rediscachespring.entity.User;
import com.example.rediscachespring.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.http.HttpEntity;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@Slf4j
public class UserController {
//    private final CacheManager cacheManager;
    private final UserRepository userRepository;
    //    private final CacheManager cacheManager;
//    private final Logger LOG = LoggerFactory.getLogger(getClass());

    @Cacheable(value = "ketmon", key = "#userId")
    @GetMapping("/{userId}")
    public User getUser(@PathVariable Long userId) {
//        Cache ketmon = cacheManager.getCache("ketmon");
//        System.out.println(ketmon);
//        System.out.println(ketmon.get("1"));
        //in memory cache xuddi databasedek
//        Cache cache = cacheManager.getCache("ketmon");
//        cache.put("userId", userId);
//        System.out.println("Keldi!");
//        Cache.ValueWrapper wrapper = cache.get("userId");
//        System.out.println(Objects.requireNonNull(Objects.requireNonNull(wrapper).get()).toString());
        System.out.println("Keldi!");
        log.info("GETTING USER ID {}.", userId);
        return userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User topilmadi"));
    }

    //cacheable ichiudagi nom e'lon qilingan ochilgan cache nomi bn bir xil bo'lishi kerak
    @Cacheable(value = "ketmon")
    @GetMapping("/all")
    public List<User> getAll() {
        log.info("GET ALL {} ");
        return userRepository.findAll();
    }

    //ishlamadi topish kk
    @CachePut(value = "ketmon", key = "#user.id")
    @PutMapping("/{id}")
    public User edit(@PathVariable Long id, @RequestBody User user) {
        log.info("Edit boshlandi");
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isEmpty()) return new User();

        User edited = optionalUser.get();
        edited.setName(user.getName());
        edited.setFollowers(user.getFollowers());
        User save = userRepository.save(edited);
        return save;
    }

    @CacheEvict(value = "ketmon", allEntries = true)
    @DeleteMapping("/{id}")
    public HttpEntity delete(@PathVariable Long id) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isEmpty()) return new HttpEntity("User not found!");

        userRepository.deleteById(id);
        return new HttpEntity("User deleted!");
    }
}
