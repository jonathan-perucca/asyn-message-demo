package models;

import java.util.Date;

import javax.persistence.Entity;

import play.data.binding.As;
import play.db.jpa.Model;

@Entity
public class User extends Model{

	public String name;
	@As("yyyy-MM-dd")
	public Date birth;
	public Boolean isAdmin;
	
	public User(Long id) {
		this.id = id;
	}
}
