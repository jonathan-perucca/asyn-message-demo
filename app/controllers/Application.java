package controllers;

import java.util.List;

import models.Message;
import models.User;
import play.mvc.Controller;

public class Application extends Controller {

    public static void index() {
    	List<User> listUsers = User.findAll();
    	render(listUsers);
    }

    public static void send(Long userFrom, Long userTo, String subject, String content){
    	Message.send(userFrom, userTo, subject, content);
    	show(userFrom);
    }
    
    public static void delete(Long userId, Long id){
    	Message message = Message.findById(id);
    	message.delete();
//    	show(userId);
    }
    
    public static void show(Long id){
    	List<Message> listMessages = Message.findMessageByUserId(id);
    	renderJSON(listMessages);
    }
}