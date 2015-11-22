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

    public boolean performanceExists() {
        MongoDatabase db = this.getDB(this.performance_id);
        MongoCollection<Document> coll = db.getCollection("performance");
        Document doc = (Document) coll.find().first();
        if( doc == null ) {
            return false;
        }
        return true;
    }

    public MongoCursor<Document> getFeedbackCursor() {
        MongoDatabase db = this.getDB(this.performance_id);
        MongoCollection<Document> coll = db.getCollection("feedback");
        return coll.find().iterator();
    }
    public void setNowPlaying( String nowPlaying ) {
        MongoDatabase db = this.getDB(this.performance_id);
        MongoCollection<Document> coll = db.getCollection("setlist");
        String ts = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
        coll.insertOne(new Document("song", nowPlaying).append("time", ts));
    }

    public String getPassword() {
        MongoDatabase db = this.getDB(this.performance_id);
        MongoCollection<Document> coll = db.getCollection("performance");
        Document doc = (Document) coll.find().first();
        if( doc == null ) {
            return "n0p3r1n0";
        }

        return doc.getString( "password" );
    }

    public String[] getSetlist() {
        MongoDatabase db = this.getDB(this.performance_id);
        MongoCollection<Document> coll = db.getCollection("setlist");
        MongoCursor<Document> cursor = coll.find().sort(Sorts.ascending("time")).iterator();
        String[] results = new String[100];
        int index = 0;
        try {
            while (cursor.hasNext()) {
                Document doc = cursor.next();
                results[index] = doc.getString("song");
                index++;
            }
        } finally {
            cursor.close();
        }

        return results;
    }

    public String getNowPlaying() {
        MongoDatabase db = this.getDB(this.performance_id);
        MongoCollection<Document> coll = db.getCollection("setlist");
        Document doc = (Document) coll.find().sort(Sorts.descending("time")).first();
        if( doc != null ) {
            return doc.getString("song");
        }
        return "NONE";
    }

    public void recordFeedback( int value ) {
        MongoDatabase db = this.getDB(this.performance_id);
        MongoCollection<Document> coll = db.getCollection("feedback");
        String ts = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
        Document doc = new Document("feedback", value)
                .append("user", this.username )
                .append("time", ts);
        coll.insertOne(doc);

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

    public void createPerformance( String performer, String location, String date, String time, String password ) {
        MongoDatabase db = this.getDB(this.performance_id);
        MongoCollection<Document> coll = db.getCollection("performance");
        Document doc = new Document("location", location)
                .append("date", date)
                .append("time", time)
                .append("user", this.username )
                .append("password", password)
                .append("performer", performer);
        coll.insertOne(doc);
    }
}

