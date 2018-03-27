package util.Deserializers;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import domain.Invoice;
import domain.InvoiceDetails;

import java.io.IOException;

public class InvoiceDeserializer extends JsonDeserializer<Invoice> {
    @Override
    public Invoice deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        ObjectMapper mapper = (ObjectMapper) jsonParser.getCodec();
        ObjectNode root = mapper.readTree(jsonParser);
        /*write you own condition*/
        return mapper.readValue(root.toString(), Invoice.class);
    }
}
