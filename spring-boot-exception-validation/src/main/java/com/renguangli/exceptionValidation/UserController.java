package com.renguangli.exceptionValidation;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/*
 *
 * Created by renguangli at 2018/9/1 13:31
 * @since JDK1.8
 */
@RestController
public class UserController {

    @GetMapping(value = {"", "/"})
    public User user(@Valid User user) {
        return user;
    }

    @GetMapping(value = {"user"})
    public Integer user(Integer id) {
        if (id == null) {
            throw new NullPointerException("id不能为null");
        }
        return id;
    }
}
