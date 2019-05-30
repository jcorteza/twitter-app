package com.khoros.twitterapp;


import com.khoros.twitterapp.services.TwitterService;
import dagger.Module;
import dagger.Provides;

@Module
class ServiceProviderModule {
    @Provides
    TwitterService provideTwitterService() {
            return TwitterService.getInstance();
    }

}
