package communication;



import com.pts62.common.finland.communication.CommunicationBuilder;
import com.pts62.common.finland.communication.IQueueSubscribeCallback;
import com.pts62.common.finland.communication.QueueConnector;
import com.pts62.common.finland.communication.QueueConstants;
import exceptions.InvoiceException;
import io.sentry.Sentry;
import io.sentry.event.Breadcrumb;
import io.sentry.event.BreadcrumbBuilder;
import service.InvoiceService;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.jms.QueueConnectionFactory;
import java.util.logging.Logger;

@Startup
@Singleton
public class QueueHandler  {

    @EJB
    InvoiceService invoiceService;

    private QueueConnector connector;

    @PostConstruct
    private void setup() {
        connector = new QueueConnector();

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

                /**
                 * First we start generating invoices for drivers in finland.
                 */

//                    try {
//                        invoiceService.generateInvoices();
//                    } catch (InvoiceException e) {
//                        Sentry.getContext().recordBreadcrumb(new BreadcrumbBuilder().setMessage("Error in generating invoices for country: Finland").build());
//                        Sentry.capture(e);
//                    }

                /**
                 * Invoices for drivers in finland are generated now. We can continue with generating invoices for foreign drivers
                 */

                try {
                    /**
                     * This method will also call {@link invoiceService#generateInvoices generateInvoice} so we can comment out the code above
                     */
                    invoiceService.generateInvoicesForVehiclesOfForeignCountries();
                } catch (InvoiceException e) {
                    Sentry.getContext().recordBreadcrumb(new BreadcrumbBuilder().setMessage("Error in generating invoices for foreign countries").build());
                    Sentry.capture(e);
                }
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
