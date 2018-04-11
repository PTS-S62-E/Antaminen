# Antaminen
Antaminen is the administration application for the Finnish government.

```Antaminen is Finnish for administration. Google it ;)```

## Running the application
This application uses so libraries that are hosted on a private maven repository.
Please see [Osake readme](https://github.com/PTS-S62-E/Osake#artifactory-repository) on how to configure your local maven installation.

## deploying the application
There're several ways to deploy this application. Please see below topics for different configuratuins

#### IntelliJ Idea
When you have a Wildfly installation installed on your local machine, you can create a new Application Server in IntelliJ.
More info about this on [the Jetbrains website](https://www.jetbrains.com/help/idea/configuring-and-managing-application-server-integration.html).

#### Deploying to a wildfly application server
When testing the application, you probably want your new functionality available for others.
To deploy to a server, we can specify multiple maven goals with the required params.

For example:

```text
mvn clean install wildfly:deploy -Dwildfly.username=admin -Dwildfly.password=proftaak -Dwildfly.port=9990 -Dwildfly.hostname=localhost

```

- wildfly:deploy wil alson invoke the maven goal "package", generating the war file
- -Dwildfy.username (required): the username of the user that has access to the admin console
- -Dwildfly.password (required): the password of the user that has access to the admin console
- -Dwildfly.port (optional, default: 9990): the port where the admin console listens on
- -Dwildfly.hostname (optional, default: localhost): the host where you want to deploy to.
 Dit is voor sop
