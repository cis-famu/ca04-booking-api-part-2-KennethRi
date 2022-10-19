package edu.famu.booking.services;
import edu.famu.booking.models.parse.Hotel;
import edu.famu.booking.models.parse.Room;
import edu.famu.booking.models.serializable.SerializableRoom;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.parse4j.Parse;
import org.parse4j.ParseException;
import org.parse4j.ParseQuery;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class RoomService {
    protected final Log logger = LogFactory.getLog(this.getClass()); //used to write to the console

    public ArrayList<Room> retrieveRooms(String sort)
    {

        logger.info(Parse.isIsRootMode());
        final ArrayList<Room> rooms = new ArrayList<>();

        ParseQuery<Room> query = ParseQuery.getQuery(Room.class);
        List<Room> list = null;

        try {

            if(sort.equals("asc")){
                list = query.orderByAscending("price").find();
            } else if (sort.equals("dsc")) {
                list = query.orderByDescending("price").find();
            }

            for (Room p : list) {
                //logger.info(p.toString()); //use if you want to see your products in the console
                rooms.add(p);
            }
        }
        catch(Exception e)
        {
            logger.error("Error occurred", e);
        }
        logger.info(rooms.size());
        return rooms;
    }

    public Room getRoomById(String id)
    {
        Room room = null;

        //defines the query for the product class
        ParseQuery<Room> query = ParseQuery.getQuery(Room.class);

        try{
            room = query.get(id); //gets a single record based on objectId
        }catch (ParseException e)
        {
            e.printStackTrace();
        }

        return room;
    }


    public String addRoom(SerializableRoom room)
    {
        String message; //message we will return to the user

        //REMAINING CODE GOES HERE
        Room parseRoom = new Room(); //Parse Product Object

//set the value of each of the fields
        parseRoom.setTitle(room.getTitle());
        parseRoom.setPrice(room.getPrice());
        parseRoom.setMaxPeople(room.getMaxPeople());
        parseRoom.setRoomNumbers(room.getRoomNumbers());

        try {
            parseRoom.save(); //runs the query to insert the new value
            message = "Room Created"; //set success the return message

        } catch (ParseException e) {
            e.printStackTrace(); //print the error to the console.
            // set the error return message
            message = "Error creating room. " + e.getMessage();
        }
        return message;
    }

    public String updateRoom(String id, Map<String, Object> room)
    {
        String message;

        //defines the query for the product class
        ParseQuery<Room> query = ParseQuery.getQuery(Room.class);

        try {
            Room roo = query.get(id); // retrieves the room by it's objectid
            room.forEach((k,v) -> {
                if(Objects.equals(k, "title"))
                    roo.put(k,(String)v);
                else if (Objects.equals(k, "price"))
                    roo.put(k, (int) v);
                else if (Objects.equals(k, "maxPeople"))
                    roo.put(k, (int) v);
                else if (Objects.equals(k, "roomNumbers"))
                    roo.put(k, Arrays.asList(v));
            });
            roo.save(); // execute update query
            message = "Room updated."; //success message

        } catch (ParseException e) {
            e.printStackTrace();
            message = "Error updating product. " + e.getMessage(); // failure message
        }

        return message;
    }

    public String removeRoom(String id)
    {
        String message;
        ParseQuery<Room> query = ParseQuery.getQuery(Room.class);

        try
        {
            Room room = query.get(id); //get product by id
            room.delete(); //delete product
            message = "Room " + id + " deleted."; //success message
        } catch (ParseException e) {
            e.printStackTrace();
            message = "Error while deleting room. " + e.getMessage(); //error message
        }
        return message;
    }
}
