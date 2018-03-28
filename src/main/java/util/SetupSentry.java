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
        Sentry.init();

        sentry = SentryClientFactory.sentryClient();

        Sentry.capture("This is a test event");
    }
}
