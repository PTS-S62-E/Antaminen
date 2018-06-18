package communication.rabbitmq.handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pts62.common.finland.communication.CommunicationBuilder;
import com.pts62.common.finland.communication.QueueConfig;
import com.pts62.common.finland.communication.QueueConnector;
import domain.Owner;
import domain.Ownership;
import dto.AdministrationDto;
import dto.ForeignVehicleDto;
import dto.TranslocationRequesterDto;
import exceptions.InvoiceException;
import exceptions.OwnerException;
import io.sentry.Sentry;
import service.InvoiceService;
import service.OwnerService;
import util.LocalDateUtil;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.enterprise.event.Event;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import java.nio.charset.Charset;
import java.util.logging.Logger;

@Stateless
public class TranslocationRequesterHandler {

    @EJB
    private InvoiceService invoiceService;

    @EJB
    private OwnerService ownerService;

    public void receiveEvent(@Observes AdministrationDto data) {
        try {
            invoiceService.generateSingleInvoice(data);
        } catch (InvoiceException e) {
            Sentry.capture(e);
        }
    }

    public void receiveEvent(@Observes ForeignVehicleDto data) {
        invoiceService.generateSingleForeignInvoice(data);
    }

    public void requestTranslocations(boolean isForeign) {
        Logger.getLogger(getClass().getName()).warning("RequestTranslocations called");
        QueueConnector connector = new QueueConnector(new QueueConfig("192.168.24.100", "REKENINGRIJDEN_EXCHANGE", Charset.defaultCharset()));
        CommunicationBuilder builder = new CommunicationBuilder();
        builder.setCountry("fi");
        builder.setApplication("registration");
        builder.setMessage("translocations.request");
        ObjectMapper mapper = new ObjectMapper();

        if(isForeign) {
            // Request translocations for foreign cars
            TranslocationRequesterDto dto = new TranslocationRequesterDto(LocalDateUtil.getCurrentLocalDate(), LocalDateUtil.getCurrentLocalDate().minusMonths(1), isForeign, 0);
            try {
                String data = mapper.writeValueAsString(dto);
                connector.publishMessage(builder.build(), data);
            } catch (JsonProcessingException e) {
                Sentry.capture(e);
            }
        } else {
            // Request translocation for internal cars
            try {
                for(Owner owner : this.ownerService.getAllOwners()) {
                    for(Ownership ownership : owner.getOwnership()) {
                        TranslocationRequesterDto dto = new TranslocationRequesterDto(LocalDateUtil.getCurrentLocalDate(), LocalDateUtil.getCurrentLocalDate().minusMonths(1), false, ownership.getVehicleId());

                        String data = mapper.writeValueAsString(dto);
                        connector.publishMessage(builder.build(), data);
                    }
                }
            } catch (OwnerException | JsonProcessingException e) {
                Sentry.capture(e);
            }
        }
    }
}
