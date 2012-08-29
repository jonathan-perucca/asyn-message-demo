package models;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import play.data.validation.Required;
import play.db.jpa.Model;
import play.db.jpa.Transactional;

@Entity
public class Message extends Model{

	@ManyToOne
	public User expediteur;
	@ManyToOne
	public User destinataire;
	
	@Required
	public String subject;
	
	@Required
	public String content;
	public Date dateCreation;
	public Boolean isNew;
	
	public Message() {}
	
	public Message(User expediteur, User destinataire, String subject, String content) {
		this.expediteur = expediteur;
		this.destinataire = destinataire;
		this.subject = subject;
		this.content = content;
	}
	
	/* CRUD Functionnalities */
	
	public static void send(Message message){
		message.save();
	}
	
	public static List<Message> findMessageByUserId(Long id){
		return Message.find("select mess from Message mess, User user " +
    			"where mess.destinataire.id = user.id and user.id = ?", id).fetch(100);
	}
}
