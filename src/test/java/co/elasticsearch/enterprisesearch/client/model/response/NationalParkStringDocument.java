package co.elasticsearch.enterprisesearch.client.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;

@Data
@Accessors(chain = true)

public class NationalParkStringDocument {
    private String description;
    @JsonProperty("nps_link")
    private String npsLink;
    private List<String> states = new ArrayList<>();
    private String title;
    private String visitors;
    @JsonProperty("world_heritage_site")
    private String worldHeritageSite;
    private String location;
    private String acres;
    @JsonProperty("square_km")
    private String squareKm;
    @JsonProperty("date_established")
    private String dateEstablished;
    private String id;
}
