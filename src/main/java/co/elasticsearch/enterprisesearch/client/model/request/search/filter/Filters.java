package co.elasticsearch.enterprisesearch.client.model.request.search.filter;

import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class Filters {

    private final Filter filter;
    public boolean isBoolean(){
        return filter instanceof BooleanFilter;
    }

    public boolean hasAnyFilter(){
        if(filter instanceof BooleanFilter){
            BooleanFilter bool  = (BooleanFilter) filter;
            return !bool.getAny().isEmpty();
        }
        return false;
    }

    public boolean hasAllFilter(){
        if(filter instanceof BooleanFilter){
            BooleanFilter bool  = (BooleanFilter) filter;
            return !bool.getAll().isEmpty();
        }
        return false;
    }

    public boolean hasNoneFilter(){
        if(filter instanceof BooleanFilter){
            BooleanFilter bool  = (BooleanFilter) filter;
            return !bool.getNone().isEmpty();
        }
        return false;
    }

    public boolean isField(){
        return filter instanceof FieldFilter;
    }

    public <T extends FieldFilter> Optional<T> getFieldFilter(Class<T> clazz){
        return filter == null ? Optional.empty() : Optional.of(filter).filter(clazz::isInstance).map(clazz::cast);
    }

    public Optional<BooleanFilter> getBooleanFilter(){
        return filter == null ? Optional.empty() : Optional.of(filter).filter(BooleanFilter.class::isInstance).map(BooleanFilter.class::cast);
    }

}
