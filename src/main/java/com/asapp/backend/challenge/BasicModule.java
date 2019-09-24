package com.asapp.backend.challenge;

import com.asapp.backend.challenge.repository.MessageContentRepository;
import com.asapp.backend.challenge.repository.MessageRepository;
import com.asapp.backend.challenge.repository.TokenRepository;
import com.asapp.backend.challenge.repository.UserRepository;
import com.asapp.backend.challenge.repository.impl.*;
import com.asapp.backend.challenge.service.AuthService;
import com.asapp.backend.challenge.service.LoginService;
import com.asapp.backend.challenge.service.MessageService;
import com.asapp.backend.challenge.service.impl.AuthServiceImpl;
import com.asapp.backend.challenge.service.impl.LoginServiceImpl;
import com.asapp.backend.challenge.service.impl.MessageServiceImpl;
import com.google.inject.AbstractModule;
import com.google.inject.Scopes;
import com.google.inject.multibindings.Multibinder;

public class BasicModule extends AbstractModule {

    @Override
    protected void configure() {
        // Service
        bind(LoginService.class).to(LoginServiceImpl.class).in(Scopes.SINGLETON);
        bind(AuthService.class).to(AuthServiceImpl.class).in(Scopes.SINGLETON);
        bind(MessageService.class).to(MessageServiceImpl.class).in(Scopes.SINGLETON);

        // Repository
        bind(TokenRepository.class).to(TokenRepositoryImpl.class).in(Scopes.SINGLETON);
        bind(UserRepository.class).to(UserRepositoryImpl.class).in(Scopes.SINGLETON);
        bind(MessageRepository.class).to(MessageRepositoryImpl.class).in(Scopes.SINGLETON);

        Multibinder<MessageContentRepository> messageBinder =
                Multibinder.newSetBinder(binder(), MessageContentRepository.class);
        messageBinder.addBinding().to(MessageImageRepositoryImpl.class);
        messageBinder.addBinding().to(MessageTextRepositoryImpl.class);
        messageBinder.addBinding().to(MessageVideoRepositoryImpl.class);
    }
}
