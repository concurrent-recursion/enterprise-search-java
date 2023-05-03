package co.elasticsearch.enterprisesearch.client.model.response.search.facet.value;

public interface FacetRangeValue<T> extends FacetValue {
    T getFrom();

    T getTo();

}
