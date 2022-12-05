package co.elasticsearch.enterprisesearch.client.model.request.filter;

import com.fasterxml.jackson.annotation.JsonValue;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


public class NestedFilter implements Filter {
    private List<Filter> all = new ArrayList<>();
    private List<Filter> any = new ArrayList<>();
    private List<Filter> none = new ArrayList<>();


    public NestedFilter setAll(List<Filter> all){
        this.all = all;
        return this;
    }
    public List<Filter> getAll(){
        return all;
    }
    public List<Filter> getAny(){
        return any;
    }
    public NestedFilter setAny(List<Filter> any){
        this.any = any;
        return this;
    }
    public List<Filter> getNone(){
        return none;
    }
    public NestedFilter setNone(List<Filter> none){
        this.none = none;
        return this;
    }

    @JsonValue
    public Map<String,List<Filter>> values(){
        Map<String,List<Filter>> values = new LinkedHashMap<>();
        if(!all.isEmpty()){
            values.put("all",all);
        }
        if(!any.isEmpty()){
            values.put("any",any);
        }
        if(!none.isEmpty()){
            values.put("none",none);
        }
        return values;
    }
}
