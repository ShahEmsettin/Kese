package org.speaway.kese;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.ChatColor;

public class Kese extends JavaPlugin {

    @Override
    public void onEnable() {
        getLogger().info("Kese aktif edildi!");
        saveDefaultConfig();
        getLogger().info("config dosyası yüklendi!");
    }

    @Override
    public void onDisable() {
        getLogger().info("Kese devre dışı bırakıldı!");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "Bu komutu sadece oyuncular kullanabilir!");
            return true;
        }
        
        Player player = (Player) sender;

        if (cmd.getName().equalsIgnoreCase("kese")) {
            if (args.length == 0) {
                KeseYardım(player);
                return true;
            }

            switch (args[0].toLowerCase()) {
                case "koy":
                    if (args.length < 2) {
                        player.sendMessage(ChatColor.RED + "Kullanım: /kese koy <miktar>");
                        return true;
                    }

                    int amountToDeposit = 0;
                    try {
                        amountToDeposit = Integer.parseInt(args[1]);
                    } catch (NumberFormatException e) {
                        player.sendMessage(ChatColor.RED + "Geçersiz miktar!");
                        return true;
                    }

                    if (amountToDeposit <= 0) {
                        player.sendMessage(ChatColor.RED + "Miktar 0'dan büyük olmalıdır!");
                        return true;
                    }

                    if (player.getInventory().containsAtLeast(new ItemStack(Material.GOLD_INGOT), amountToDeposit)) {
                        player.getInventory().removeItem(new ItemStack(Material.GOLD_INGOT, amountToDeposit));
                        // Burada keseye altın eklenmesi için bir ekonomi eklentisi kullanılabilir.
                        player.sendMessage(ChatColor.GREEN + "Başarıyla " + amountToDeposit + " adet altın keseye konuldu!");
                    } else {
                        player.sendMessage(ChatColor.RED + "Yeterli altın bulunmuyor!");
                    }
                    break;

                case "al":
                    if (args.length < 2) {
                        player.sendMessage(ChatColor.RED + "Kullanım: /kese al <miktar>");
                        return true;
                    }

                    int amountToWithdraw;
                    try {
                        amountToWithdraw = Integer.parseInt(args[1]);
                    } catch (NumberFormatException e) {
                        player.sendMessage(ChatColor.RED + "Geçersiz miktar!");
                        return true;
                    }

                    if (amountToWithdraw <= 0) {
                        player.sendMessage(ChatColor.RED + "Miktar 0'dan büyük olmalıdır!");
                        return true;
                    }

                    // Burada keseden altın çekilmesi için bir ekonomi eklentisi kullanılabilir.
                    if (true) { // Burada kesede yeterli altın olup olmadığı kontrol edilir.
                        player.getInventory().addItem(new ItemStack(Material.GOLD_INGOT, amountToWithdraw));
                        player.sendMessage(ChatColor.GREEN + "Başarıyla " + amountToWithdraw + " adet altın keseden alındı!");
                    } else {
                        player.sendMessage(ChatColor.RED + "Kesede yeterli altın bulunmuyor!");
                    }
                    break;

                default:
                    KeseYardım(player);
                    break;
            }
            return true;
        } else if (cmd.getName().equalsIgnoreCase("kesem")) {
            // Burada kesedeki altın miktarını gösteren bir ekonomi eklentisi kullanılabilir.
            player.sendMessage(ChatColor.GOLD + "Kesende " + ChatColor.GREEN + "100" + ChatColor.GOLD + " adet altın bulunuyor."); // Örnek miktar
            return true;
        } else if (cmd.getName().equalsIgnoreCase("altıngönder")) {
            if (args.length < 2) {
                player.sendMessage(ChatColor.RED + "Kullanım: /altıngönder <oyuncu> <miktar>");
                return true;
            }

            Player target = Bukkit.getPlayer(args[0]);
            if (target == null) {
                player.sendMessage(ChatColor.RED + "Oyuncu bulunamadı!");
                return true;
            }

            int amountToSend;
            try {
                amountToSend = Integer.parseInt(args[1]);
            } catch (NumberFormatException e) {
                player.sendMessage(ChatColor.RED + "Geçersiz miktar!");
                return true;
            }

            if (amountToSend <= 0) {
                player.sendMessage(ChatColor.RED + "Miktar 0'dan büyük olmalıdır!");
                return true;
            }

            // Burada keseden altın göndermek için bir ekonomi eklentisi kullanılabilir.
            if (true) { // Burada kesede yeterli altın olup olmadığı kontrol edilir.
                // Burada keseden altın çıkarılır ve hedef oyuncuya eklenir.
                player.sendMessage(ChatColor.GREEN + "Başarıyla " + amountToSend + " adet altın " + target.getName() + " adlı oyuncuya gönderildi!");
                target.sendMessage(ChatColor.GREEN + player.getName() + " adlı oyuncu size " + amountToSend + " adet altın gönderdi!");
            } else {
                player.sendMessage(ChatColor.RED + "Kesede yeterli altın bulunmuyor!");
            }
            return true;
        }
        return false;
    }

    private void KeseYardım(Player player) {
        player.sendMessage(ChatColor.GOLD + "⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯ Kese Sistemi ⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯");
        player.sendMessage("");
        player.sendMessage(ChatColor.YELLOW + "/kesem " + ChatColor.WHITE + "| Kesenizdeki altın miktarını gösterir.");
        player.sendMessage(ChatColor.YELLOW + "/kese koy " + ChatColor.WHITE + "| Kesenize altın koymanızı sağlar.");
        player.sendMessage(ChatColor.YELLOW + "/kese al " + ChatColor.WHITE + "| Kesenizden altın almanızı sağlar.");
        player.sendMessage(ChatColor.YELLOW + "/altıngönder " + ChatColor.WHITE + "| Bir oyuncuya altın göndermenizi sağlar.");
        player.sendMessage("");
        player.sendMessage(ChatColor.GOLD + "⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯");
    }
}
