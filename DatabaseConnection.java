package code;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DatabaseConnection {	
                private static final String DB_URL = "jdbc:mysql://srv-bdens.insa-toulouse.fr:3306/projet_gei_016";
                private static final String USER = "projet_gei_016";
                private static final String PASSWORD = "Fahd7ohb";

                public static void insertUser(String name, String email, String password, String role) {
                    try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASSWORD)) {
                    	Class.forName("com.mysql.cj.jdbc.Driver");

                        String query = "INSERT INTO utilisateur (name, email, password, role) VALUES (?, ?, ?, ?)";
                        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                            preparedStatement.setString(1, name);
                            preparedStatement.setString(2, email);
                            preparedStatement.setString(3, password);
                            preparedStatement.setString(4, role);
                            preparedStatement.executeUpdate();
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    } catch (ClassNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
                }
            }

