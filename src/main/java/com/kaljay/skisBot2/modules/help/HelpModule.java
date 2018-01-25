package com.kaljay.skisBot2.modules.help;

import com.kaljay.skisBot2.comms.Text;
import com.kaljay.skisBot2.modules.Module;
import com.kaljay.skisBot2.modules.ModuleManager;
import com.kaljay.skisBot2.skisBot2;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class HelpModule implements Module {

    private Map<String, ArrayList<String>> helpSections = new HashMap<>();

    public HelpModule() {
        ModuleManager.addCommandPrefix("!help ", this);
        HelpEntryBuilder builder = new HelpEntryBuilder(this);
        builder.addHelpEntry("<module prefix>", "", "Shows related help provided by the module, eg. '!help help' would show this, as 'help' is this module's prefix");
        this.buildEntry(builder);
    }

    @Override
    public void command(String[] args, MessageReceivedEvent event) {
        Map.Entry<String, ArrayList<String>> resultEntry = getHelpEntryByPrefix(args[0]);

        if(resultEntry != null) {

            Text.sendToPM(event.getAuthor().getOrCreatePMChannel(), createSendableHelp(resultEntry, true));
        } else {
            if (args[0].isEmpty()) {
                Text.sendToPM(event.getAuthor().getOrCreatePMChannel(), "Unrecognised Command");
                Text.sendToPM(event.getAuthor().getOrCreatePMChannel(), createSendableHelp(getHelpEntryByName("HelpModule"), false));
                //Text.sendToPM(event.getAuthor().getOrCreatePMChannel(), unrecognisedCommand(this.getName()));
            } else {
                StringBuilder text = new StringBuilder();
                text.append("```");
                text.append("Unknown help entry specified, please use one of the below: \n\n");
                for (Map.Entry<String, ArrayList<String>> entry : helpSections.entrySet()) {
                    text.append("!help ").append(ModuleManager.getCommandPrefix(ModuleManager.getModuleByName(entry.getKey())).substring(1)).append(" - help for ").append(ModuleManager.getModuleByName(entry.getKey()).getName()).append("\n");
                }
                text.append("```");
                Text.sendToPM(event.getAuthor().getOrCreatePMChannel(), text.toString());
            }
        }
    }

    @Override
    public String getName() {
        return "HelpModule";
    }

    @Override
    public String getDescription() {
        return "HelpModule is a module that manages help entries for all other modules. Help entries for this module show all registered module entries";
    }

    public void buildEntry(HelpEntryBuilder builder) {
        helpSections.put(builder.getModule().getName(), builder.getHelpEntries());
        skisBot2.logInfo(builder.getModule().getName() + " has registered help pages");
    }

    private Map.Entry<String, ArrayList<String>> getHelpEntryByName(String name) {
        for (Map.Entry<String, ArrayList<String>> entry : helpSections.entrySet()) {
            skisBot2.logDebug(name);
            skisBot2.logDebug(ModuleManager.getModuleByName(entry.getKey()).getName());
            if(name.equals(ModuleManager.getModuleByName(entry.getKey()).getName())) {

                return entry;
            }
        }
        return null;
    }

    private String createSendableHelp(Map.Entry<String, ArrayList<String>> entry, boolean description) {
        StringBuilder text = new StringBuilder();
        text.append("```");
        if (entry == null) {
        }
        if(description) {
            text.append(ModuleManager.getModuleByName(entry.getKey()).getDescription()).append("\n\n");
        }
        for (String entry1 : entry.getValue()) {
            text.append(entry1).append("\n");
        }
        text.append("```");
        return text.toString();
    }

    private Map.Entry<String, ArrayList<String>> getHelpEntryByPrefix(String prefix) {
        for (Map.Entry<String, ArrayList<String>> entry : helpSections.entrySet()) {
            skisBot2.logDebug(prefix);
            skisBot2.logDebug(ModuleManager.getModuleByName(entry.getKey()).getName());
            if(prefix.equals(ModuleManager.getCommandPrefix(ModuleManager.getModuleByName(entry.getKey())).substring(1).trim())) {

                return entry;
            }
        }
        return null;
    }
}
