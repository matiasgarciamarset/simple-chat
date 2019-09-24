package com.asapp.backend.challenge.repository.impl;

import com.asapp.backend.challenge.repository.TokenRepository;
import com.asapp.backend.challenge.repository.config.GenericDao;
import com.asapp.backend.challenge.repository.config.HibernateUtil;
import com.asapp.backend.challenge.repository.model.Token;
import com.asapp.backend.challenge.repository.model.User;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;

import java.util.Optional;

@Slf4j
public class TokenRepositoryImpl extends GenericDao<Token, String> implements TokenRepository {

    @Override
    public Optional<Token> findByUser(User user) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        return session.createQuery("from Token where user = :value", Token.class)
                .setParameter("value", user)
                .uniqueResultOptional();
    }

    @Override
    public String save(Token token) {
        return super.save(token);
    }

    @Override
    public Boolean existsById(String token) {
        return findById(token).isPresent();
    }

    @Override
    public Optional<Token> findById(String token) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        return session.createQuery("from Token where token = :value", Token.class)
                .setParameter("value", token)
                .uniqueResultOptional();
    }
}
