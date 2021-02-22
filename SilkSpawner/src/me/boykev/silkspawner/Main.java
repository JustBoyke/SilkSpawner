package me.boykev.silkspawner;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import net.md_5.bungee.api.ChatColor;

public class Main extends JavaPlugin implements Listener{
	
	public ArrayList<Material> pickaxe = new ArrayList<Material>();
	
	public void onEnable() {
		Bukkit.getPluginManager().registerEvents(this, this);
		pickaxe.add(Material.DIAMOND_PICKAXE);
		pickaxe.add(Material.NETHERITE_PICKAXE);
	}
	public void onDisable() {
		pickaxe.clear();
	}
	
	@EventHandler
	public void onSpawnerBreak(BlockBreakEvent e) {
		if(!(e.getBlock().getType() == Material.SPAWNER)) {
			return;
		}
		CreatureSpawner s = (CreatureSpawner) e.getBlock().getState();
		ItemStack hand = e.getPlayer().getInventory().getItemInMainHand();
		Material hm = hand.getType();
		if(hand.getItemMeta() == null) {
			return;
		}
		ItemMeta ih = hand.getItemMeta();
		EntityType en = s.getSpawnedType();
		if(pickaxe.contains(hm)) {
			if(ih.hasEnchant(Enchantment.SILK_TOUCH)) {
				ItemStack spawner = new ItemStack(Material.SPAWNER);
				ItemMeta im = spawner.getItemMeta();
				ArrayList<String> lore = new ArrayList<String>();
				lore.add(ChatColor.AQUA + "Spawner Type:");
				lore.add(en.toString());
				im.setLore(lore);
				spawner.setItemMeta(im);
				e.getPlayer().getInventory().addItem(spawner);
				e.getPlayer().sendMessage(ChatColor.GREEN + "Je hebt een " + ChatColor.AQUA + en.toString() + " spawner gehakt!");
				return;
			}
		}
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onSpawnerPlace(BlockPlaceEvent e) {
		
		if(e.getBlock().getType() == Material.SPAWNER) {
			Player p = e.getPlayer();
			ItemStack is = p.getInventory().getItemInMainHand();
			ItemMeta im = is.getItemMeta();
			List<String> lore = im.getLore();
			CreatureSpawner s = (CreatureSpawner) e.getBlock().getState();
			s.setCreatureTypeByName(lore.get(1));
			s.update();
			p.sendMessage(ChatColor.GREEN + "Je hebt een " + ChatColor.AQUA + lore.get(1) + " spawner geplaatst!");
		}
	}
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(cmd.getName().equalsIgnoreCase("silk")) {
			Player p = (Player) sender;
			p.sendMessage(ChatColor.RED + "Je dacht dat dit gedownload was he? Nope, zelf gemaakt door de enige echte: " + ChatColor.GOLD + "boykev");
			p.sendMessage(ChatColor.AQUA + "© Kopieren, Nameken of herdistributie verboden!");
			return false;
		}
		return false;
	}
	
}
