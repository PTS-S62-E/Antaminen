package communication.rabbitmq.binder;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.AMQP;
import dto.AdministrationDto;
import dto.ForeignVehicleDto;
import dto.TranslocationRequesterDto;
import io.sentry.Sentry;
import net.reini.rabbitmq.cdi.*;

import javax.enterprise.context.Dependent;
import java.io.IOException;

@Dependent
public class TranslocationRequesterBinder extends EventBinder {
    @Override
    protected void bindEvents() {
        bind(TranslocationRequesterDto.class)
                .toExchange("TEST_EXCHANGE")
                .withRoutingKey("fi.registration.translocations.request")
                .withEncoder(new Encoder<TranslocationRequesterDto>() {
                    @Override
                    public byte[] encode(TranslocationRequesterDto translocationRequesterDto) throws EncodeException {
                        ObjectMapper mapper = new ObjectMapper();
                        try {
                            String data = mapper.writeValueAsString(translocationRequesterDto);
                            return data.getBytes();
                        } catch (JsonProcessingException e) {
                            Sentry.capture(e);
                        }

                        return null;
                    }

                    @Override
                    public String contentType() {
                        return "text/plain";
                    }
                });

        bind(ForeignVehicleDto.class)
                .toQueue("fi.antaminen.translocations.receive.foreign")
                .withDecoder(new Decoder<ForeignVehicleDto>() {
                    @Override
                    public ForeignVehicleDto decode(byte[] bytes) throws DecodeException {
                        String data = new String(bytes);

                        ObjectMapper mapper = new ObjectMapper();
                        try {
                            return mapper.readValue(data, ForeignVehicleDto.class);
                        } catch (IOException e) {
                            Sentry.capture(e);
                        }

                        return null;
                    }

                    @Override
                    public boolean willDecode(String s) {
                        return s.equals("text/plain");
                    }
                }).autoAck();

        bind(AdministrationDto.class)
                .toQueue("fi.antaminen.translocations.receive.internal")
                .withDecoder(new Decoder<AdministrationDto>() {
                    @Override
                    public AdministrationDto decode(byte[] bytes) throws DecodeException {
                        String data = new String(bytes);

                        ObjectMapper mapper = new ObjectMapper();

                        try {
                            return mapper.readValue(data, AdministrationDto.class);
                        } catch (IOException e) {
                            Sentry.capture(e);
                        }

                        return null;
                    }

                    @Override
                    public boolean willDecode(String s) {
                        return s.equals("text/plain");
                    }
                }).autoAck();
    }
}
