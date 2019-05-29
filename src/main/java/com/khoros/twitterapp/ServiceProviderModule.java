package com.khoros.twitterapp;


import com.khoros.twitterapp.services.TwitterService;
import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;

@Module
class ServiceProviderModule {
    @Provides @Singleton
    TwitterService provideTwitterService() {
            return TwitterService.getInstance();
    }

}
