package com.example.service;

import com.example.common.JwtTokenUtils;
import com.example.dao.UserDao;
import com.example.entity.Params;
import com.example.entity.User;
import com.example.exception.CustomException;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UserService {

    @Resource
    private UserDao userDao;

    public List<User> getAll() {
        return userDao.getAll();
    }

    public PageInfo<User> findBySearch(Params params) {
        PageHelper.startPage(params.getCurrentPage(), params.getPageSize());
        List<User> list = userDao.findBySearch(params);
        return PageInfo.of(list);
    }

    public void add(User user) {
        if(user.getName() == null || "".equals(user.getName())){
            throw new CustomException("用户名不能为空");
        }
        User user1 = userDao.findByName(user.getName());
        if(user1 != null){
            throw new CustomException("该用户已存在");
        }
        if(user.getPassword() == null) {
            user.setPassword("123456");
        }
        userDao.insertSelective(user);
    }

    public void update(User user) {
        userDao.updateByPrimaryKeySelective(user);
    }

    public void delete(Integer id) {
        userDao.deleteByPrimaryKey(id);
    }

    public User login(User user) {
        User user2 = userDao.findByNamePassword(user.getName(), user.getPassword());
        if(user2 == null){
            throw new CustomException("用户名或密码输入错误");
        }
        // 生成该用户对应的token
        String token = JwtTokenUtils.getToken(user2.getId().toString(), user2.getPassword());
        user2.setToken(token);
        return user2;
    }

    public User findById(Integer id) {
        return userDao.selectByPrimaryKey(id);
    }

}
