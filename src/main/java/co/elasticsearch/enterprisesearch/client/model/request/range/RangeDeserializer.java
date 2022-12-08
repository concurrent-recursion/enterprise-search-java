package co.elasticsearch.enterprisesearch.client.model.request.range;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.node.NumericNode;

import java.io.IOException;

public class RangeDeserializer extends StdDeserializer<Range> {
    protected RangeDeserializer(){
        super(Range.class);
    }

    @Override
    public Range deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        TreeNode node = jsonParser.readValueAsTree();
        TreeNode to = node.get("to");
        TreeNode from = node.get("from");
        if(to instanceof NumericNode || from instanceof NumericNode){
            return jsonParser.getCodec().treeToValue(node, NumberRange.class);
        }else{
            return jsonParser.getCodec().treeToValue(node, DateRange.class);
        }
    }
}
