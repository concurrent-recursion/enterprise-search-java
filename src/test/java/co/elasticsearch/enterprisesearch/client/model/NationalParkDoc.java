package co.elasticsearch.enterprisesearch.client.model;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

@Getter
@Setter
@Accessors(chain = true)
public class NationalParkDoc {
    private String id;
    private Long visitors;
    private BigDecimal squareKm;
    private Boolean worldHeritageSite;
    private OffsetDateTime dateEstablished;
    private String description;
    private String location;
    private BigDecimal acres;
    private String title;
    private String npsLink;
    private List<String> states;
}
