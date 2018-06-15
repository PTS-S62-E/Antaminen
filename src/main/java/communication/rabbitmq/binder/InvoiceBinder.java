package communication.rabbitmq.binder;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rekeningrijden.europe.dtos.SubInvoiceDto;
import io.sentry.Sentry;
import io.sentry.event.BreadcrumbBuilder;
import net.reini.rabbitmq.cdi.DecodeException;
import net.reini.rabbitmq.cdi.Decoder;
import net.reini.rabbitmq.cdi.EventBinder;

import javax.enterprise.context.Dependent;
import java.io.IOException;

@Dependent
public class InvoiceBinder extends EventBinder {
    @Override
    protected void bindEvents() {
        bind(SubInvoiceDto.class)
                .toQueue("rekeningrijden.invoices")
                .withDecoder(new Decoder<SubInvoiceDto>() {
                    @Override
                    public SubInvoiceDto decode(byte[] bytes) throws DecodeException {
                        String data = new String(bytes);

                        ObjectMapper mapper = new ObjectMapper();
                        try {
                            return mapper.readValue(data, SubInvoiceDto.class);
                        } catch (IOException e) {
                            Sentry.getContext().recordBreadcrumb(new BreadcrumbBuilder().setMessage("Unable to parse external invoice message that has been received").build());
                            Sentry.capture(e);
                        }

                        return null;
                    }

                    @Override
                    public boolean willDecode(String s) {
                        return s.equals("text/plain") || s.equals("application/json");
                    }
                });
    }
}
