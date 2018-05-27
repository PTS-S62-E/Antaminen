package communication;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pts62.common.finland.communication.CommunicationBuilder;
import com.pts62.common.finland.communication.QueueConnector;
import com.rekeningrijden.europe.dtos.SubInvoiceDto;
import io.sentry.Sentry;

public class QueueMessageSender {

    private static QueueMessageSender _instance;
    private QueueConnector connector;
    private CommunicationBuilder builder;

    private QueueMessageSender() {
        _instance = this;

        this.connector = new QueueConnector();
        this.builder = new CommunicationBuilder();
    }

    public static QueueMessageSender getInstance() {
        if(_instance == null) {
            new QueueMessageSender();
        }

        return _instance;
    }

    /**
     * Send an SubInvoiceDto (invoice) to the correct country
     * @param invoice {@link SubInvoiceDto SubInvoiceDto} object containing the required information
     *                                                   to send the invoice to the correct country
     */
    public void sendInvoiceToForeignCountry(SubInvoiceDto invoice) {

        //TODO: Setup the message queue correctly so that the actual invoice will be sent

        this.builder.setCountry(invoice.getCountry());
        this.builder.setApplication("PLEASE_REPLACE_THIS");
        this.builder.setMessage("PLEASE_REPLACE_THIS");

        ObjectMapper mapper = new ObjectMapper();
        try {
            String invoiceAsJsonString = mapper.writeValueAsString(invoice);
//            this.connector.publishMessage(this.builder.build(), invoiceAsJsonString);
        } catch (JsonProcessingException e) {
            Sentry.capture(e);
        }
    }
}
