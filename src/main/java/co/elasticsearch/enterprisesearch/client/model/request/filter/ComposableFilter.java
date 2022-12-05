package co.elasticsearch.enterprisesearch.client.model.request.filter;


import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(using = FilterDeserializer.class)
public interface ComposableFilter extends Filter {
}
