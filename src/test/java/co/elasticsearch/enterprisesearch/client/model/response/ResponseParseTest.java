package co.elasticsearch.enterprisesearch.client.model.response;

import co.elasticsearch.enterprisesearch.TestUtil;
import co.elasticsearch.enterprisesearch.client.model.request.search.facet.ValueFacet;
import co.elasticsearch.enterprisesearch.client.model.response.documents.IndexResponse;
import co.elasticsearch.enterprisesearch.client.model.response.documents.IndexResult;
import co.elasticsearch.enterprisesearch.client.model.response.search.MultiSearchApiResponse;
import co.elasticsearch.enterprisesearch.client.model.response.search.SearchApiResponse;
import co.elasticsearch.enterprisesearch.client.model.response.search.facet.*;
import co.elasticsearch.enterprisesearch.client.model.response.search.facet.value.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
class ResponseParseTest {
    private static final ObjectMapper objectMapper = new ObjectMapper();
    static{
        objectMapper.registerModule(new JavaTimeModule());
    }



    @Test
    void deserializeResponse() throws JsonProcessingException {
        String json = TestUtil.readResourceFile("examples/responses/parks-1.json");
        JavaType type = objectMapper.getTypeFactory().constructParametricType(SearchApiResponse.class, NationalParkDocument.class);
        SearchApiResponse<NationalParkDocument> response = objectMapper.readValue(json, type);
        assertEquals(1,response.getResults().size());
    }

    @Test
    void deserializeMultiResponse() throws JsonProcessingException {
        String json = TestUtil.readResourceFile("examples/responses/multiresponse.json");
        JavaType type = objectMapper.getTypeFactory().constructParametricType(SearchApiResponse.class, NationalParkDocument.class);
        type = objectMapper.getTypeFactory().constructParametricType(MultiSearchApiResponse.class,type);

        MultiSearchApiResponse<NationalParkDocument> response = objectMapper.readValue(json, type);
        assertEquals(2,response.getResults().size());
        assertEquals(2,response.getResults().get(0).getResults().size());
    }

    @Test
    void searchResponse_NumberValue_Facet() throws JsonProcessingException {
        String json = TestUtil.readResourceFile("examples/responses/facets/search-facet-number.json");
        JavaType type = objectMapper.getTypeFactory().constructParametricType(SearchApiResponse.class, NationalParkDocument.class);
        SearchApiResponse<NationalParkDocument> response = objectMapper.readValue(json, type);
        assertEquals(1,response.getFacets().size());
        Facet docType = response.getFacets().get(0);
        assertEquals(5,docType.getData().size());
        FacetValue facetValue = docType.getData().get(0);
        assertInstanceOf(NumberValue.class,facetValue);
        NumberValue number = (NumberValue) facetValue;
        assertEquals(new BigDecimal("12.3"), number.getValue());
        assertEquals(8L, number.getCount());
    }

    @Test
    void searchResponse_TextValue_Facet() throws JsonProcessingException {
        String json = TestUtil.readResourceFile("examples/responses/facets/search-facet-text.json");
        JavaType type = objectMapper.getTypeFactory().constructParametricType(SearchApiResponse.class, NationalParkDocument.class);
        SearchApiResponse<NationalParkDocument> response = objectMapper.readValue(json, type);
        assertEquals(1,response.getFacets().size());
        Facet docType = response.getFacets().get(0);
        assertEquals(5,docType.getData().size());
        FacetValue facetValue = docType.getData().get(0);
        assertInstanceOf(TextValue.class,facetValue);
        TextValue textValue = (TextValue) facetValue;
        assertEquals("California", textValue.getValue());
        assertEquals(8L, textValue.getCount());
    }

    @Test
    void searchResponse_NumberRange_Facet() throws JsonProcessingException {
        String json = TestUtil.readResourceFile("examples/responses/facets/search-facet-number-range.json");
        JavaType type = objectMapper.getTypeFactory().constructParametricType(SearchApiResponse.class, NationalParkDocument.class);
        SearchApiResponse<NationalParkDocument> response = objectMapper.readValue(json, type);
        assertEquals(1,response.getFacets().size());
        Facet docType = response.getFacets().get(0);
        assertEquals(2,docType.getData().size());
        assertEquals("min-and-max-range",docType.getName());
        FacetValue facetValue = docType.getData().get(0);
        assertInstanceOf(NumberRange.class,facetValue);
        NumberRange numberRangeValue = (NumberRange) facetValue;
        assertEquals(10000L, numberRangeValue.getToLong());
        assertEquals(1L,numberRangeValue.getFromLong());
        assertEquals(2L, numberRangeValue.getCount());

        facetValue = docType.getData().get(1);
        assertInstanceOf(NumberRange.class,facetValue);
        numberRangeValue = (NumberRange) facetValue;
        assertNull(numberRangeValue.getTo());
    }

    @Test
    void searchResponse_DateRange_Facet_Direct() throws JsonProcessingException {
        String json = TestUtil.readResourceFile("examples/responses/facets/search-facet-date-range.json");
        JavaType type = objectMapper.getTypeFactory().constructParametricType(SearchApiResponse.class, NationalParkDocument.class);
        SearchApiResponse<NationalParkDocument> response = objectMapper.readValue(json, type);
        assertEquals(1,response.getFacets().size());
        Facet docType = response.getFacets().get(0);
        assertEquals(1,docType.getData().size());
        assertEquals("half-century",docType.getName());
        FacetValue facetValue = docType.getData().get(0);
        assertInstanceOf(DateRange.class,facetValue);
        DateRange dateRangeValue = (DateRange) facetValue;
        assertEquals(OffsetDateTime.parse("1950-01-01T00:00:00.000Z"), dateRangeValue.getTo());
        assertEquals(OffsetDateTime.parse("1900-01-01T12:00:00.000Z"),dateRangeValue.getFrom());
        assertEquals(15L, dateRangeValue.getCount());

    }

    @Test
    void searchResponse_DateRange_Facet() throws JsonProcessingException {
        String json = TestUtil.readResourceFile("examples/responses/facets/search-facet-date-range.json");
        JavaType type = objectMapper.getTypeFactory().constructParametricType(SearchApiResponse.class, NationalParkDocument.class);
        SearchApiResponse<NationalParkDocument> response = objectMapper.readValue(json, type);
        Optional<DateRangeFacet> halfCentury = response.getFacetByFieldAndName("date_established","half-century",DateRangeFacet.class);
        assertTrue(halfCentury.isPresent());
        assertEquals(1,halfCentury.get().getData().size());
        assertEquals("half-century",halfCentury.get().getName());
        DateRange dateRangeValue = halfCentury.get().getDateRanges().get(0);
        assertEquals(OffsetDateTime.parse("1950-01-01T00:00:00.000Z"), dateRangeValue.getTo());
        assertEquals(OffsetDateTime.parse("1900-01-01T12:00:00.000Z"),dateRangeValue.getFrom());
        assertEquals(15L, dateRangeValue.getCount());

    }

    @Test
    void searchResponse_Empty_Facet() throws JsonProcessingException {
        String json = TestUtil.readResourceFile("examples/responses/facets/search-facet-empty.json");
        JavaType type = objectMapper.getTypeFactory().constructParametricType(SearchApiResponse.class, NationalParkDocument.class);
        SearchApiResponse<NationalParkDocument> response = objectMapper.readValue(json, type);
        
        final String valueFieldName = "states";
        final String valueFacetName = "states_name";
        final String rangeFieldName = "acres";
        final String rangeFacetName = "acres_name";

        Optional<TextValueFacet> textValueFacet = response.getFacetByFieldAndName( valueFieldName, valueFacetName, TextValueFacet.class);
        assertTrue(textValueFacet.isPresent());
        assertEquals( valueFacetName,textValueFacet.get().getName());

        Optional<NumberValueFacet> numberValueFacet = response.getFacetByFieldAndName( valueFieldName, valueFacetName, NumberValueFacet.class);
        assertTrue(numberValueFacet.isPresent());
        assertEquals( valueFacetName,numberValueFacet.get().getName());

        Optional<DateValueFacet> dateValueFacet = response.getFacetByFieldAndName( valueFieldName, valueFacetName, DateValueFacet.class);
        assertTrue(dateValueFacet.isPresent());
        assertEquals( valueFacetName,dateValueFacet.get().getName());

        Optional<DateRangeFacet> dateRangeFacet = response.getFacetByFieldAndName( rangeFieldName, rangeFacetName, DateRangeFacet.class);
        assertTrue(dateRangeFacet.isPresent());
        assertEquals( rangeFacetName,dateRangeFacet.get().getName());

        Optional<GeolocationRangeFacet> geoRangeFacet = response.getFacetByFieldAndName( rangeFieldName, rangeFacetName, GeolocationRangeFacet.class);
        assertTrue(geoRangeFacet.isPresent());
        assertEquals( rangeFacetName,geoRangeFacet.get().getName());

        Optional<NumberRangeFacet> numberRangeFacet = response.getFacetByFieldAndName( rangeFieldName, rangeFacetName, NumberRangeFacet.class);
        assertTrue(numberRangeFacet.isPresent());
        assertEquals( rangeFacetName,numberRangeFacet.get().getName());

    }

    @Test
    void indexResponse() throws JsonProcessingException{
        String json = TestUtil.readResourceFile("examples/responses/index.json");
        JavaType listType = TypeFactory.defaultInstance().constructParametricType(List.class, IndexResult.class);
        List<IndexResult> results = objectMapper.readValue(json,listType);
        IndexResponse response = new IndexResponse().setDocuments(results);
        assertEquals(3,response.getDocuments().size());
        assertTrue(response.isError());
    }
}
