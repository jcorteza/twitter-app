package com.khoros.twitterapp;

import com.khoros.twitterapp.models.CacheStatus;
import com.khoros.twitterapp.models.Status;
import com.khoros.twitterapp.models.User;
import com.khoros.twitterapp.services.CacheUp;
import static com.khoros.twitterapp.services.CacheUp.getCacheStatusHashMap;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.Assert;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class TwitterAppTest {

    private HashMap<String, CacheStatus> testCacheMap;
    private HashMap<String, CacheStatus> originalHashMap;
    private Runnable originalRunnable;
    private long testInterval;
    private Date testDate;
    private String testKey1;
    private String testKey2;

    @Before
    public void setup() {


        originalRunnable = CacheUp.cleanCache;
        // 5 minute interval for testing
        testInterval = 300000;
        testDate = new Date();
        long timeDiffFiveMin = 5 * 60 * 1000;
        originalHashMap = CacheUp.getCacheStatusHashMap();
        testCacheMap = new HashMap<>();
        twitter4j.Status testStatus = new Twitter4jStatusImpl();

        User newUser = new User();
        newUser.setTwHandle(testStatus.getUser().getScreenName());
        newUser.setName(testStatus.getUser().getName());
        newUser.setProfileImageUrl(testStatus.getUser().getProfileImageURL());

        Status newStatus = new Status();
        newStatus.setMessage(testStatus.getText());
        newStatus.setUser(newUser);
        newStatus.setCreatedAt(testStatus.getCreatedAt());

        testKey1 = "test1";
        testKey2 = "test2";

        testCacheMap.putIfAbsent(testKey2, new CacheStatus(new Date(testDate.getTime() - timeDiffFiveMin), newStatus));
        testCacheMap.putIfAbsent(testKey1, new CacheStatus(testDate, newStatus));

        CacheUp.setCacheStatusHashMap(testCacheMap);

    }

    @After
    public void resetMock() {

        CacheUp.setCacheStatusHashMap(originalHashMap);
        CacheUp.cleanCache = originalRunnable;

    }

    @Test
    public void cleanCacheTest() {

        CacheUp.cleanCache = new Runnable() {
            @Override
            public void run() {

                Date now = testDate;
                Set<Map.Entry<String, CacheStatus>> cachStatusSet = getCacheStatusHashMap().entrySet();

                for(Iterator<Map.Entry<String, CacheStatus>> i = cachStatusSet.iterator(); i.hasNext();) {

                    Map.Entry<String, CacheStatus> cacheStatusEntry = i.next();
                    long dateCreatedCacheStatus = cacheStatusEntry.getValue().getCacheObjectCreated().getTime();

                    if(now.getTime() - dateCreatedCacheStatus >= testInterval) {
                        i.remove();
                    }

                }
            }
        };

        CacheUp.cleanCache.run();

        Assert.assertTrue(testCacheMap.keySet().contains(testKey1));
        Assert.assertFalse(testCacheMap.keySet().contains(testKey2));
    }
}