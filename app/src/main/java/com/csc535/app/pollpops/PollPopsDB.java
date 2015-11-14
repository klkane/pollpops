package com.csc535.app.pollpops;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.*;
import com.mongodb.client.model.Sorts.*;
import org.bson.Document;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.NoSuchElementException;
import java.util.Set;



public class PollPopsDB {
    public String performance_id;
    public String username;

    private MongoDatabase getDB( String pid ) {
        MongoClient mongoClient = new MongoClient( "trillian.arctangent.net" , 27017 );
        MongoDatabase db = mongoClient.getDatabase(this.performance_id);
        return db;
    }

    public void setNowPlaying( String nowPlaying ) {
        MongoDatabase db = this.getDB(this.performance_id);
        MongoCollection<Document> coll = db.getCollection("performance");
        coll.replaceOne(Filters.eq("performance_id", this.performance_id),
                new Document("$set", new Document("now_playing", nowPlaying)));
    }

    public String getNowPlaying() {
        MongoDatabase db = this.getDB(this.performance_id);
        MongoCollection<Document> coll = db.getCollection("performance");
        Document doc = coll.find().first();
        return doc.getString( "now_playing" );
    }

    public void sendChatMessage( String msg ) {
        MongoDatabase db = this.getDB(this.performance_id);
        MongoCollection<Document> coll = db.getCollection("chat_messages");
        String ts = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
        Document doc = new Document("message", msg)
                .append("user", this.username )
                .append("time", ts );
        coll.insertOne(doc);
    }

    public String[] getChatMessages( int count ) {
        MongoDatabase db = this.getDB(this.performance_id);
        MongoCollection<Document> coll = db.getCollection("chat_messages");
        MongoCursor<Document> cursor = coll.find().sort(Sorts.descending("time")).limit(count).iterator();
        String[] results = new String[count];
        int index = 0;
        try {
            while (cursor.hasNext()) {
                Document doc = cursor.next();
                results[index] = doc.getString("user") + ": "
                            + doc.getString("message");
                index++;
            }

        } finally {
            cursor.close();
        }
        return results;
    }

    public void createPerformance( String performer, String location, String datetime ) {
        MongoDatabase db = this.getDB(this.performance_id);
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
}

