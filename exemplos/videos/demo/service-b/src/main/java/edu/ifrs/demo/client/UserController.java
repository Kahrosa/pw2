package edu.ifrs.demo.client;

import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import edu.ifrs.demo.model.User;

@Stateless
@Path("/user")
public class UserController {
    
    @POST
    @Consumes("application/x-www-form-urlencoded")
    @Produces(MediaType.APPLICATION_JSON)
    public User createUser(@FormParam("name") String name){
        User user = new User();
        user.setName(name);
        return user;
    }

}
