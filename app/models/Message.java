package models;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import play.data.validation.Required;
import play.db.jpa.Model;

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
	public void setId(Long id) {
		this.id = id;
	}
	public Boolean getIsNew() {
		return isNew;
	}
	public void setIsNew(Boolean isNew) {
		this.isNew = isNew;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Date getDateCreation() {
		return dateCreation;
	}
	public void setDateCreation(Date dateCreation) {
		this.dateCreation = dateCreation;
	}
	public User getDestinataire() {
		return destinataire;
	}
	public void setDestinataire(User destinataire) {
		this.destinataire = destinataire;
	}
	public User getExpediteur() {
		return expediteur;
	}
	public void setExpediteur(User expediteur) {
		this.expediteur = expediteur;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	
	/* CRUD Functionnalities */
	
	public static void send(Long idFromUser, Long idToUser, String subject, String content){
		User userFrom = new User(idFromUser);
		User userTo = new User(idToUser);
		Message message = new Message(userFrom, userTo, subject, content);
		message.setDateCreation(new Date());
		message.setIsNew(true);
		message.save();
	}
	
	public static List<Message> findMessageByUserId(Long id){
		return Message.find("select mess from Message mess, User user " +
    			"where mess.destinataire.id = user.id and user.id = ?", id).fetch(100);
	}
	
	public String toString(){
		return "From " + expediteur + " To " + destinataire +
				" Subject : " + subject  + " Content " + content + 
				( (isNew) ? "New Message" : "Old Message" );
	}
}
