package com.khoros.twitterapp;

import dagger.Module;
import dagger.Provides;
import twitter4j.conf.Configuration;
import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

@Module
class TwitterFactoryModule {
    private Configuration twConf;

    TwitterFactoryModule(ConfigurationBuilder twConfBuilder) {
        this.twConf = twConfBuilder.build();
    }

    @Provides
    Twitter provideTwitterFactory() {
        return new TwitterFactory(twConf).getInstance();
    }

}
