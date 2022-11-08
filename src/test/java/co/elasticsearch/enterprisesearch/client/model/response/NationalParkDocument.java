package co.elasticsearch.enterprisesearch.client.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class NationalParkDocument {
    @JsonProperty("_meta")
    private MetaField meta;
    private TextField id;
    @JsonProperty("date_established")
    private DateField dateEstablished;
    @JsonProperty("square_km")
    private NumberField squareKm;
    private NumberField acres;
    private GeolocationField location;
    @JsonProperty("world_heritage_site")
    private TextField worldHeritageSite;
    private NumberField visitors;
    private TextField title;
    private TextArrayField states;
    @JsonProperty("nps_link")
    private TextField npsLink;
    private TextField description;
}
