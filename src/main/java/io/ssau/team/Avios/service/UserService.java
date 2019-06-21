package io.ssau.team.Avios.service;

import io.ssau.team.Avios.dao.UserDao;
import io.ssau.team.Avios.model.json.UserJson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

@Service
public class UserService {
    private UserDao userDao;

    @Autowired
    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }
    public UserJson getUserById(Integer id){
        return new UserJson(userDao.getUserByIdNullable(id).orElseThrow(()-> new HttpClientErrorException(HttpStatus.NOT_FOUND)));
    }
}
