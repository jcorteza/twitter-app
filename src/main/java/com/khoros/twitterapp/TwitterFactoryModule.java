package com.khoros.twitterapp;

import dagger.Module;
import dagger.Provides;
import dagger.Reusable;
import twitter4j.Twitter;

@Module
class TwitterFactoryModule {
    private Twitter twFactory;

    TwitterFactoryModule(Twitter twFactory) {
        this.twFactory = twFactory;
    }

    @Provides @Reusable Twitter provideTwitterFactory() {
        return twFactory;
    }

}
