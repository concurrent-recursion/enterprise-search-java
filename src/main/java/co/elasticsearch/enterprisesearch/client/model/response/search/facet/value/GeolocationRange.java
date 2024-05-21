package co.elasticsearch.enterprisesearch.client.model.response.search.facet.value;

import co.elasticsearch.enterprisesearch.client.model.Geolocation;

public class GeolocationRange implements FacetRangeValue<Geolocation>{

    @Override
    public Geolocation getFrom() {
        return null;
    }

    @Override
    public Geolocation getTo() {
        return null;
    }

    @Override
    public Long getCount() {
        return null;
    }
}
