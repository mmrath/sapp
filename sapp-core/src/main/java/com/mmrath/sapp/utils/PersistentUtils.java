package com.mmrath.sapp.utils;

import org.hibernate.Hibernate;

import java.util.Collection;

/**
 * Created by Murali on 4/6/2014.
 */
public class PersistentUtils {
    public static void initialize(Object entity){
        Hibernate.initialize(entity);
    }

    public static void initialize(Collection<?> entities){
        for(Object entity:entities){
            Hibernate.initialize(entity);
        }
    }

}
