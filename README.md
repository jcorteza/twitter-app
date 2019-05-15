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
* Initialize your Maven project by following the [Creating a Project Maven insructions](https://maven.apache.org/guides/getting-started/maven-in-five-minutes.html). Make sure the groupId is com.khoros.twitterapp and the artifactId is TwitterApp.
* Copy your twitter4j.properties file into a new 'resources' directory in the TwiterApp/src/main directory.
### Setting up Dropwizard
* Add a file 'configuration.yml' to your root directory and add the following text to the file and save it:
  ```
  server:
    type: simple
    applicationContextPath: /api/1.0/twitter/
    connector:
      type: http
      port: 8080
  ```
* Make sure you have an updated pom.xml file that includes dropwizard as a dependency. You can use [Dropwizard's Tutorial](https://www.dropwizard.io/1.3.9/docs/getting-started.html#tutorial) to get maven set up with Dropwizard.
* Once your pom.xml file is updated, you can reimport your project with Maven in IntelliJ.
  * Right click on your pom.xml file in IntelliJ.
  * Hover over "Maven" until the submenu opens.
  * Click on reimport.
  * Give your library a couple of minutes to update.
* Dropwizard should now be integrated into your Maven project.
### Compile, Package, and Run
* Compile and package your Maven project by running the following command in the command line: `mvn package`
* There should now be a new 'target' directory with two JAR files in it.
* To run your fat-JAR file and start the Dropwizard server use the following command:
  ```
  java -jar target/TwitterApp-1.0-SNAPSHOT-launcher.jar server configuration.yml
  ```
  * [Dropwizard Reference—Runing Your Application](https://www.dropwizard.io/1.3.9/docs/getting-started.html#running-your-application)
* Your server should now be listening to client requests.
#### Making Client Requests
* You can use `curl` to make a POST request to [http://localhost:8080/api/1.0/twitter/tweet](http://localhost:8080/api/1.0/twitter/tweet) as such:
  ```
  curl -X POST --data-urlencode message="<your message>" http://localhost:8080/api/1.0/twitter/tweet
  ```
* To make GET request to [http://localhost:8080/api/1.0/twitter/timeline](http://localhost:8080/api/1.0/twitter/timeline) you can use curl or the browser:
  * Using curl:
    ```
    curl -X GET http://localhost:8080/api/1.0/twitter/timeline
    ```
  * Using the browser: Open [http://localhost:8080/api/1.0/twitter/timeline](http://localhost:8080/api/1.0/twitter/timeline) in the browser of your choice.
## Testing the Application
### Generating a Test Coverage Report
* Type the following command into the command line to generate a test coverage report: `mvn jacoco:report verify`
* To view the report open the index.html file (located in your `target/site/jacoco/`) in your browser.
