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
	
	public User(){}
	
	public User(Long id) {
		this.id = id;
	}

	public void setId(Long id){
		this.id = id;
	}
	public Date getBirth() {
		return birth;
	}
	public void setBirth(Date birth) {
		this.birth = birth;
	}
	public Boolean getIsAdmin() {
		return isAdmin;
	}
	public void setIsAdmin(Boolean isAdmin) {
		this.isAdmin = isAdmin;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public String toString(){
		return "User : { " + this.name + ", " + this.birth + ", " + ((isAdmin) ? "Admin" : "Not Admin") + " }";
	}
}
