package edu.famu.booking.services;
import edu.famu.booking.models.parse.User;
import edu.famu.booking.models.serializable.SerializableUser;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.parse4j.Parse;
import org.parse4j.ParseException;
import org.parse4j.ParseQuery;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserService {
    protected final Log logger = LogFactory.getLog(this.getClass()); //used to write to the console

    public ArrayList<User> retrieveUsers(String sort)
    {

        logger.info(Parse.isIsRootMode());
        final ArrayList<User> users = new ArrayList<>();

        ParseQuery<User> query = ParseQuery.getQuery(User.class);
        List<User> list = null;

        try {

            if(sort.equals("asc")){
                list = query.orderByAscending("username").find();
            } else if (sort.equals("dsc")) {
                list = query.orderByDescending("username").find();
            }

            for (User p : list) {
                //logger.info(p.toString()); //use if you want to see your products in the console
                users.add(p);
            }
        }
        catch(Exception e)
        {
            logger.error("Error occurred", e);
        }
        logger.info(users.size());
        return users;
    }

    public User getUserById(String id)
    {
        User user = null;

        //defines the query for the product class
        ParseQuery<User> query = ParseQuery.getQuery(User.class);

        try{
            user = query.get(id); //gets a single record based on objectId
        }catch (ParseException e)
        {
            e.printStackTrace();
        }

        return user;
    }


    public String addUser(SerializableUser user)
    {
        String message; //message we will return to the user

        //REMAINING CODE GOES HERE
        User parseUser = new User(); //Parse Product Object

//set the value of each of the fields
        parseUser.setUsername(user.getUsername());
        parseUser.setEmail(user.getEmail());
        parseUser.setCountry(user.getCountry());
        parseUser.setImg(user.getImg());
        parseUser.setCity(user.getCity());
        parseUser.setPhone(user.getPhone());
        parseUser.setIsAdmin(user.getIsAdmin());

        try {
            parseUser.save(); //runs the query to insert the new value
            message = "User Created"; //set success the return message

        } catch (ParseException e) {
            e.printStackTrace(); //print the error to the console.
            // set the error return message
            message = "Error creating user. " + e.getMessage();
        }
        return message;
    }

    public String updateUser(String id, Map<String, Object> user)
    {
        String message;

        //defines the query for the product class
        ParseQuery<User> query = ParseQuery.getQuery(User.class);

        try {
            User use = query.get(id); // retrieves the room by it's objectid
            user.forEach((k,v) -> {
                if(Objects.equals(k, "username"))
                    use.put(k,(String)v);
                else if (Objects.equals(k, "email"))
                    use.put(k, (String) v);
                else if (Objects.equals(k, "country"))
                    use.put(k, (String) v);
                else if (Objects.equals(k, "img"))
                    use.put(k, (String) v);
                else if (Objects.equals(k, "city"))
                    use.put(k, (String) v);
                else if (Objects.equals(k, "phone"))
                    use.put(k, (String) v);
                else if (Objects.equals(k, "isAdmin"))
                    use.put(k, (boolean) v);
            });
            use.save(); // execute update query
            message = "User updated."; //success message

        } catch (ParseException e) {
            e.printStackTrace();
            message = "Error updating user. " + e.getMessage(); // failure message
        }

        return message;
    }

    public String removeUser(String id)
    {
        String message;
        ParseQuery<User> query = ParseQuery.getQuery(User.class);

        try
        {
            User user = query.get(id); //get product by id
            user.delete(); //delete product
            message = "User " + id + " deleted."; //success message
        } catch (ParseException e) {
            e.printStackTrace();
            message = "Error while deleting user. " + e.getMessage(); //error message
        }
        return message;
    }
}