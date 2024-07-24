package ru.gb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import ru.gb.model.*;
import ru.gb.repository.*;

import java.time.LocalDate;
import java.util.concurrent.ThreadLocalRandom;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		ApplicationContext ctx = SpringApplication.run(Application.class, args);

		UserRepository userRepository = ctx.getBean(UserRepository.class);
		RoleRepository roleRepository = ctx.getBean(RoleRepository.class);
		UserRoleRepository userRoleRepository = ctx.getBean(UserRoleRepository.class);

		Role adminRole = new Role();
		adminRole.setName("ADMIN");
		Role userRole = new Role();
		userRole.setName("USER");
		roleRepository.save(adminRole);
		roleRepository.save(userRole);

		User admin = new User();
		admin.setLogin("admin");
		admin.setPassword("$2a$12$LbAPCsHn8ZN5MUDqDmIX7e9n1YlDkCxEt0lW3Q2WuW0M1vteo8jvG"); // admin
		userRepository.save(admin);

		User user = new User();
		user.setLogin("user");
		user.setPassword("$2a$12$.dlnBAYq6sOUumn3jtG.AepxdSwGxJ8xA2iAPoCHSH61Vjl.JbIfq"); // user
		userRepository.save(user);

		User anonymous = new User();
		anonymous.setLogin("anon");
		anonymous.setPassword("$2a$12$tPkyYzWCYUEePUFXUh3scesGuPum1fvFYwm/9UpmWNa52xEeUToRu"); // anon
		userRepository.save(anonymous);

		UserRole adminRoleLink = new UserRole();
		adminRoleLink.setUserId(admin.getId());
		adminRoleLink.setRoleId(adminRole.getId());
		userRoleRepository.save(adminRoleLink);

		UserRole userRoleLink = new UserRole();
		userRoleLink.setUserId(admin.getId());
		userRoleLink.setRoleId(userRole.getId());
		userRoleRepository.save(userRoleLink);

		UserRole userRoleLink2 = new UserRole();
		userRoleLink2.setUserId(user.getId());
		userRoleLink2.setRoleId(userRole.getId());
		userRoleRepository.save(userRoleLink2);

		ProjectRepository projectRepo = ctx.getBean(ProjectRepository.class);
		for (int i = 1; i <= 5; i++) {
			Project project = new Project();
			project.setName("Project #" + i);
			projectRepo.save(project);
		}

		EmployeeRepository employeeRepo = ctx.getBean(EmployeeRepository.class);
		for (int i = 1; i <= 5; i++) {
			Employee employee = new Employee();
			employee.setName("Employee #" + i);
			employeeRepo.save(employee);
		}

		TimesheetRepository timesheetRepo = ctx.getBean(TimesheetRepository.class);
		LocalDate createdAt = LocalDate.now();
		for (int i = 1; i <= 10; i++) {
			createdAt = createdAt.plusDays(1);

			Timesheet timesheet = new Timesheet();
			timesheet.setProjectId(ThreadLocalRandom.current().nextLong(1, 6));
			timesheet.setEmployeeId(ThreadLocalRandom.current().nextLong(1, 6));
			timesheet.setCreatedAt(createdAt);
			timesheet.setMinutes(ThreadLocalRandom.current().nextInt(100, 1000));

			timesheetRepo.save(timesheet);
		}
	}
}
