# Simple Twitter Application
Clone this repo to have your very own CLI Twitter Application that will allow you to post status updates and check your personal Twitter feed from the comfort of your command line.

---

**Note:** This application runs with Java, so Java must be installed on your computer. ([Java Download Resources](https://docs.oracle.com/cd/E19182-01/820-7851/inst_cli_jdk_javahome_t/))

---

## Steps To Get Up and Running
### Set Up App Configuration
* Create a Twitter account, if you don't have one already, by visiting the [Twitter Sign Up page](https://twitter.com/i/flow/signup) and filling out the form.
* Log in to Twitter.
* Visit the [Twitter Developers page](https://developer.twitter.com/content/developer-twitter/en.html). In the dropdown menu under your Twitter handle select [Apps](https://developer.twitter.com/en/apps).
* On the new page that loads, click on the blue [Create an app](https://developer.twitter.com/en/apps/create) button and fill out the necessary details to receive your API keys.
* Once your app has been set up, go back to the [Apps page](https://developer.twitter.com/en/apps) and click on the Details button of your new application.
* Click on the Permissions tab on the new page that loads. Click the blue Edit button, and make sure your Access permission is set to "Read and write."
* Now select the "Keys and tokens" tab of your application. Copy the Consumer API keys and access tokens into a new file "twitter4j.properties," which should be located in the same directory as the cloned git repo.
* The format of your "twitter4j.properties" file should follow the format shown in the [twitter4J Configuration documentation](http://twitter4j.org/en/configuration.html) as shown below:
![example twitter4j.properties file](https://github.com/jcorteza/twitter-app/blob/master/twitter4j-config.png)
### Compile Source Files
* To compile the .java file and create your .class file run the following command:
```
javac -cp resources/libraries/twitter4j-core-4.0.7.jar src/com/khoros/twitterapp/TwitterApp.java
```
### Set Up JAR File
* Click the green "Clone or download" button in the repo, copy the [link](https://github.com/jcorteza/twitter-app.git), and `git clone <the git clone link>` in a directory of your choosing.
* Create a JAR File
```
jar cfm <name of the jar file>.jar resources/manifest-info.txt -C src .
``` 
### Run the Application
* Run the "Tweet" feauture with the following command: `java -jar twitter-app.jar tweet <your tweet text>`. If no further arguments are entered after `tweet`, The default tweet text is "Hello Twitter followers!".
* Run the "Check Feed" feature with the following command: `java -jar twitter-app.jar check_feed`
