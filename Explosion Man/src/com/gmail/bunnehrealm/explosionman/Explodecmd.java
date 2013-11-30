
/*Explosion Man for general explosions on Bukkit
    Copyright (C) 2013  Rory Finnegan
    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/

package com.gmail.bunnehrealm.explosionman;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Explodecmd implements CommandExecutor {
	public MainClass MainClass;
	public Explodecmd(MainClass mainClass) {
        this.MainClass = mainClass;
    }

	@Override
	public boolean onCommand(CommandSender sender, Command label,
			String string, String[] args) {
		if (string.equalsIgnoreCase("explode")) {
			if(!(sender instanceof Player)){
				MainClass.getLogger().info("That is a player only command!");
				return false;
			}
		else if(sender instanceof Player){
			Player player = (Player) sender;
			if (player.hasPermission("explosionman.explode")|| player.isOp()) {
				Location pLocation = player.getLocation();
				double pX = pLocation.getX();
				double pY = pLocation.getY();
				double pZ = pLocation.getZ();
				World w = player.getWorld();
				if (args.length == 0) {
					if(MainClass.getConfig().getBoolean("explodemsg")){
						String explodemsg = MainClass.getConfig().getString("explodetext").replaceAll("(&([a-f0-9]))", "\u00A7$2");
						player.sendMessage(explodemsg);
					}
					w.createExplosion(pX, pY, pZ, MainClass.getConfig().getInt("explodepower"),MainClass.getConfig().getBoolean("explodefire"), MainClass.getConfig().getBoolean("explodeblocks") );
					if(player.hasPermission("explosionman.explodelightning")){
						w.strikeLightningEffect(pLocation);
					}
				} else if (args.length == 1) {
					try {
						@SuppressWarnings("unused")
						float explosionPower = Float.parseFloat(args[0]);
					} catch (NumberFormatException e) {
						player.sendMessage(ChatColor.RED + "The proper use is "
								+ ChatColor.AQUA + "/explode [number]");
						return false;
					}
					float explosionPower = Float.parseFloat(args[0]);
					if (explosionPower > MainClass.getConfig().getInt("explodebigpower")) {
						if (player.hasPermission("explosionman.explodebig") || player.isOp()) {
						} else {
							player.sendMessage(ChatColor.RED
									+ "You don't have permission to create such a large explosion!");
							return false;
						}
					}
					if(MainClass.getConfig().getBoolean("explodemsg")){
						String explodemsg = MainClass.getConfig().getString("explodetext").replaceAll("(&([a-f0-9]))", "\u00A7$2");
						player.sendMessage(explodemsg);
					}
					w.createExplosion(pX, pY, pZ,explosionPower ,MainClass.getConfig().getBoolean("explodefire"), MainClass.getConfig().getBoolean("explodeblocks") );
					if(player.hasPermission("explosionman.explodelightning")){
						w.strikeLightningEffect(pLocation);
					}
				}
				else if(args.length > 1){
					player.sendMessage(ChatColor.RED + "The proper use is "
							+ ChatColor.AQUA + "/explode [number]");
				}

			} else {
				player.sendMessage(ChatColor.RED + "You cannot explode!");
			}
			
			return false;
		}
		}
		return false;

	}
}

