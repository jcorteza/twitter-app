package com.khoros.twitterapp;

import com.khoros.twitterapp.services.TwitterService;

import dagger.Module;
import dagger.Provides;
import twitter4j.Twitter;
import javax.inject.Singleton;

@Module
class ServiceProviderModule {
    private Twitter twFactory;

    public ServiceProviderModule(Twitter twFactory) {
        this.twFactory = twFactory;
    }

    @Provides @Singleton
    TwitterService provideTwitterService() {
            return new TwitterService(twFactory);
    }

}
