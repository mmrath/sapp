package com.mmrath.sapp.utils;

import org.hibernate.Hibernate;

import java.util.Collection;
import java.util.Optional;

/**
 * Created by Murali on 4/6/2014.
 */
public class PersistentUtils {
    public static void initialize(Object entity){
        if(entity!=null){
            if(entity instanceof Optional && ((Optional) entity).isPresent()){
                Hibernate.initialize(((Optional) entity).get());
            }else{
                Hibernate.initialize(entity);
            }
        }
    }

    public static void initialize(Collection<?> entities){
        for(Object entity:entities){
            Hibernate.initialize(entity);
        }
    }

}
