package communication.rabbitmq.handler;

import com.rekeningrijden.europe.dtos.SubInvoiceDto;
import domain.Invoice;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import javax.ejb.Stateless;
import javax.enterprise.event.Observes;

@Stateless
public class ExternalInvoiceHandler {

    public void receiveExternalInvoice(@Observes SubInvoiceDto dto) {
        throw new NotImplementedException();
    }
}
