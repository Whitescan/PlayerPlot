package de.whitescan.playerplot.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import org.bukkit.scheduler.BukkitRunnable;

import de.whitescan.playerplot.PlayerPlot;
import de.whitescan.playerplot.config.PluginConfig;
import lombok.Getter;

public class DatabaseConnection {

	@Getter
	private static DatabaseConnection instance = new DatabaseConnection();

	private String host;
	private int port;
	private String database;
	private String username;
	private String password;

	private boolean ssl;
	private static KeepAlive keepAlive;

	private static Connection connection;

	private DatabaseConnection() {
		this.host = PluginConfig.getHost();
		this.port = Integer.parseInt(PluginConfig.getPort());
		this.database = PluginConfig.getDatabase();
		this.username = PluginConfig.getUsername();
		this.password = PluginConfig.getPassword();
		this.ssl = PluginConfig.isSsl();
		DatabaseConnection.keepAlive = new KeepAlive();
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

	public Connection getConnection() throws SQLException, ClassNotFoundException {

		if (connection == null || connection.isClosed())
			DatabaseConnection.connection = DriverManager.getConnection(
					"jdbc:mysql://" + this.host + ":" + this.port + "/" + this.database + "?useSSL=" + ssl,
					this.username, this.password);

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
				Statement statement = getConnection().createStatement();
				statement.executeQuery(query);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

}