package de.whitescan.playerplot.storage;

import org.bukkit.scheduler.BukkitRunnable;

import de.whitescan.playerplot.PlayerPlot;
import de.whitescan.playerplot.config.PluginConfig;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseConnection {

	private static KeepAlive keepAlive;
	private static DatabaseConnection databaseConnection = new DatabaseConnection();
	private static Connection connection;
	private String host;
	private int port;
	private String database;
	private String username;
	private String password;
	private boolean ssl;

	private DatabaseConnection() {
		this.host = PluginConfig.getHost();
		this.port = Integer.parseInt(PluginConfig.getPort());
		this.database = PluginConfig.getDatabase();
		this.username = PluginConfig.getUsername();
		this.password = PluginConfig.getPassword();
		this.ssl = PluginConfig.isSsl();
		keepAlive = new KeepAlive();
	}

	public static DatabaseConnection getInstance() {
		return databaseConnection;
	}

	public static void shutdown() {
		keepAlive.cancel();
		try {
			if (connection != null && !connection.isClosed())
				connection.close();
		} catch (SQLException e) {
			// do nothing
		}
	}

	public void openConnection() throws SQLException, ClassNotFoundException {
		synchronized (this) {
			if (connection != null && !connection.isClosed())
				return;
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection(
					"jdbc:mysql://" + this.host + ":" + this.port + "/" + this.database + "?useSSL=" + ssl,
					this.username, this.password);
		}
	}

	public Connection getConnection() {
		return connection;
	}

	private class KeepAlive extends BukkitRunnable {

		private String query = "SELECT 1 FROM PPlot;";

		public KeepAlive() {
			runTaskTimerAsynchronously(PlayerPlot.getPlugin(), 30 * 20, 30 * 20);
		}

		@Override
		public void run() {
			try {
				openConnection();
				Statement statement = connection.createStatement();
				statement.executeQuery(query);
			} catch (Exception e) {
				// do nothing
			}
		}
	}

}