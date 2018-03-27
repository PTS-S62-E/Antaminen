package util;

import io.sentry.Sentry;
import io.sentry.SentryClient;
import io.sentry.SentryClientFactory;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;

@Startup
@Singleton
public class SetupSentry {

    private static SentryClient sentry;

    @PostConstruct
    public void startup() {
        Sentry.init("http://8929efda9f724aa8976c86537437484a:8092ef1438554ba3925c8dc9a5342ea8@85.144.215.28:9002/6");

        sentry = SentryClientFactory.sentryClient();

        Sentry.capture("This is a test event");
    }
}
