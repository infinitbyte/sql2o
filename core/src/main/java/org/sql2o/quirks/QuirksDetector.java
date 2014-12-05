package org.sql2o.quirks;

import org.sql2o.GenericDatasource;

import java.util.Iterator;
import java.util.ServiceLoader;

/**
 * User: dimzon
 * Date: 4/24/14
 * Time: 9:39 AM
 */
public class QuirksDetector{
    static final ServiceLoader<QuirksProvider> providers = ServiceLoader.load(QuirksProvider.class);

    public static Quirks forURL(String jdbcUrl) {
        Quirks quirks;
        Iterator<QuirksProvider> itr = providers.iterator();
        while (itr.hasNext()) {
            QuirksProvider next =  itr.next();
            quirks=next.forURL(jdbcUrl);
            if(quirks!=null) return quirks;

        }
        return new NoQuirks();
    }

    public static Quirks forObject(Object jdbcObject) {
        if(jdbcObject instanceof GenericDatasource)
            return forURL(((GenericDatasource) jdbcObject).getUrl());
        Quirks quirks;

        if(providers!=null){
            Iterator<QuirksProvider> itr = providers.iterator();
            while (itr.hasNext()) {
                QuirksProvider next = itr.next();
                quirks=next.forObject(jdbcObject);
                if(quirks!=null) return quirks;
        }
    }

    return new NoQuirks();

    }
}
