package com.example.controller;

import com.example.common.Result;
import com.example.entity.Params;
import com.example.entity.User;
import com.example.service.UserService;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;

    /**
     * controller里的方法就是web项目接口的入口
     *
     * @return
     */
    @PostMapping("/login")
    public Result login(@RequestBody User user){
        User loginUser = userService.login(user);
        return Result.success(loginUser);
    }

    @GetMapping("/getAll")
    public Result getUser() {
        List<User> list = userService.getAll();
        return Result.success(list);
    }

    @GetMapping("/search")
    public Result findBySearch(Params params) {
        PageInfo<User> info = userService.findBySearch(params);
        return Result.success(info);
    }

    @PostMapping("/add")
    public Result save(@RequestBody User user) {
        if (user.getId() == null) {
            userService.add(user);
        }else{
            userService.update(user);
        }
        return Result.success();
    }

    @DeleteMapping("/del/{id}")
    public Result delete(@PathVariable Integer id){
        userService.delete(id);
        return Result.success();
    }
}
