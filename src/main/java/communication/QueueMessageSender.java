package communication;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pts62.common.finland.communication.CommunicationBuilder;
import com.pts62.common.finland.communication.QueueConfig;
import com.pts62.common.finland.communication.QueueConnector;
import com.rekeningrijden.europe.dtos.SubInvoiceDto;
import io.sentry.Sentry;
import io.sentry.event.BreadcrumbBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.Properties;

public class QueueMessageSender {

    private static QueueMessageSender _instance;
    private QueueConnector connector;
    private CommunicationBuilder builder;

    private Properties properties;

    private QueueMessageSender() {
        _instance = this;

        this.builder = new CommunicationBuilder();

        InputStream input = null;
        properties = new Properties();

        try {
            input = getClass().getClassLoader().getResourceAsStream("countries.properties");
            properties.load(input);
        } catch (IOException e) {
            Sentry.getContext().recordBreadcrumb(new BreadcrumbBuilder().setMessage("Unable to load property file for countries to retrieve IP addresses.").build());
            Sentry.capture(e);
        } finally {
            if(input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    Sentry.capture(e);
                }
            }
        }
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

        String countryIp = properties.getProperty(invoice.getCountry());

        this.connector = new QueueConnector(new QueueConfig(countryIp, "", Charset.defaultCharset()));
        this.builder.setApplication("rekeningrijden");
        this.builder.setMessage("invoices");

        ObjectMapper mapper = new ObjectMapper();
        try {
            String invoiceAsJsonString = mapper.writeValueAsString(invoice);
            this.connector.publishMessage(this.builder.build(), invoiceAsJsonString);
        } catch (JsonProcessingException e) {
            Sentry.capture(e);
        }
    }
}
