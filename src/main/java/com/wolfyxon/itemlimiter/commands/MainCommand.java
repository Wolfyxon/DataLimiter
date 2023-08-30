package com.wolfyxon.itemlimiter.commands;

import com.wolfyxon.itemlimiter.ConfigMgr;
import com.wolfyxon.itemlimiter.ItemLimiter;
import com.wolfyxon.itemlimiter.ItemLimiterCommand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainCommand extends ItemLimiterCommand {


    class Action {
        public String[] aliases;
        public String description;
        public String permission;

        public Action(String[] aliases, String description, String permission){
            this.aliases = aliases;
            this.description = description;
            this.permission = permission;
        }
        public Action(String alias, String description, String permission){
            this(new String[]{alias},description,permission);
        }
        public Action(String alias, String description){
            this(new String[]{alias},description,null);
        }
        public Action(String aliases[], String description){
            this(aliases,description,null);
        }

        public String getPrimaryAlias(){
            return aliases[0];
        }

        public String aliasesAsString(){
            if(aliases.length==1) return getPrimaryAlias();
            String str = "[";
            for(int i=0;i<aliases.length;i++){
                if(i!=0) str += ",";
                str += aliases[i];
            }
            str += "]";
            return str;
        }

        public boolean matches(String alias){ return Arrays.asList(aliases).contains(alias); }

    }

    Action[] actions = {
            new Action("reload","Reloads the plugin's config.","reload"),
    };

    public Action getAction(String alias){
        for(Action a : actions){
            if(a.matches(alias)) return a;
        }
        return null;
    }

    public MainCommand(ItemLimiter plugin) {
        super(plugin);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(args.length == 0){
            sendMsg("&6&lItemLimiter",sender);
            sendMsg("&cby &4Wolfyxon",sender);
            sendMsg("&b&nhttps://github.com/Wolfyxon/ItemLimiter",sender);
            return true;
        }

        String strAction = args[0];
        Action action = getAction(strAction);
        if(action == null) {
            sendMsg("&cUnrecognized option '"+strAction+"'",sender);
            return true;
        }
        if(action.permission!=null && !ConfigMgr.hasPermission(action.permission,sender)){
            sendMsg(getConfig().getMessage("noPermission"),sender);
            return true;
        }
        switch (action.getPrimaryAlias()){
            case "reload":{
                getConfig().reload();
                sendMsg("&aConfig reloaded",sender);
            }
        }


        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> res = new ArrayList<>();

        if(args.length == 1){
            for(Action a : actions){
                for(String als : a.aliases){
                    res.add(als);
                }
            }
            return res;
        }

        return res;
    }

}
