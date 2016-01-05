package it.simonesalvo.prjX.utils;

import com.google.common.collect.ImmutableList;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Subclass;
import org.reflections.Reflections;

import java.lang.annotation.Annotation;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class DatastoreUtils {
    
    public static final String MAIN_CLASSES_PACKAGE = "it.simonesalvo";
    
    private static final Collection<Class<? extends Annotation>> DATASTORE_ENTITIES_ANNOTATIONS =
            ImmutableList.of(Entity.class, Subclass.class);

    public static Set<Class<?>> getDatastoreClasses() {
        Set<Class<?>> classes = new HashSet<>();
        for (Class<? extends Annotation> c : DATASTORE_ENTITIES_ANNOTATIONS) {
            classes.addAll(getClassesAnnotatedWith(c));
        }
        return classes;
    }

    public static Set<Class<?>> getClassesAnnotatedWith(Class<? extends Annotation> annotation) {
        Reflections reflections = new Reflections(MAIN_CLASSES_PACKAGE);
        return reflections.getTypesAnnotatedWith(annotation);
    }



}
