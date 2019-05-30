package com.khoros.twitterapp;

import dagger.Module;
import dagger.Provides;
import twitter4j.Twitter;

@Module
class TwitterFactoryModule {
    private Twitter twFactory;

    TwitterFactoryModule(Twitter twFactory) {
        this.twFactory = twFactory;
    }

    @Provides Twitter provideTwitterFactory() {
        return twFactory;
    }

}
