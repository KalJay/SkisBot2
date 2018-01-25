package com.kaljay.skisBot2.modules.help;

import com.kaljay.skisBot2.modules.Module;
import com.kaljay.skisBot2.modules.ModuleManager;
import com.kaljay.skisBot2.skisBot2;

import java.util.ArrayList;

public class HelpEntryBuilder {

    private Module module;
    private ArrayList<String> helpEntries = new ArrayList<>();

    private String prefix;
    private String desc;

    public HelpEntryBuilder(Module module) {
        this.module = module;
        this.desc = module.getDescription();
        try {
            prefix = ModuleManager.getCommandPrefix(module);
        } catch (NullPointerException e) {
            skisBot2.logWarn("Module '" + module.getName() + "' attempted to create a help entry without having registered a prefix");
            prefix = "ERROR";
        }
    }

    public HelpEntryBuilder(Module module, String command, String extraSyntax, String helpEntry) {
        this.module = module;
        addHelpEntry(command, extraSyntax, helpEntry);
    }

    public void addHelpEntry(String command, String extraSyntax, String helpEntry) {
        if(extraSyntax.equals("")) {
            helpEntries.add(prefix + command + " - " + helpEntry);
        } else {
            helpEntries.add(prefix + command + " " + extraSyntax + " - " + helpEntry);
        }
    }

    public void addModuleDescription(String desc) {
        this.desc = desc;
    }

    public void removeHelpEntry(String command) {
        ArrayList<String> tempList = new ArrayList<>();
        for(String helpEntry : helpEntries) {
            if (helpEntry.contains(prefix + " " + command + " ")) {
                tempList.add(helpEntry);
            }
        }
        helpEntries.removeAll(tempList);
    }

    public ArrayList<String> getHelpEntries() {
        return helpEntries;
    }

    public String getPrefix() {
        return prefix;
    }

    public String getDesc() {
        return desc;
    }

    public Module getModule() {
        return module;
    }
}
