package me.Tiernanator.AttackBar;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

public class AttackBar {

	private static List<AttackBar> allAttackBars = new ArrayList<AttackBar>();
	
	public static List<AttackBar> getAttackBars() {
		return allAttackBars;
	}
	
	public static AttackBar getAttackBar(Player player) {
		
		for(AttackBar attackBar : getAttackBars()) {
			
			Player owner = attackBar.getPlayer();
			if(owner.equals(player)) {
				return attackBar;
			}
			
		}
		return null;
		
	}
	
	public static void addAttackBar(AttackBar attackBar) {
		
		List<AttackBar> allBars = new ArrayList<AttackBar>();
		allBars = getAttackBars();
		if(!allBars.contains(attackBar)) {
			allBars.add(attackBar);
		}
		setAllAttackBars(allBars);
	}
	
	private static void setAllAttackBars(List<AttackBar> allBars) {
		
		if(allBars == null || allBars.isEmpty()) {
			
			allAttackBars.clear();
			
		} else {
			allAttackBars = allBars;
		}
		
	}
	
	private Player player;
	private BossBar attackBar;
	
	public AttackBar(Player player) {
		
		this.player = player;
		this.attackBar = createAttackBar();
		
		getAttackBar().addPlayer(getPlayer());
		
		addAttackBar(this);
	}
	
	public Player getPlayer() {
		return this.player;
	}
	
	public BossBar getAttackBar() {
		return this.attackBar;
	}
	
	public void updateBar() {
		
		this.attackBar = createAttackBar();
		
	}
	
	public void remove() {
		
		BossBar attackBar = getAttackBar();
		Player player = getPlayer();
		attackBar.removePlayer(player);;
		
	}
	
	@SuppressWarnings("deprecation")
	private BossBar createAttackBar() {
		
		Player player = getPlayer();
		List<Entity> nearbyEntities = player.getNearbyEntities(100, 100, 100);
		LivingEntity nearestEntity = null;
		double distance = 999;
		Location playerLocation = player.getLocation();
		for(Entity entity : nearbyEntities) {
			
			Location entityLocation = entity.getLocation();
			double entityDistance = playerLocation.distance(entityLocation);
			if(entityDistance < distance && entity instanceof LivingEntity) {
				distance = entityDistance;
				nearestEntity = (LivingEntity) entity;
			}
			
		}
		
		if(nearestEntity.getType() == EntityType.UNKNOWN) {
			return null;
		}
		
		String title = nearestEntity.getName();
		BarColor colour = BarColor.RED;
		
		BarStyle style = BarStyle.SEGMENTED_20;
		
		BossBar attackBar = Bukkit.createBossBar(title, colour, style);
		
		double maxHealth = nearestEntity.getMaxHealth();
		double health = nearestEntity.getHealth();
		double progress = health / maxHealth;
		attackBar.setProgress(progress);
		
		return attackBar;
		
	}
	
}
