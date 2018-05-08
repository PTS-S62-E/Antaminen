package communication;



import com.pts62.common.finland.communication.QueueConnector;
import com.pts62.common.finland.communication.QueueConstants;

import javax.annotation.PostConstruct;
import javax.ejb.Startup;

@Startup
public class QueueHandler  {

    private QueueConnector connector;

    @PostConstruct
    private void setup() {
        connector = new QueueConnector();

    }

    public void test() {

    }
}
