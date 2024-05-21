package co.elasticsearch.enterprisesearch.client.model.request.search.filter;

import lombok.RequiredArgsConstructor;

import java.util.Optional;

/**
 * Utility class that wraps an instance of a filter and provides methods for working with filters.
 */
@RequiredArgsConstructor
public class Filters {

    /**
     * The filter that will be operated on
     * @param filter the filter
     * @return the filter
     */
    private final Filter filter;

    /**
     * Determine whether the filter is a bool filter
     * @return true if the filter is a BooleanFilter, otherwise false
     */
    public boolean isBoolean(){
        return filter instanceof BooleanFilter;
    }

    /**
     * Determine whether the filter contains a nested any filter
     * @return true if the filter is a BooleanFilter and it contains an any filter
     */
    public boolean hasAnyFilter(){
        if(filter instanceof BooleanFilter){
            BooleanFilter bool  = (BooleanFilter) filter;
            return !bool.getAny().isEmpty();
        }
        return false;
    }

    /**
     * Determine whether the filter contains a nested all filter
     * @return true if the filter is a BooleanFilter and it contains an all filter
     */
    public boolean hasAllFilter(){
        if(filter instanceof BooleanFilter){
            BooleanFilter bool  = (BooleanFilter) filter;
            return !bool.getAll().isEmpty();
        }
        return false;
    }

    /**
     * Determine whether the filter contains a nested none filter
     * @return true if the filter is a BooleanFilter and it contains a none filter
     */
    public boolean hasNoneFilter(){
        if(filter instanceof BooleanFilter){
            BooleanFilter bool  = (BooleanFilter) filter;
            return !bool.getNone().isEmpty();
        }
        return false;
    }

    /**
     * Determine whether the filter applies to a field. Boolean filters do not directly apply to fields and would return
     * false
     * @return true if the filter applies to a field, otherwise false.
     */
    public boolean isField(){
        return filter instanceof FieldFilter;
    }

    /**
     * Ensure that the filter is a field filter, and cast it to the appropriate class type
     * @param clazz The concrete FieldFilter class that the filter represents
     * @return an Optional that will contain the given FieldFilter class type, or an empty Optional if the filter is null or
     * is not the given class type
     * @param <T> The given concrete FieldFilter class
     */
    public <T extends FieldFilter> Optional<T> getFieldFilter(Class<T> clazz){
        return filter == null ? Optional.empty() : Optional.of(filter).filter(clazz::isInstance).map(clazz::cast);
    }

    /**
     * Ensure that the filter is a bool filter, and cast it to a BooleanFilter
     * @return an Optional that will contain the given BooleanFilter, or an empty Optional if the filter is null or
     * is not a BooleanFilter
     */
    public Optional<BooleanFilter> getBooleanFilter(){
        return filter == null ? Optional.empty() : Optional.of(filter).filter(BooleanFilter.class::isInstance).map(BooleanFilter.class::cast);
    }

}
