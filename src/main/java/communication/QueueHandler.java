package communication;

import com.fasterxml.jackson.databind.JsonNode;
import com.pts62.common.finland.communication.IQueueSubscribeCallback;
import io.sentry.Sentry;
import service.InvoiceService;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Startup;

@Startup
public class QueueHandler  implements IQueueSubscribeCallback{

    @EJB
    InvoiceService invoiceService;

    @PostConstruct
    public void setupCommunicationQueue() {
        new QueueHandler(com.pts62.common.finland.communication.QueueConstants.getAntaMinenQueue());
    }

    public QueueHandler(String queue) {
        new com.pts62.common.finland.communication.QueueConnector(queue);
    }

    @Override
    public void onMessageReceived(Object o) {
        try {
            JsonNode data = (JsonNode) o;

            if(data.has("message") && data.get("message").has("invoice") && data.get("message").get("invoice").has("generate")) {
                boolean startInvoiceGeneration = data.get("message").get("invoice").get("generate").asBoolean();

                if(startInvoiceGeneration) {
                    invoiceService.generateInvoices();
                }
            } else {
                throw new Exception("Unable to parse received message");
            }
        } catch (Exception e) {
            Sentry.capture(e);
            Sentry.getStoredClient().addExtra("MessageQueueMessage", o);
        }

    }
}
