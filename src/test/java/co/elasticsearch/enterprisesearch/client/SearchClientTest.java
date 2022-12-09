package co.elasticsearch.enterprisesearch.client;

import co.elasticsearch.enterprisesearch.client.model.request.SearchApiRequest;
import co.elasticsearch.enterprisesearch.client.model.response.SearchApiResponse;
import co.elasticsearch.enterprisesearch.client.model.response.TextField;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SearchClientTest {


    static class TestResult{
        private TextField title;
        public TextField getTitle(){
            return title;
        }
        public void setTitle(TextField title){
            this.title = title;
        }
    }

}
