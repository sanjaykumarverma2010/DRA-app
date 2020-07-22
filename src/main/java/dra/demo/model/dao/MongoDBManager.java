package dra.demo.model.dao;

import com.mongodb.client.MongoCollection;
import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;
import dra.demo.model.*;
import org.bson.Document;

/*
 * @author George 
 * Connect to MongoDB Atlas and perform CRUD operations
 */
public class MongoDBManager extends MongoDB {

    private MongoCollection<Document> collection;

    //Run the MongoDB super-class constructor and build a connection with MongoDB Atlas
    public MongoDBManager(String owner, String password, String role, String db, String collection) {
        super(owner, password, role, db);
        connect(collection);
    }

    //connect to a collect of the existing db
    public void connect(String name) {
        this.collection = super.database.getCollection(name);
    }

    //Create operation
    public void create(String name, String email, String password, String phone) {
        Document entity = new Document("_id", email)
                .append("name", name)
                .append("password", password)
                .append("phone", phone);
        this.collection.insertOne(entity);
    }

    //Read Operation
    public User user(String email, String password) {
        Document doc = collection.find(and(eq("_id", email), eq("password", password))).first();
        User user = new User((String) doc.get("name"), (String) doc.get("email"), (String) doc.get("password"), (String) doc.get("phone"));
        return user;
    }    
}
