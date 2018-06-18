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
public class RabbitMqBinderInitializer {

    @Inject
    private InvoiceBinder invoiceBinder;

    @Inject
    private TranslocationRequesterBinder translocationRequesterBinder;

    @PostConstruct
    public void init() {
        try {
            invoiceBinder.configuration()
                    .addHost("192.168.24.100");
            invoiceBinder.initialize();
        } catch (IOException e) {
            Sentry.getContext().recordBreadcrumb(new BreadcrumbBuilder().setMessage("Unable to initialize RabbitMQ queue to receive external invoices").build());
            Sentry.capture(e);
        }

        try {
            translocationRequesterBinder.configuration()
                    .addHost("192.168.24.100:5672")
                    .setUsername("proftaak")
                    .setPassword("proftaak")
                    .setVirtualHost("/");
            translocationRequesterBinder.initialize();
        } catch (IOException e) {
            Sentry.getContext().recordBreadcrumb(new BreadcrumbBuilder().setMessage("Unable to initialize RabbitMQ queue to receive translocations").build());
            Sentry.capture(e);
        }
    }
}
