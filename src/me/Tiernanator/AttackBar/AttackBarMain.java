package me.Tiernanator.AttackBar;

import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class AttackBarMain extends JavaPlugin {
	
	@Override
	public void onEnable() {
		registerCommands();
//		registerEvents();
		
		for(Player player : getServer().getOnlinePlayers()) {
			AttackBar attackBar = new AttackBar(player);
			attackBar.updateBar();
		}
	}

	@Override
	public void onDisable() {

		for(Player player : getServer().getOnlinePlayers()) {
			AttackBar attackBar = AttackBar.getAttackBar(player);
			if(attackBar == null) {
				continue;
			}
			attackBar.remove();
		}
		
	}

	public void registerCommands() {
//		getCommand("test").setExecutor(new Test(this));
	}

//	public void registerEvents() {
//		PluginManager pm = getServer().getPluginManager();
//		
//		pm.registerEvents(new TNTEntityExplodeInCoreZone(this), this);
//	}


}
