package web.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import web.model.User;
import org.springframework.stereotype.Repository;


import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Component
@Repository
public class UserDaoImpl implements UserDao {
    @PersistenceContext
    private final EntityManager em;
    @Autowired
    public UserDaoImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    @Bean
    public void addUser(User user) {
        em.persist(user);
    }
    @Override
    @Bean
    public void updateUser(User user) {
        em.merge(user);
    }
    @Override
    public void deleteUser(User user) {
        em.remove(user);
    }

    @Override
    @Transactional
    @Bean
    public List<User> getAllUsers() {
        return em.createQuery("select u from User u", User.class).getResultList();
    }
}
