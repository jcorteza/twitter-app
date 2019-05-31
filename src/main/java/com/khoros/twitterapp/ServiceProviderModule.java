package com.khoros.twitterapp;

import com.khoros.twitterapp.services.TwitterService;

import dagger.Module;
import dagger.Provides;
import twitter4j.Twitter;
import javax.inject.Singleton;

@Module
class ServiceProviderModule {

    @Provides @Singleton
    TwitterService provideTwitterService(Twitter twFactory) {
            return new TwitterService(twFactory);
    }

}
