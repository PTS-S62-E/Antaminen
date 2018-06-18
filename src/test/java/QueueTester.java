import com.pts62.common.finland.communication.CommunicationBuilder;
import com.pts62.common.finland.communication.QueueConfig;
import com.pts62.common.finland.communication.QueueConnector;

import javax.jms.QueueConnection;
import java.nio.charset.Charset;
import java.util.logging.Logger;

public class QueueTester {

    public static void main(String args[]) throws Exception {
        CommunicationBuilder builder = new CommunicationBuilder();
        QueueConnector connector = new QueueConnector(new QueueConfig("192.168.24.100", "TEST_EXCHANGE", Charset.defaultCharset()));

        builder.setCountry("fi");
        builder.setApplication("antaminen");
        builder.setMessage("generate.invoices");

        Logger.getLogger(QueueTester.class.getName()).warning(builder.build());

        connector.publishMessage(builder.build(), "true");

        System.exit(1);
    }
}
