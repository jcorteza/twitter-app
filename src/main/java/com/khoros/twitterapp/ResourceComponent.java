package com.khoros.twitterapp;

import com.khoros.twitterapp.resources.MainResource;
import dagger.Component;
import javax.inject.Singleton;

@Component(
        dependencies = TwitterFactoryComponent.class,
        modules = ServiceProviderModule.class
)
@Singleton
public interface ResourceComponent {
    MainResource mainResource();
}
