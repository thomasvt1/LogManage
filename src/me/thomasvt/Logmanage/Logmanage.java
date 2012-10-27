package me.thomasvt.Logmanage;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Logger;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class Logmanage extends JavaPlugin implements Listener {

	Logger log = Logger.getLogger("Minecraft");
	File logfile = new File("../server.log");
	boolean delete = false;

	public void clearLog() {
		getLogger().info("Starting Clearing of Log.");
		try {
			BufferedWriter out = new BufferedWriter(
					new FileWriter("server.log"));
			out.write("");
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		getLogger().info("Log file is successfully cleared!");
	}

	public void metrics() {
		try {
			MetricsLite metrics = new MetricsLite(this);
			metrics.start();
			getLogger().info("Metrics Succesfull");
		} catch (IOException e) {
			getLogger().info("O.o something went frong with Metrics :-(");
		}
	}

	public void shedule() {
		boolean clear = getConfig().getBoolean("autoclear");
		if (!clear == true) {
			return;
		}
		int timer = getConfig().getInt("timer");
		long time = timer * 20 * 60;
		getServer().getScheduler().scheduleSyncRepeatingTask(this,
				new Runnable() {

					@Override
					public void run() {
						clearLog();
					}
				}, 0, time);
	}
	public void configCheck() {
		File file = new File(getDataFolder() + File.separator + "config.yml");
		if (!file.exists()) {
			loadConfiguration();
			reloadConfig();
		}
	}
	public void loadConfiguration() {
		getConfig().options().copyDefaults(true);
		saveConfig();
	}

	public void deleteLog() {
		try {
			BufferedWriter out = new BufferedWriter(
					new FileWriter("server.log"));
			out.write("");
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void onEnable() {
		getServer().getPluginManager().registerEvents(this, this);
		configCheck();
		shedule();
		metrics();
	}

	public void onDisable() {
		getLogger().info("LogManage disabled!");
		if (delete == true)
			deleteLog();
	}

	public boolean onCommand(CommandSender sender, Command cmd,
			String commandLabel, String[] args) {

		if (cmd.getName().equalsIgnoreCase("logmanagereload")) {
			configCheck();
			sender.sendMessage("The config has been reloaded");
		}

		if (cmd.getName().equalsIgnoreCase("logmanage")) {

			Player player = (Player) sender;
			if (player.hasPermission("logmanage.about")) {
				sender.sendMessage(ChatColor.YELLOW
						+ "-----------------------------------------");
				sender.sendMessage(ChatColor.GREEN
						+ "-This server is using LogManage!");
				sender.sendMessage(ChatColor.YELLOW
						+ "-----------------------------------------");
				sender.sendMessage(ChatColor.GREEN + "-@Thomasvt");
				sender.sendMessage(ChatColor.YELLOW
						+ "-----------------------------------------");
			} else {
				sender.sendMessage(ChatColor.YELLOW
						+ "-----------------------------------------");
				sender.sendMessage(ChatColor.GREEN
						+ "-This server is using LogManage!");
				sender.sendMessage(ChatColor.YELLOW
						+ "-----------------------------------------");
				sender.sendMessage(ChatColor.GREEN + "-@Thomasvt");
				sender.sendMessage(ChatColor.YELLOW
						+ "-----------------------------------------");
			}
		}

		if (cmd.getName().equalsIgnoreCase("logdelete")) {
			if (delete == false) {
				sender.sendMessage("[" + ChatColor.RED + "Log"
						+ ChatColor.GREEN + "Manage" + ChatColor.WHITE
						+ "] Log delete : true ");
				delete = true;
				return true;
			}
			if (delete == true) {
				sender.sendMessage("[" + ChatColor.RED + "Log"
						+ ChatColor.GREEN + "Manage" + ChatColor.WHITE
						+ "] Log delete : false ");
				delete = false;
				return true;
			}
		}

		if (cmd.getName().equalsIgnoreCase("logclear")) {
			clearLog();
			return true;
		}
		return false;
	}
}