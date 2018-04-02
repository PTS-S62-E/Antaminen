package util;

import io.sentry.Sentry;
import io.sentry.SentryClient;
import io.sentry.SentryClientFactory;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.ws.rs.ext.Provider;

@Startup
@Singleton
public class SetupSentry {

    @PostConstruct
    public void setupSentry() {
        // Let sentry look for the properties file with the required information
        Sentry.init();
    }
}
