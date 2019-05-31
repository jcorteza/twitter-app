package com.khoros.twitterapp;

import dagger.Component;
import twitter4j.Twitter;

@Component(modules = TwitterFactoryModule.class)
public interface TwitterFactoryComponent {
    Twitter twFactory();
}
