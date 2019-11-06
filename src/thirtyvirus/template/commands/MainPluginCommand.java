package thirtyvirus.template.commands;

import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import thirtyvirus.template.WorldOfBlocks;
import thirtyvirus.template.helpers.MenuUtils;
import thirtyvirus.template.helpers.Utilities;

import java.util.Arrays;

public class MainPluginCommand implements CommandExecutor{

    private WorldOfBlocks main = null;
    public MainPluginCommand(WorldOfBlocks main) { this.main = main; }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        // verify that the user has proper permissions
        if (!sender.hasPermission("wob.user")) {
            Utilities.warnPlayer(sender, Arrays.asList(main.getPhrase("no-permissions-message")));
            return true;
        }

        try {

            switch (args[0].toLowerCase()) {
                case "help":
                    help(sender);
                    break;
                case "info":
                    info(sender);
                    break;
                case "tutorial":
                    if (sender instanceof Player) MenuUtils.tutorialMenu((Player) sender);
                    else Utilities.warnPlayer(sender, Arrays.asList(main.getPhrase("no-console-message")));
                    break;

                // put plugin specific commands here
                case "toggle":
                    if (sender.hasPermission("wob.admin")) {
                        WorldOfBlocks.enabled = !WorldOfBlocks.enabled;
                        sender.sendMessage("enabled: " + WorldOfBlocks.enabled);
                    }
                    else Utilities.warnPlayer(sender, Arrays.asList(main.getPhrase("no-permissions-message")));
                    break;
                case "reload":
                    if (sender.hasPermission("wob.admin")) reload(sender);
                    else Utilities.warnPlayer(sender, Arrays.asList(main.getPhrase("no-permissions-message")));
                    break;
                default:
                    Utilities.warnPlayer(sender, Arrays.asList(main.getPhrase("not-a-command-message")));
                    help(sender);
                    break;
            }

        } catch(Exception e) {
            Utilities.warnPlayer(sender, Arrays.asList(main.getPhrase("formatting-error-message")));
        }

        return true;
    }

    private void info(CommandSender sender) {
        sender.sendMessage(WorldOfBlocks.prefix + ChatColor.GRAY + "Plugin Info");
        sender.sendMessage(ChatColor.DARK_PURPLE + "- " + ChatColor.GREEN + "Version " + main.getVersion() + " - By ThirtyVirus");
        sender.sendMessage("");
        sender.sendMessage(ChatColor.DARK_PURPLE + "- " + ChatColor.GREEN + "~Replace blocks randomly!");
        sender.sendMessage("");
        sender.sendMessage(ChatColor.DARK_PURPLE + "- " + ChatColor.RESET + ChatColor.RED + "" + ChatColor.BOLD + "You" + ChatColor.WHITE + ChatColor.BOLD + "Tube" + ChatColor.GREEN + " - https://youtube.com/thirtyvirus");
        sender.sendMessage(ChatColor.DARK_PURPLE + "- " + ChatColor.RESET + ChatColor.DARK_AQUA + "" + ChatColor.BOLD + "Twitter" + ChatColor.GREEN + " - https://twitter.com/thirtyvirus");
        sender.sendMessage(ChatColor.DARK_PURPLE + "- " + ChatColor.RESET + ChatColor.GOLD + "" + ChatColor.BOLD + "SpigotMC" + ChatColor.GREEN + " - https://spigotmc.org/members/179587/");
        sender.sendMessage(ChatColor.DARK_PURPLE + "------------------------------");
    }

    private void help(CommandSender sender) {
        sender.sendMessage(WorldOfBlocks.prefix + ChatColor.GRAY + "Commands");
        sender.sendMessage(ChatColor.DARK_PURPLE + "- " + ChatColor.GRAY + "/wob help");
        sender.sendMessage(ChatColor.DARK_PURPLE + "- " + ChatColor.GRAY + "/wob info");
        sender.sendMessage(ChatColor.DARK_PURPLE + "- " + ChatColor.GRAY + "/wob tutorial");
        sender.sendMessage(ChatColor.DARK_PURPLE + "- " + ChatColor.GRAY + "/wob reload");
        sender.sendMessage(ChatColor.DARK_PURPLE + "------------------------------");
    }

    private void reload(CommandSender sender) {
        main.reloadConfig();
        main.loadConfiguration();

        main.loadLangFile();

        Utilities.informPlayer(sender, Arrays.asList("configuration, values, and language settings reloaded"));
    }

}
