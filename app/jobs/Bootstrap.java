package jobs;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.List;

import models.Message;
import models.User;
import play.jobs.Job;
import play.jobs.On;
import play.jobs.OnApplicationStart;
import play.test.Fixtures;

@OnApplicationStart
public class Bootstrap extends Job{
	
	public void doJob(){
		if(User.count() == 0){
			Fixtures.load("app.yml");
		}
	}
}
