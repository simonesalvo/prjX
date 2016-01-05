package it.simonesalvo.prjX.helper;

import com.google.appengine.api.NamespaceManager;
import com.google.appengine.api.datastore.Cursor;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entities;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.QueryResultIterator;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.cmd.LoadType;
import com.googlecode.objectify.cmd.Query;

import java.util.*;

public class DatastoreHelper {

    private Objectify client = ObjectifyService.ofy();
    private String namespace;

    public DatastoreHelper() {
        this.namespace = null;
    }

    public DatastoreHelper(String namespace) {
        this.namespace = namespace;
    }

    public String setUpNamespace(){
    	 String oldNamespace = null;
         if (namespace != null) {
             oldNamespace = NamespaceManager.get();
             NamespaceManager.set(namespace);
         }
         return oldNamespace;
    }

    public void resetNamespace(String oldNamespace){
    	if (namespace != null) {
            NamespaceManager.set(oldNamespace);
        }
    }

	//////////////////////////
	//NAMESPACE start
	//////////////////////////

    /**
     * Returns the names of the namespaces currently used in Datastore.
     * It won't return the default one.
     *
     * @return a collection of namespaces' names
     */
    public static Collection<String> getNamespaces() {
        com.google.appengine.api.datastore.DatastoreService gaeDatastore = DatastoreServiceFactory.getDatastoreService();
        Collection<String> domains = new HashSet<>();
        com.google.appengine.api.datastore.Query namespacesQuery = new com.google.appengine.api.datastore.Query(Entities.NAMESPACE_METADATA_KIND);

        for (Entity e : gaeDatastore.prepare(namespacesQuery).asIterable()) {
            // A nonzero numeric id denotes the default namespace
            if (e.getKey().getId() == 0) {
                domains.add(e.getKey().getName());
            } // else: default namespace - it will be skipped
        }

        return domains;
    }

	//////////////////////////
	//NAMESPACE end
	//////////////////////////

	//////////////////////////
	//EXISTS start
	//////////////////////////

    /**
     * Test if the entity exists
     * @param entityClass
     * @param entityId
     * @return
     */
    public boolean existsEntity(Class<?> entityClass, String entityId) {
    	String oldNamespace = setUpNamespace();
        Object result = client.load().type(entityClass).id(entityId).now();
        resetNamespace(oldNamespace);
        return result != null;
    }

	//////////////////////////
	//EXISTS end
	//////////////////////////

    //////////////////////////
    //DELETE start
    //////////////////////////

    /**
     * Remove entity
     * @param entityClass
     * @param entityId
     */
    public void deleteEntitiesById(Class<?> entityClass, String entityId) {
    	String oldNamespace = setUpNamespace();
    	client.delete().type(entityClass).id(entityId).now();
    	resetNamespace(oldNamespace);
    }


    public void deleteEntitiesList(List<?> entitiesList) {
        if (entitiesList.isEmpty())
            return;
        String oldNamespace = setUpNamespace();
        client.delete().entities(entitiesList).now();
        resetNamespace(oldNamespace);
    }

    public <T> void deleteEntitiesOfClass(Class<T> entityClass) {
    	String oldNamespace = setUpNamespace();
        List<Key<T>> keys = client.load().type(entityClass).keys().list();
        client.delete().keys(keys).now();
        resetNamespace(oldNamespace);
    }

    public void deleteEntities(Set<Class<?>> classes) {
    	String oldNamespace = setUpNamespace();
        for (Class<?> c : classes)
            client.delete().keys(client.load().type(c).keys().list()).now();
        resetNamespace(oldNamespace);
    }

	//////////////////////////
	//DELETE end
	//////////////////////////

	//////////////////////////
	//GET BY INDEX start
	//////////////////////////

    /**
     *
     * @param entityClass
     * @param fieldWithCondition ex. "age >="
     * @param value
     * @return
     */
    public <T> List<T> getEntitiesByIndex(Class<T> entityClass, String fieldWithCondition, Object value) {
        String oldNamespace = setUpNamespace();
        List<T> list = client.load().type(entityClass).filter(fieldWithCondition, value).list();
        resetNamespace(oldNamespace);
        return list == null ? new ArrayList<T>() : list;
    }

    /**
     *
     * @param entityClass
     * @param fieldWithCondition ex. "age >="
     * @param value
     * @return
     */
    public <T> List<T> getEntitiesByRange(Class<T> entityClass, String fieldName, String value) {
        String oldNamespace = setUpNamespace();

        List<T> list = client.load().type(entityClass)
        	.filter(fieldName + " >=", value)
        	.filter(fieldName + " <", value + "\uFFFD")
        	.list();

        resetNamespace(oldNamespace);
        return list == null ? new ArrayList<T>() : list;
    }

   /**
    *
    * @param entityClass
    * @param fieldName
    * @param value
    * @param cursor it needs to be web safe String cursor
    * @return
    */
    public <T> QueryResultIterator<T>  getEntitiesIteratorByRange(
    		Class<T> entityClass, String fieldName,
    		String value, Cursor cursor) {

    	String oldNamespace = setUpNamespace();
    	Query<T> query = null;

    	if(cursor != null){
    		query = client.load().type(entityClass)
    				.filter(fieldName + " >=", value)
    				.filter(fieldName + " <", value + "\uFFFD")
    				.startAt(cursor);
    	}else{
    		query = client.load().type(entityClass)
    				.filter(fieldName + " >=", value)
    				.filter(fieldName + " <", value + "\uFFFD");
    	}

        resetNamespace(oldNamespace);
        return query.iterator();
    }


    /**
     *
     * @param entityClass
     * @param fieldConditionMap ex. <"age >=", 10>
     * @return
     */
	public <T> List<T> getEntititesByIndexes(Class<T> entityClass, Map<String, Object> fieldConditionMap) {
		String oldNamespace = setUpNamespace();
		LoadType<T> loader = client.load().type(entityClass);
		Query<T> query = null;
		for (String key : fieldConditionMap.keySet()) {
			if (query == null) {
				query = loader.filter(key, fieldConditionMap.get(key));
			} else {
				query = query.filter(key, fieldConditionMap.get(key));
			}
		}
		List<T> list = query != null ? query.list() : loader.list();
		resetNamespace(oldNamespace);
		return list == null ? new ArrayList<T>() : list;
	}

	//////////////////////////
	//GET BY INDEX end
	//////////////////////////

	//////////////////////////
	//GET BY CLASS start
	//////////////////////////

    public <T> List<Key<T>> getEntitiesKeysListOfClass(Class<T> entityClass) {
    	String oldNamespace = setUpNamespace();
        List<Key<T>> keys = client.load().type(entityClass).keys().list();
        resetNamespace(oldNamespace);
        return keys;
    }

    /**
     * List the entity base on the input class
     * @param entityClass
     * @return
     */
    public <T> List<T> listEntitiesOfClass(Class<T> entityClass) {
    	String oldNamespace = setUpNamespace();
        List<T> list = client.load().type(entityClass).list();
        resetNamespace(oldNamespace);
        return list == null ? new ArrayList<T>() : list;
    }

    /**
     * List the entity base on the input class
     * @param entityClass
     * @return
     */
    public <T> QueryResultIterator<T> getEntitiesIteratorByClass(Class<T> entityClass, Cursor cursor) {
    	String oldNamespace = setUpNamespace();

        Query<T> query = null;

    	if(cursor != null){
    		query = client.load().type(entityClass)
    				.startAt(cursor);
    	}else{
    		query = client.load().type(entityClass);
    	}

        resetNamespace(oldNamespace);
        return query.iterator();
    }

	//////////////////////////
	//LOAD ENTITY start
	//////////////////////////

    /**
     * Load entity by Key
     * @param entityClass
     * @param key
     * @return
     */
    public <T> T loadEntity(Class<T> entityClass, String key) {
    	Key<T> k = Key.create(entityClass, key);
    	return loadEntity(k);
    }

    /**
     * Load entity by Key
     * @param entityClass
     * @param key
     * @return
     */
    public <T> T loadEntity(Class<T> entityClass, Long keyId) {
    	Key<T> k = Key.create(entityClass, keyId);
    	return loadEntity(k);
    }

    /**
     * Load entity by Key
     * @param entityClass
     * @param key
     * @return
     */
    public <T> T loadEntity(Key<T> key) {
    	String oldNamespace = setUpNamespace();
        T entity = client.load().key(key).now();
        resetNamespace(oldNamespace);
        return entity;
    }

    public <T> Map<Key<T>,T> loadEntitiesLong(Class<T> entityClass, List<Long> keys) {
    	List<Key<T>> kList = new ArrayList<Key<T>>();
    	for(Long k : keys){
    		kList.add(Key.create(entityClass, k));
    	}
    	return loadEntities(kList);
    }

    public <T> Map<Key<T>,T> loadEntitiesStr(Class<T> entityClass, List<String> keys) {
    	List<Key<T>> kList = new ArrayList<Key<T>>();
    	for(String k : keys){
    		kList.add(Key.create(entityClass, k));
    	}
    	return loadEntities(kList);
    }

    /**
     * Load entities by Keys
     * @param entityClass
     * @param key
     * @return
     */
    public <T> Map<Key<T>,T> loadEntities(List<Key<T>> keys) {
    	String oldNamespace = setUpNamespace();
        Map<Key<T>,T> entities = client.load().keys(keys);
        resetNamespace(oldNamespace);
        return entities;
    }

	//////////////////////////
	//LOAD ENTITY end
	//////////////////////////


	//////////////////////////
	//STORE ENTITY start
	//////////////////////////

    /**
     * Store entity
     * @param entity
     * @return
     */
    public <T> Key<T> storeEntity(T entity) {
    	String oldNamespace = setUpNamespace();
        Key<T> key = client.save().entity(entity).now();
        resetNamespace(oldNamespace);
        return key;
    }

    /**
     * Store entities
     * @param entity
     * @return
     */
    public <T> Map<Key<T>,T> storeEntities(List<T> entities) {
    	String oldNamespace = setUpNamespace();
        Map<Key<T>,T> keys = client.save().entities(entities).now();
        resetNamespace(oldNamespace);
        return keys;
    }

	//////////////////////////
	//STORE ENTITY end
	//////////////////////////
}
