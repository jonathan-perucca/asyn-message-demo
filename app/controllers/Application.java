package controllers;

import java.util.List;

import models.Message;
import models.User;
import play.db.jpa.Transactional;
import play.mvc.Controller;

public class Application extends Controller {

    public static void index() {
    	List<User> listUsers = User.findAll();
    	render(listUsers);
    }

    public static void send(Message message){
    	Message.send(message);
    	show(message.expediteur.id);
    }
    
    public static void delete(Message message){
    	Message attached = Message.findById(message.id);
    	attached.delete();
    }
    
    public static void show(Long id){
    	List<Message> listMessages = Message.findMessageByUserId(id);
    	renderJSON(listMessages);
    }
}