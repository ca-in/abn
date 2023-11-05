package com.example.demo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.sql.*;

@SpringBootApplication(scanBasePackages = {"com.example.demo"})
@Controller
public class DemoApplication {
	private static final Database db = new Database();

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);

		String url = "jdbc:sqlite:src/main/resources/db/database.db";
		try {
			Connection conn = DriverManager.getConnection(url);
			if (conn != null) {
				System.out.println("Connected to the database");

				Statement stmt = conn.createStatement();
				String sql = "CREATE TABLE IF NOT EXISTS users (id integer PRIMARY KEY, username text NOT NULL UNIQUE, password text NOT NULL, online integer DEFAULT 0)";
				stmt.execute(sql);
				db.setConnection(conn);
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	@GetMapping("/register")
	public String showForm(Model model) {
		model.addAttribute("user", new User());
		return "register_form";
	}

	@PostMapping("/register")
	public String submitForm(@ModelAttribute("user") User user, Model model) {
		try {
			String username = user.getUsername();
			String password = user.getPassword();

			if (!db.isUserExists(username)) {
				db.registerUser(username, password);;
				model.addAttribute("user", user);
				System.out.println("New user " + username + " registered");
				return "register_success";
			} else {
				System.out.println("User " + username + " already exists");
				return "register_form";
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return "error";
	}

	@GetMapping("/login")
	public String showLoginForm(Model model) {
		model.addAttribute("user", new User());
		return "login_form";
	}

	@PostMapping("/login")
	public String submitLoginForm(@ModelAttribute("user") User user, Model model) {
		try {
			String username = user.getUsername();
			String password = user.getPassword();

			if (db.isUserExists(username)) {
				if(db.isPasswordCorrect(username, password)) {
					db.loginUser(username, password);
					model.addAttribute("user", user);
					System.out.println("User " + username + " logged in");
					return "login_success";
				} else {
					System.out.println("Password is incorrect");
					return "invalid_password";
				}
			} else {
				System.out.println("User " + username + " does not exist");
				return "invalid_user";
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return "error";
	}

	@GetMapping("/recover")
	public String showRecoverForm(Model model) {
		model.addAttribute("user", new User());
		return "recover_form";
	}

	@PostMapping("/recover")
	public String submitRecoverForm(@ModelAttribute("user") User user, Model model) {
		try {
			String username = user.getUsername();
			String password = user.getPassword();

			if (db.isUserExists(username)) {
				String password_old = db.getPasswordOld(username);
				db.recoverUser(username, password);
				model.addAttribute("user", user);
				model.addAttribute("password_old", password_old);
				System.out.println("User " + username + " recovered");
				return "recover_success";
			} else {
				System.out.println("User " + username + " does not exist");
				return "invalid_user";
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return "error";
	}
}


