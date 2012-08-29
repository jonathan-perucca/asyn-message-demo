import java.util.Date;
import java.util.List;

import models.Message;
import models.User;

import org.junit.Test;

import play.mvc.Http.Response;
import play.test.FunctionalTest;

public class ApplicationTest extends FunctionalTest {

    @Test
    public void testThatIndexPageWorks() {
        Response response = GET("/");
        assertIsOk(response);
        assertContentType("text/html", response);
        assertCharset(play.Play.defaultWebEncoding, response);
    }
    
    @Test
    public void testUserExistInDB(){
    	long count = User.count();
    	System.out.println("UserCounter is " + count);
    	assertTrue(count == 2);
    }
    
    @Test
    public void testRetrieveUser(){
    	User user = User.findById((long) 1);
    	System.out.println("First User Name is " + user.name);
    	assertTrue(user.name.equalsIgnoreCase("Jonathan"));
    }
    
    @Test
    public void testGet(){
    	Response response = GET("/show/1");
    	assertIsOk(response);
    }
    
    @Test
    public void testSendAndRemoveMessage(){
    	List<User> users = User.findAll();
    	Message message = new Message();
    	message.expediteur = users.get(0);
    	message.destinataire = users.get(1);
    	message.subject = "Subject in test";
    	message.content = "Content in test";
    	message.dateCreation = new Date();
    	message.isNew = true;
    	long counterMessageBefore = Message.count();
    	Message.send(message);
    	long counterMessageAfter = Message.count();
    	assertNotSame(counterMessageAfter, counterMessageBefore);
    	message.delete();
    	counterMessageAfter = Message.count();
    	assertEquals(counterMessageAfter, counterMessageBefore);
    }
}