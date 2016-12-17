package com.mcr.crates;


import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin  {
	
	Map<UUID, Integer> crateUsesMap;
	Random random;
	
	String[] prizes = {"Material:Diamond", "Material:Dirt", "Rank:Example Rank"};
	
	public void onEnable(){
		this.random = new Random();
		this.crateUsesMap = new HashMap<UUID, Integer>();
	}
	
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args){
		
		if(!(sender instanceof Player)){
			return true;
		}
		
		Player p = (Player) sender;
		if (command.getName().equalsIgnoreCase("crate")){
			activateCrate(p);
		}
		
		return false;
		
	}
	
	void activateCrate(Player p){
		
		if (this.crateUsesMap.get(p.getUniqueId()) == null) { this.crateUsesMap.put(p.getUniqueId(), 0); }
		this.crateUsesMap.put(p.getUniqueId(), this.crateUsesMap.get(p.getUniqueId()) + 1);
		
		String prize = choosePrize();
		String[] prizeIndex = prize.split("\\:");
		if (prize.contains("Rank")){
			Bukkit.broadcastMessage("The user " + p.getName() + " has won the rank" + prizeIndex[1]);
			
			//TODO: Add ranks.
		} else if (prize.contains("Material")){
			Bukkit.broadcastMessage("The user " + p.getName() + " has won the item" + prizeIndex[1]);
			p.getInventory().addItem(new ItemStack(Material.valueOf(prizeIndex[1].toUpperCase())));
		}
	}
	
	String choosePrize(){
		return prizes[this.random.nextInt(prizes.length)];
		
	}

}
