package com.csc535.app.pollpops;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoCollection;
import org.bson.Document;
import android.os.AsyncTask;
import java.util.Set;

public class PollPopsDB {
    private MongoDatabase getDB( String pid ) {
        MongoClient mongoClient = new MongoClient( "trillian.arctangent.net" , 27017 );
        MongoDatabase db = mongoClient.getDatabase(pid);
        return db;
    }
    public void sendChatMessage(String msg, String performance_id ) {
        MongoDatabase db = this.getDB(performance_id);
        MongoCollection<Document> coll = db.getCollection("chat_messages");
        Document doc = new Document("message", msg)
                .append("user", "testuser")
                .append("time", "blorp");
        coll.insertOne(doc);
    }

    public void createPerformance( String pid, String performer, String location, String datetime ) {
        MongoDatabase db = this.getDB(pid);
        MongoCollection<Document> coll = db.getCollection("performance");
        Document doc = new Document("location", location)
                .append("datetime", datetime)
                .append("performer", performer);
        coll.insertOne(doc);
    }

    public void createSetList( String pid, String[] songs ) {
        MongoDatabase db = this.getDB(pid);
        MongoCollection<Document> coll = db.getCollection("setlist");
        for( String set : songs ) {
            Document doc = new Document("song", set);
            coll.insertOne(doc);
        }
    }
    public String getMessages( String vid ) {
        return "blorp";
    }
}

