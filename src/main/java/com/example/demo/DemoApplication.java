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
	private static Database db = new Database();

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);

		String url = "jdbc:sqlite:src/main/resources/db/database.db";
		try {
			Connection conn = DriverManager.getConnection(url);
			if (conn != null) {
				System.out.println("Connected to the database");

				Statement stmt = conn.createStatement();
				String sql = "CREATE TABLE IF NOT EXISTS users (id integer PRIMARY KEY, username text NOT NULL UNIQUE, password text NOT NULL)";
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
		Connection conn = db.getConnection();
		try {
			if (!db.isUserExists(user.getUsername())) {
				db.registerUser(user.getUsername(), user.getPassword());
				model.addAttribute("user", user);
				System.out.println("New user registered");
				return "register_success";
			} else {
				System.out.println("User already exists");
				return "register_form";
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return "error";
	}

}


