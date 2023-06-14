package co.elasticsearch.enterprisesearch.client.model;

import co.elasticsearch.enterprisesearch.client.model.response.search.*;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class NationalParkSearchDoc extends ResponseDocument {
    private TextField id;
    private NumberField visitors;
    private NumberField squareKm;
    private TextField worldHeritageSite;
    private DateField dateEstablished;
    private TextField description;
    private GeolocationField location;
    private NumberField acres;
    private TextField title;
    private TextField npsLink;
    private TextArrayField states;
}
