package co.elasticsearch.enterprisesearch.client.model.request.search.filter;


import co.elasticsearch.enterprisesearch.client.model.DeserializationUtil;
import co.elasticsearch.enterprisesearch.client.model.GeolocationRange;
import co.elasticsearch.enterprisesearch.client.model.request.search.range.DateRange;
import co.elasticsearch.enterprisesearch.client.model.request.search.range.NumberRange;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.NumericNode;
import com.fasterxml.jackson.databind.node.TextNode;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class FilterDeserializer extends StdDeserializer<Filter> {
    private static final Set<String> NESTED_KEYS = Set.of("all", "any", "none");


    protected FilterDeserializer() {
        super(Filter.class);
    }

    private static List<Filter> parseFilters(JsonParser jsonParser, ArrayNode filterValues) throws JsonProcessingException {
        if (filterValues == null) {
            return Collections.emptyList();
        }
        List<Filter> filters = new ArrayList<>();
        for (JsonNode element : filterValues) {
            filters.add(jsonParser.getCodec().treeToValue(element, Filter.class));
        }
        return filters;
    }

    private static BooleanFilter deserializeNestedFilter(JsonParser jsonParser, TreeNode node) throws JsonProcessingException {
        BooleanFilter filter = new BooleanFilter();

        ArrayNode allNode = (ArrayNode) node.get("all");
        filter.setAll(parseFilters(jsonParser, allNode));

        ArrayNode anyNode = (ArrayNode) node.get("any");
        filter.setAny(parseFilters(jsonParser, anyNode));

        ArrayNode noneNode = (ArrayNode) node.get("none");
        filter.setNone(parseFilters(jsonParser, noneNode));

        return filter;
    }

    private static boolean isNestedFilter(TreeNode node) {
        Iterator<String> fieldNames = node.fieldNames();
        if(!fieldNames.hasNext()){
            return true;//If it's an empty "filters" : {}  , then return BooleanFilter
        }
        while (fieldNames.hasNext()) {
            String fieldName = fieldNames.next();
            if (NESTED_KEYS.contains(fieldName)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Filter deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        TreeNode node = jsonParser.readValueAsTree();

        if (isNestedFilter(node)) {
            return deserializeNestedFilter(jsonParser, node);
        } else {
            Iterator<String> fieldNames = node.fieldNames();
            String filterName = fieldNames.next();
            TreeNode filterNode = node.get(filterName);

            TreeNode to = filterNode.get("to");
            TreeNode from = filterNode.get("from");

            if (filterNode.get("center") != null) {
                //Geolocation Filter
                return new GeolocationFilter(filterName).setRange(jsonParser.getCodec().treeToValue(filterNode, GeolocationRange.class));
            } else if (to instanceof NumericNode || from instanceof NumericNode) {
                //Numeric Range Filter
                return new NumberRangeFilter(filterName).setRange(jsonParser.getCodec().treeToValue(filterNode, NumberRange.class));
            } else if (to instanceof TextNode || from instanceof TextNode) {
                //Date Range Filter
                return new DateRangeFilter(filterName).setRange(jsonParser.getCodec().treeToValue(filterNode, DateRange.class));
            } else {
                //Value Filters
                final JsonNode firstValue = DeserializationUtil.getFirstValue(filterNode);
                final Stream<JsonNode> nodeStream = DeserializationUtil.getNodeStream(filterNode);

                if (firstValue instanceof NumericNode) {
                    //Number Value Filter
                    return new NumberValueFilter(filterName).setValues(nodeStream.map(JsonNode::asText).map(BigDecimal::new).collect(Collectors.toList()));
                } else if (DeserializationUtil.isDate((TextNode) firstValue)) {
                    //Date Value Filter
                    return new DateValueFilter(filterName).setValues(nodeStream.map(JsonNode::asText).map(dateString -> OffsetDateTime.parse(dateString, DateValueFilter.RFC_3339)).collect(Collectors.toList()));
                } else {
                    //Text Value Filter
                    return new TextValueFilter(filterName).setValues(nodeStream.map(JsonNode::asText).collect(Collectors.toList()));
                }

            }
        }
    }


}
