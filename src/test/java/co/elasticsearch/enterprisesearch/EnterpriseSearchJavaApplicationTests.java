package co.elasticsearch.enterprisesearch;

import co.elasticsearch.enterprisesearch.client.model.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.OffsetDateTime;
import java.util.List;

@Slf4j
class EnterpriseSearchJavaApplicationTests {
    private final ObjectMapper objectMapper = new ObjectMapper();


    @Test
    void JsonSerializeTest() throws JsonProcessingException {

        SearchApiRequest request = new SearchApiRequest()
                .setSort(List.of(new Sort("score", "asc"), new Sort("home", new GeoLocationSort(new BigDecimal("-54.123"), new BigDecimal("23.002")).setOrder("desc"))))
                .setPage(new Page().setCurrent(1).setSize(20))
                .setGroup(new Group().setField("abc").setSize(3))
                .setQuery("Stuff")
                .setAnalytics(new Analytics().addTag("yourmom").addTag("joemamma"))
                .addFacet("states",new SearchFacet().setName("top-ten-states").setType("value").setSize(10).setSort(new Sort("count","desc")))
                .addFacet("acres", new SearchRangeFacet().setName("min-max-range").setRanges(List.of(new Range().setFrom(1).setTo(10000 ),new Range().setFrom(10000 ))))
                .addFacet("date_established",new SearchRangeFacet().setName("half-century").setRanges(List.of(new Range().setFrom(OffsetDateTime.parse("1900-01-01T12:00:00+00:00")).setTo(OffsetDateTime.parse("1950-01-01T00:00:00+00:00")))))
                .addFacet("location",new SearchRangeFacet().setName("geo-range-from-sanfrancisco").setCenter(new GeoLocation(new BigDecimal("37.386483"),new BigDecimal("-122.083842"))).setUnit("m")
                        .setRanges(List.of(new Range().setFrom(0).setTo(100000).setName("Nearby"),new Range().setFrom(100000).setTo(300000).setName("A longer drive."),new Range().setFrom(300000).setName("Perhaps fly?"))))
                .addFilter("world_heritage_site","true")
                .addFilter("states","California","Alaska")
                ;
        String json = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(request);
        log.info("Parsed \n{}",json);

    }

    @Test
    void JsonDeserializeTest() throws Exception{
        Path path = Paths.get("src/test/resources/examples/search-request-1.json");
        try(InputStream is = Files.newInputStream(path, StandardOpenOption.READ)){
            SearchApiRequest request = objectMapper.readValue(is,SearchApiRequest.class);
            Assertions.assertNotNull(request,"SearchApiRequest should not be null");
        }
    }

}
