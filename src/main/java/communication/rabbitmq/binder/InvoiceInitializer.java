package communication.rabbitmq.binder;

import io.sentry.Sentry;
import io.sentry.event.BreadcrumbBuilder;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;
import java.io.IOException;

@Startup
@Singleton
public class InvoiceInitializer {

    @Inject
    private InvoiceBinder binder;

    @PostConstruct
    public void init() {
        try {
            binder.configuration()
                    .addHost("192.168.24.100");
            binder.initialize();
        } catch (IOException e) {
            Sentry.getContext().recordBreadcrumb(new BreadcrumbBuilder().setMessage("Unable to initizalize RabbitMQ queue to receive external invoices").build());
            Sentry.capture(e);
        }
    }
}
