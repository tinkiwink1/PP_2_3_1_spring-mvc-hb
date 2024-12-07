package web.service;

import web.dao.UserDaoImpl;
import web.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
@Transactional
@Service
public class UserServiceImpl implements UserService {
    private final UserDaoImpl userDao;

    @Autowired
    public UserServiceImpl(UserDaoImpl userDao) {
        this.userDao = userDao;
    }
    @Override
    public void addUser(User user) {
        userDao.addUser(user);
    }
    @Override
    public void updateUser(User user) {
        userDao.updateUser(user);
    }
    @Override
    public void deleteUser(User user) {
        userDao.deleteUser(user);
    }
    @Override
    public List<User> getAllUsers() {
        return userDao.getAllUsers();
    }
}
