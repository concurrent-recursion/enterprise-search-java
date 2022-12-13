package co.elasticsearch.enterprisesearch.client;

import co.elasticsearch.enterprisesearch.client.model.request.Page;
import co.elasticsearch.enterprisesearch.client.model.request.SearchApiRequest;
import co.elasticsearch.enterprisesearch.client.model.response.schemas.FieldType;
import co.elasticsearch.enterprisesearch.client.model.response.schemas.Schema;
import co.elasticsearch.enterprisesearch.client.model.response.search.TextField;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import javax.swing.text.Document;
import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SearchClientTest {

    private AppSearchClient client;


    static class TestResult{
        private TextField title;
        public TextField getTitle(){
            return title;
        }
        public void setTitle(TextField title){
            this.title = title;
        }
    }

    @Test
    void test() throws IOException {
        AppSearchClient client = null;
        SchemaApi schema = client.schemas();
        EnginesApi engines = client.engines();
        var output = engines.listEngines(new Page().setCurrent(1).setSize(20));
        //Schema output = schema.updateSchema("mobilus",new Schema().setField("test", FieldType.DATE));
        System.out.println(new ObjectMapper().writeValueAsString(output));
    }

}
