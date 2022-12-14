package co.elasticsearch.enterprisesearch.client.model.response.documents;

import co.elasticsearch.enterprisesearch.TestUtil;
import co.elasticsearch.enterprisesearch.client.model.response.NationalParkStringDocument;
import co.elasticsearch.enterprisesearch.client.model.response.ResponsePage;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DocumentsTests {
    private final ObjectMapper objectMapper = new ObjectMapper();
    @Test
    void deserializeIndexResponse() throws JsonProcessingException {
        String json = TestUtil.readResourceFile("examples/responses/documents-index.json");

        JavaType listType = TypeFactory.defaultInstance().constructParametricType(List.class, IndexResult.class);
        List<IndexResult> results = objectMapper.readValue(json,listType);
        IndexResponse jsonResponse = new IndexResponse().setDocuments(results);
        IndexResponse response = new IndexResponse().setDocuments(List.of(new IndexResult().setId("park_death-valley"),new IndexResult().setId("park_wind-cave")));
        assertEquals(response,jsonResponse);
        assertFalse(jsonResponse.isError());
        assertTrue(jsonResponse.getErrors().isEmpty());
    }
    @Test
    void deserializeIndexError() throws JsonProcessingException {
        String json = TestUtil.readResourceFile("examples/responses/documents-index-error.json");

        JavaType listType = TypeFactory.defaultInstance().constructParametricType(List.class, IndexResult.class);
        List<IndexResult> results = objectMapper.readValue(json,listType);
        IndexResponse jsonResponse = new IndexResponse().setDocuments(results);
        IndexResponse response = new IndexResponse().setDocuments(List.of(new IndexResult().setId("park_yosemite"),new IndexResult().setId("").setErrors(List.of("Missing required key 'id'")),new IndexResult().setId("park_wind-cave")));
        assertEquals(response,jsonResponse);
        assertTrue(jsonResponse.isError());
        assertEquals(new IndexResult().setId("").setErrors(List.of("Missing required key 'id'")) ,jsonResponse.getErrors().stream().findFirst().get());
    }

    @Test
    void deserializeDeleteResponse() throws JsonProcessingException{
        String json = TestUtil.readResourceFile("examples/responses/documents-delete.json");
        JavaType listType = TypeFactory.defaultInstance().constructParametricType(List.class, DeleteResult.class);
        List<DeleteResult> results = objectMapper.readValue(json,listType);
        DeleteResponse jsonResponse = new DeleteResponse().setDocuments(results);
        DeleteResponse response = new DeleteResponse().setDocuments(List.of(new DeleteResult().setId("park_zion").setDeleted(true),new DeleteResult().setId("park_yosmite").setDeleted(true),new DeleteResult().setId("does_not_exist").setDeleted(false)));
        assertEquals(response,jsonResponse);
        assertTrue(jsonResponse.isError());
        assertEquals(1,jsonResponse.getErrors().size());
    }

    @Test
    void deserializeListResponse() throws JsonProcessingException{
        String json = TestUtil.readResourceFile("examples/responses/documents-list.json");
        JavaType listType = TypeFactory.defaultInstance().constructParametricType(ListResponse.class, NationalParkStringDocument.class);
        ListResponse<NationalParkStringDocument> jsonResponse = objectMapper.readValue(json,listType);
        NationalParkStringDocument zion = new NationalParkStringDocument().setAcres("147237.02").setTitle("Zion").setDescription("Located at the junction of the Colorado Plateau, Great Basin, and Mojave Desert, this park contains sandstone features such as mesas, rock towers, and canyons, including the Virgin River Narrows. The various sandstone formations and the forks of the Virgin River create a wilderness divided into four ecosystems: desert, riparian, woodland, and coniferous forest.")
                .setLocation("37.3,-113.05").setStates(List.of("Utah")).setNpsLink("https://www.nps.gov/zion/index.htm").setSquareKm("595.8").setVisitors("4295127").setDateEstablished("1919-11-19T06:00:00Z").setId("park_zion").setWorldHeritageSite("false");
        List<NationalParkStringDocument> docs = new ArrayList<>();
        docs.add(zion);
        docs.add(null);
        ResponsePage page = new ResponsePage();
        page.setTotalPages(30).setTotalResults(60).setCurrent(1).setSize(2);
        ListResponse<NationalParkStringDocument> response = new ListResponse<NationalParkStringDocument>().setDocuments(docs).setMeta(new Meta().setPage(page));
        assertEquals(response,jsonResponse);
    }

    @Test
    void deserializeGetResponse() throws JsonProcessingException{
        String json = TestUtil.readResourceFile("examples/responses/documents-get.json");
        JavaType listType = TypeFactory.defaultInstance().constructParametricType(List.class, NationalParkStringDocument.class);
        List<NationalParkStringDocument> results = objectMapper.readValue(json,listType);
        NationalParkStringDocument zion = new NationalParkStringDocument().setAcres("147237.02").setTitle("Zion").setDescription("Located at the junction of the Colorado Plateau, Great Basin, and Mojave Desert, this park contains sandstone features such as mesas, rock towers, and canyons, including the Virgin River Narrows. The various sandstone formations and the forks of the Virgin River create a wilderness divided into four ecosystems: desert, riparian, woodland, and coniferous forest.")
                .setLocation("37.3,-113.05").setStates(List.of("Utah")).setNpsLink("https://www.nps.gov/zion/index.htm").setSquareKm("595.8").setVisitors("4295127").setDateEstablished("1919-11-19T06:00:00Z").setId("park_zion").setWorldHeritageSite("false");
        List<NationalParkStringDocument> expectedDocs = new ArrayList<>();
        expectedDocs.add(zion);
        expectedDocs.add(null);
        assertEquals(results,expectedDocs);
    }
}
