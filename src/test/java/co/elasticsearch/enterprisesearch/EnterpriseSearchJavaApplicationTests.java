package co.elasticsearch.enterprisesearch;

import co.elasticsearch.enterprisesearch.client.model.*;
import co.elasticsearch.enterprisesearch.client.model.request.*;
import co.elasticsearch.enterprisesearch.client.model.request.Range;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
class EnterpriseSearchJavaApplicationTests {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeAll
    static void setup(){
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
        objectMapper.registerModule(new JavaTimeModule());
    }

    @Test
    void JsonSerializeTest() throws JsonProcessingException {
        Map<String,List<Facet>> facets = new HashMap<>();
        facets.put("states",List.of(new ValueFacet().setName("top-ten-states").setSize(10).setSort(new Sort("count","desc"))));
        facets.put("acres",List.of( new SearchRangeFacet().setName("min-max-range").setRanges(List.of(new NumberRange().setFrom(1L).setTo(10000L),new NumberRange().setFrom(10000L )))));
        facets.put("date_established",List.of(new SearchRangeFacet().setName("half-century").setRanges(List.of(new DateRange().setFrom(OffsetDateTime.parse("1900-01-01T12:00:00+00:00")).setTo(OffsetDateTime.parse("1950-01-01T00:00:00+00:00"))))));
        facets.put("location",List.of(new SearchRangeFacet().setName("geo-range-from-sanfrancisco").setCenter(new GeoLocation(new BigDecimal("37.386483"),new BigDecimal("-122.083842"))).setUnit("m")
                .setRanges(List.of(new NumberRange().setFrom(0).setTo(100000).setName("Nearby"),new NumberRange().setFrom(100000).setTo(300000).setName("A longer drive."),new NumberRange().setFrom(300000).setName("Perhaps fly?")))));
        SearchApiRequest request = new SearchApiRequest()
                .setSort(List.of(new Sort("score", "asc"), new Sort("home", new GeoLocationSort(new BigDecimal("-54.123"), new BigDecimal("23.002")).setOrder("desc"))))
                .setPage(new Page().setCurrent(1).setSize(20))
                .setGroup(new Group().setField("abc").setSize(3))
                .setQuery("Stuff")
                .setAnalytics(new Analytics().addTag("yourmom").addTag("joemamma"))
                .setFacets(facets)
                .addValueFilter("world_heritage_site","true")
                .addValueFilter("states","California","Alaska")
                ;
        String json = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(request);
        log.info("Parsed \n{}",json);

    }

    @Test
    @Disabled
    void JsonDeserializeTest() throws Exception{
        Path path = Paths.get("src/test/resources/examples/search-request-1.json");
        try(InputStream is = Files.newInputStream(path, StandardOpenOption.READ)){
            SearchApiRequest request = objectMapper.readValue(is,SearchApiRequest.class);
            Assertions.assertNotNull(request,"SearchApiRequest should not be null");
        }
    }

}
