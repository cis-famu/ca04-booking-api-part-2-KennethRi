package edu.famu.booking.services;
import edu.famu.booking.models.parse.Hotel;
import edu.famu.booking.models.serializable.SerializableHotel;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.parse4j.Parse;
import org.parse4j.ParseException;
import org.parse4j.ParseQuery;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class HotelService {
    protected final Log logger = LogFactory.getLog(this.getClass()); //used to write to the console

    public ArrayList<Hotel> retrieveHotels(String sort)
    {
        logger.info(Parse.isIsRootMode());
        final ArrayList<Hotel> hotels = new ArrayList<>();

        ParseQuery<Hotel> query = ParseQuery.getQuery(Hotel.class);
        List<Hotel> list = null;

        try {

            if(sort.equals("asc")){
                list = query.orderByAscending("cheapestPrice").find();
            } else if (sort.equals("dsc")) {
                list = query.orderByDescending("cheapestPrice").find();
            }

            for (Hotel p : list) {
                //logger.info(p.toString()); //use if you want to see your products in the console
                hotels.add(p);
            }
        }
        catch(Exception e)
        {
            logger.error("Error occurred", e);
        }
        logger.info(hotels.size());
        return hotels;
    }



    public Hotel getHotelById(String id)
    {
        Hotel hotel = null;

        //defines the query for the product class
        ParseQuery<Hotel> query = ParseQuery.getQuery(Hotel.class);

        try{
            hotel = query.get(id); //gets a single record based on objectId
        }catch (ParseException e)
        {
            e.printStackTrace();
        }

        return hotel;
    }

    public String addHotel(SerializableHotel hotel)
    {
        String message; //message we will return to the user

        //REMAINING CODE GOES HERE
        Hotel parseHotel = new Hotel(); //Parse Product Object

//set the value of each of the fields
        parseHotel.setName(hotel.getName());
        parseHotel.setType(hotel.getType());
        parseHotel.setCity(hotel.getCity());
        parseHotel.setAddress(hotel.getAddress());
        parseHotel.setDistance(hotel.getDistance());
        parseHotel.setPhotos(hotel.getPhotos());
        parseHotel.setTitle(hotel.getTitle());
        parseHotel.setRating(hotel.getRating());
        parseHotel.setRooms(hotel.getRooms());
        parseHotel.setCheapestPrice(hotel.getCheapestPrice());
        parseHotel.setFeatured(hotel.getFeatured());


        try {
            parseHotel.save(); //runs the query to insert the new value
            message = "Hotel Created"; //set success the return message

        } catch (ParseException e) {
            e.printStackTrace(); //print the error to the console.
            // set the error return message
            message = "Error creating hotel. " + e.getMessage();
        }
        return message;
    }

    public String updateHotel(String id, Map<String, Object> hotel)
    {
        String message;

        //defines the query for the product class
        ParseQuery<Hotel> query = ParseQuery.getQuery(Hotel.class);

        try {
            Hotel hot = query.get(id); // retrieves the hotel by it's objectid
            hotel.forEach((k,v) -> {
                if(Objects.equals(k, "name"))
                    hot.put(k,(String)v);
                else if (Objects.equals(k, "type"))
                    hot.put(k, (String) v);
                else if (Objects.equals(k, "city"))
                    hot.put(k, (String) v);
                else if (Objects.equals(k, "address"))
                    hot.put(k, (String) v);
                else if (Objects.equals(k, "distance"))
                    hot.put(k, (String) v);
                else if (Objects.equals(k, "rooms"))
                    hot.put(k, Arrays.asList(v));
                else if (Objects.equals(k, "cheapestPrice"))
                    hot.put(k, (int) v);
                else if (Objects.equals(k, "featured"))
                    hot.put(k, (boolean) v);
            });
            hot.save(); // execute update query
            message = "Hotel updated."; //success message

        } catch (ParseException e) {
            e.printStackTrace();
            message = "Error updating product. " + e.getMessage(); // failure message
        }

        return message;
    }

    public String removeHotel(String id)
    {
        String message;
        ParseQuery<Hotel> query = ParseQuery.getQuery(Hotel.class);

        try
        {
            Hotel hotel = query.get(id); //get product by id
            hotel.delete(); //delete product
            message = "Hotel " + id + " deleted."; //success message
        } catch (ParseException e) {
            e.printStackTrace();
            message = "Error while deleting hotel. " + e.getMessage(); //error message
        }
        return message;
    }

}
