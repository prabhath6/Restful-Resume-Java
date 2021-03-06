package com.restful.resume;

/**
 * Created by prabhath on 7/8/16.
 */

import java.net.UnknownHostException;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.codehaus.jettison.json.JSONObject;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;


@Path("/{a:resume|}")
public class Resume implements loginDetails {

    public static DBCollection getObject(){

        StringBuilder userDetails = new StringBuilder();

        userDetails.append("mongodb://");
        userDetails.append(loginDetails.username);
        userDetails.append(":");
        userDetails.append(loginDetails.password);
        userDetails.append("@ds015995.mlab.com:15995/resume");


        MongoClientURI uri  = new MongoClientURI(userDetails.toString());
        MongoClient mongo = null;
        DBCollection table = null;

        try {
            mongo = new MongoClient(uri);
            DB db = mongo.getDB(uri.getDatabase());
            table = db.getCollection("my_resume");
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        return table;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getResume() {

        DBCollection table = getObject();

        DBObject allQuery = new BasicDBObject();
        DBObject removeIdProjection = new BasicDBObject("_id", 0);

        DBCursor cursor = table.find(allQuery, removeIdProjection);

        BasicDBObject result = (BasicDBObject) cursor.next();

        JSONObject obj = new JSONObject(result);

        cursor.close();

        return Response.status(200).entity(obj.toString()).build();
    }

    @Path("{param}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getResumeSection(@PathParam("param") String param) {

        DBCollection table = getObject();

        DBObject allQuery = new BasicDBObject();
        DBObject removeIdProjection = new BasicDBObject("_id", 0);

        DBCursor cursor = table.find(allQuery, removeIdProjection);

        BasicDBObject result = (BasicDBObject) cursor.next();
        BasicDBObject contact = null;

        switch (param) {
            case "contact":

                contact = (BasicDBObject) result.get("contact_info");

                break;
            case "experience":

                contact = (BasicDBObject) result.get("Experience");

                break;
            case "skills":

                contact = (BasicDBObject) result.get("Skills");

                break;
            case "links":

                contact = (BasicDBObject) result.get("Links");

                break;
            case "education":

                contact = (BasicDBObject) result.get("Education");

                break;
            case "projects":

                contact = (BasicDBObject) result.get("Projects");

                break;
            default:
                contact = (BasicDBObject) result.get("");
                break;
        }

        JSONObject obj = new JSONObject(contact);

        cursor.close();

        return Response.status(200).entity(obj.toString()).build();
    }

}
