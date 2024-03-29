package co.elasticsearch.enterprisesearch.client.model.request.search.filter;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.util.List;

/**
 * Represents a search filter
 */
@JsonDeserialize(using = FilterDeserializer.class)
@JsonSerialize(using = FilterSerializer.class)
public interface Filter {



}
