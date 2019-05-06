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
### Setting Up Maven Project
* [Install Maven](https://maven.apache.org/install.html) if it's not already installed on your computer.
* Initialize your Maven project by following the [Creating a Project Maven insructions](https://maven.apache.org/guides/getting-started/maven-in-five-minutes.html). Make sure the groupId matches your package name and the artifactId matches the name for your application.
* Copy your twitter4j.properties file into a new 'resources' directory in the <my-app>/src/main directory.
### Compile, Package, and Run
* `cd` into your new <my-app> directory.
* Compile the maven src files into a 'target' directory with the following command: `mvn compile`
* Copy your dependencies into the target directory and package the maven src files into a JAR file with the following command: `mvn dependency:copy-dependencies package`
* You should now be able to run your application with the following command: `java -jar /target/<my-app>-1.0-SNAPSHOT.jar [options]`
#### Options
* To post a new tweet enter `tweet` as an option and follow it with the new tweet text. Ex:
  ```
  java -jar target/<my-app>-1.0-SNAPSHOT.jar tweet Hello Twitter followers!
  ```
  If you do not enter tweet text the application default is to post "Hello Twitter followers!"
* To check Twitter feed, enter `check_feed` as the option. Ex:
  ```
  java -jar target/<my-app>-1.0-SNAPSHOT.jar check_feed
  ```
