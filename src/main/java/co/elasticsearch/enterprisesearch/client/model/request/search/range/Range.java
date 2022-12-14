package co.elasticsearch.enterprisesearch.client.model.request.search.range;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(using = RangeDeserializer.class)
public interface Range<T> {
    T getFrom();

    T getTo();
}
