package nabi.comworker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class WorkersMeetServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(WorkersMeetServiceApplication.class, args);
		System.out.println("Workers Meet Service Application started");
	}

}
