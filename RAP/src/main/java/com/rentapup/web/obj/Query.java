package com.rentapup.web.obj;

import java.util.Map;

/**
 * Created by elijahstaple on 4/16/17.
 */
public class Query {
    private QueryType queryType;
    private Map<String, Object> query;

    public QueryType getQueryType() {
        return queryType;
    }

    public void setQueryType(QueryType queryType) {
        this.queryType = queryType;
    }

    public Map<String, Object> getQuery() {
        return query;
    }

    public void setQuery(Map<String, Object> query) {
        this.query = query;
    }
}
