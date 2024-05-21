# Elasticsearch Enterprise Search Java Client

A Java client for the Elasticsearch Enterprise Search platform.

The App Search client provides methods for interacting with the App Search APIs, such as searching, ingesting, managing engines, etc. 


## Binaries/Download


Binaries and dependency information for Maven, Ivy, Gradle and others can be found at http://search.maven.org.

Releases of enterprise-search-java are available in the Maven Central repository. Or alternatively see [Releases](https://github.com/concurrent-recursion/enterprise-search-java/releases).

### Maven 
Add the following to your pom.xml
```xml
<dependency>
    <groupId>io.github.concurrent-recursion</groupId>
    <artifactId>enterprisesearch-java-client</artifactId>
    <version>0.5.5</version>
</dependency>
```
### Gradle
Add the following dependency
```groovy
dependencies {
  implementation 'io.github.concurrent-recursion:enterprisesearch-java-client:0.5.5'
}
```

## Basic Usage

### Token Based Authentication
```java
//Token based authentication, App Search API Key, Elasticsearch Token, etc
final AppSearchClient client = AppSearchClient.builder("http://localhost:3002")
    .clientAuthentication(ClientAuthentication.withBearerAuth("private-priVateToKEnFrOMAppseArCh")).build();
```
### Username + Password Authentication
```java
//Username + Password authentication
final AppSearchClient client = AppSearchClient.builder("http://localhost:3002")
    .clientAuthentication(ClientAuthentication.withBasicAuth("myusername","mypassword")).build();
```


### Adding Logging
In order to add logging to your Appsearch client, you will need to add an OKHttp3 HttpLoggingInterceptor.

You can also customize the OkHttpClient by passing in your own OkHttpClientBuilder as shown in the logging example below
```java
HttpLoggingInterceptor logging = new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY);

final AppSearchClient client = AppSearchClient.builder("http://localhost:3002")
    //Pass in a custom builder with the logging interceptor added
    .clientBuilder(new OkHttpClient.Builder().addInterceptor(logging))
    .clientAuthentication(ClientAuthentication.withBearerAuth("private-priVateToKEnFrOMAppseArCh")).build();
```

## Engines API

Get Engine by name
```java
EnginesApi someEngine = client.engines().getEngineByName("some-engine");

```



## Search API

### Data Types
AppSearch has 4 field types, which are mapped to the following types in this library
1. **Text** `TextField` or `TextArrayField`
2. **Number** `NumberField` or `NumberArrayField`
3. **Date** `DateField` or `DateArrayField`
4. **Geolocation** `GeolocationField` or `GeolocaitonArrayField`

Each type has a subfield named `raw` that contain the raw value(s) of the field. The Text type also may contain a single `snippet` field that contains a text snippet.

Any of the data types may contain multiple values. If your database may contain multiple, use the corresponding `*Array` type to map the field

For Searching, Create a POJO class that extends `ResponseDocument` using 4 Field types (Text,Number,Date,Geolocation)
#### Example Search Document mapping
```java
import co.elasticsearch.enterprisesearch.client.model.response.search.*;

public class NationalParkSearch extends ResponseDocument {
    private TextField id;
    private TextField title;
    private TextArrayField states;
    private NumberField acres;
    private GeolocationField location;
    private DateField dateEstablished;
    //Getters,Setters, etc
}
```

### Execute a Search
```java
//Create a search client
SearchApi<NationalParkSearch> parkSearchClient = client.search(NationalParkSearch.class);

//Execute a search against the specified engine
SearchApiResponse<NationalParkSearch> results = parkSearchClient.search("my-engine",new SearchRequest().setQuery("zion"));
log.info("Found {} results",results.getMeta().getPage().getTotalResults());
for(NationalParkSearch park : results){
    log.info("Found id:{}, title:{}",park.getId().getRaw(),park.getTitle().getRaw());
}
```

### Facets
To read a facet of a particular type, you can use one of the convience methods
```java
SearchApiResponse response = ...;
Optional<TextValueFacet> myTextFacet = response.getFacetByFieldAndName("title","my_title_facet",TextValueFacet.class);
```
If the facet exists, it will be present in the optional, if it doesn't exist it will not be present.

## Documents API
The client supports working with typed documents to ingest content into an engine. The AppSearch schema supports 4 types:
1. Text `String`
2. Number `BigDecimal`, `Integer`, `Long`
3. Date `OffsetDateTime`, `ZonedDateTime`, `LocalDateTime`
4. Geolocation `String`, `BigDecimal[]` (using \[lon, lat\] )

Example POJO for ingesting into AppSearch
```java
public class NationalPark {
    private String id;
    private String title;
    private BigDecimal acres;
    private String location;
    private OffsetDateTime dateEstablished;
    //Getters, Setters, etc
}
```

Using this document, we can ingest the documents using this example:
```java
AppSearchClient client = //...
        
List<IndexResult> ingestDocs(List<NationalPark> parkList){
    //Create a documents client
    DocumentsApi<NationalPark> documentsClient = client.documents(NationalPark.class);
    //Post the documents to the specified engine
    IndexResponse indexResponse = documentsClient.index("my-engine",parkList);
    if(indexResponse.isError()){
        List<IndexResult> errors = indexResponse.getErrors();
            //handle errors
    }
    return indexResponse.getDocuments();            
}
```




## Supported APIs
* Documents API (excluding PATCH)
* Engines API
* Query Suggestions API
* Schema API
* Search API
* Multi Search API
* Search Settings API
* Source Engines API

## Future APIs
* API Logs API
* Click API
* Credentials API
* Log Settings API
* Synonyms API
* Adaptive Relevance API

## Not Planned
* Curations API
* Analytics API
* Web Crawler API
