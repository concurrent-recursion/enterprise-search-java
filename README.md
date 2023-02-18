# Elasticsearch Enterprise Search Java Client (unofficial)

An unofficial Java client for the Elasticsearch Enterprise Search platform.

The App Search client provides methods for interacting with the App Search APIs, such as searching, indexing, engines, etc. 

The Enterprise Search client is not currently implemented.

## Binaries/Download


Binaries and dependency information for Maven, Ivy, Gradle and others can be found at http://search.maven.org.

Releases of enterprise-search-java are available in the Maven Central repository. Take also a look at the [Releases](https://github.com/concurrent-recursion/enterprise-search-java/releases).

Example for Maven:

```xml
<dependency>
    <groupId>io.github.concurrent-recursion</groupId>
    <artifactId>enterprisesearch-java-client</artifactId>
    <version>0.0.3</version>
</dependency>
```

Basic Usage
-----------
For Searching, Create a POJO class that extends `ResponseDocument` using 4 Field types (Text,Number,Date,Geolocation)

```java
import co.elasticsearch.enterprisesearch.client.model.response.search.*;

public class NationalParkSearch extends ResponseDocument {
    private TextField id;
    private TextField title;
    private NumberField acres;
    private GeolocationField location;
    private DateField dateEstablished;
}
```
For the Documents API, use a regular POJO
```java
public class NationalPark{
    private String id;
    private String title;
    private BigDecimal acres;
    private String location;
    private OffsetDateTime dateEstablished;
}
```

Initialize the client, using the applicable credentials
```java
AppSearchClient client = AppSearchClient.builder("http://localhost:3002").clientAuthentication(ClientAuthentication.withBearerAuth("private-priVateToKEnFrOMAppseArCh")).build();

//Create a documents client
DocumentsApi<NationalPark> documentsClient = client.documents(NationalPark.class);
List<NationalPark> parkList = //load a list of NationalPark objects
//Post the documents to the specified engine
IndexResponse indexResponse = documentsClient.index("my-engine",parkList);
if(indexResponse.isError()){
    List<IndexResult> errors = indexResponse.getErrors();
//handle errors
}

//Create a search client
SearchApi<NationalParkSearch> parkSearchClient = client.search(NationalParkSearch.class);

//Execute a search against the specified engine
SearchApiResponse<NationalParkSearch> results = parkSearchClient.search("my-engine",new SearchApiRequest().setQuery("zion"));
log.info("Found {} results",results.getMeta().getPage().getTotalResults());
for(NationalParkSearch park : results){
    log.info("Found id:{}, title:{}",park.getId().getRaw(),park.getTitle().getRaw());
}
```

## Supported APIs
* Documents API (excluding PATCH)
* Engines API
* Query Suggestions API
* Schema API
* Search API
* Search Settings API
* Source Engines API

## Future APIs
* API Logs API
* Click API
* Credentials API
* Log Settings API
* Synonyms API
* Multi Search API
* Adaptive Relevance API

## Not Planned
* Curations API
* Analytics API
* Web Crawler API
