package co.elasticsearch.enterprisesearch.client;

import co.elasticsearch.enterprisesearch.client.model.request.SearchApiRequest;
import co.elasticsearch.enterprisesearch.client.model.response.search.TextField;

import javax.swing.text.Document;
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

    void test(){

    }

}
