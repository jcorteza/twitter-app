package com.khoros.twitterapp;

import dagger.Module;
import dagger.Provides;
import twitter4j.conf.Configuration;
import twitter4j.Twitter;
import twitter4j.TwitterFactory;

@Module
class TwitterFactoryModule {
    private Configuration twConf;

    TwitterFactoryModule(Configuration twConf) {
        this.twConf = twConf;
    }

    @Provides Twitter provideTwitterFactory() {
        return new TwitterFactory(twConf).getInstance();
    }

}
