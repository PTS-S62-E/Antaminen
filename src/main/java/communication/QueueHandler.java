package communication;



import com.pts62.common.finland.communication.CommunicationBuilder;
import com.pts62.common.finland.communication.IQueueSubscribeCallback;
import com.pts62.common.finland.communication.QueueConfig;
import com.pts62.common.finland.communication.QueueConnector;
import communication.rabbitmq.handler.TranslocationRequesterHandler;
import exceptions.InvoiceException;
import io.sentry.Sentry;
import io.sentry.event.Breadcrumb;
import io.sentry.event.BreadcrumbBuilder;
import service.InvoiceService;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;
import javax.jms.QueueConnectionFactory;
import java.nio.charset.Charset;
import java.util.logging.Logger;

@Startup
@Singleton
public class QueueHandler  {

    @EJB
    InvoiceService invoiceService;

    @Inject
    private TranslocationRequesterHandler translocationRequesterHandler;

    private QueueConnector connector;

    @PostConstruct
    private void setup() {
        connector = new QueueConnector(new QueueConfig("192.168.24.100", "TEST_EXCHANGE", Charset.defaultCharset()));

        this.handleRead();
    }

    private void handleRead() {
        CommunicationBuilder invoiceBuilder = new CommunicationBuilder();
        invoiceBuilder.setCountry("fi");
        invoiceBuilder.setApplication("antaminen");
        invoiceBuilder.setMessage("generate.invoices");

        CommunicationBuilder comBuilder = new CommunicationBuilder();
        comBuilder.setCountry("fi");
        comBuilder.setApplication("antaminen");
        comBuilder.setMessage("*");

        connector.readMessage(invoiceBuilder.build(), new IQueueSubscribeCallback() {
            @Override
            public void onMessageReceived(String s) {
                Logger.getLogger(getClass().getName()).warning("Request to generate invoices received");
                translocationRequesterHandler.requestTranslocations(true); // First we request translocations for all cars from foreign countries
//                translocationRequesterHandler.requestTranslocations(false); // Then we request translocations for all cars from our own nation
            }
        });

        connector.readMessage(comBuilder.build(), new IQueueSubscribeCallback() {
            @Override
            public void onMessageReceived(String s) {
                Exception e = new Exception("Unable to process MQ request for specific route with data " + s);
                Sentry.getContext().recordBreadcrumb( new BreadcrumbBuilder().setMessage(s).build());
                Sentry.capture(e);
            }
        });
    }
}
