package com.kaljay.skisBot2;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ResourceHandler {

    public ResourceHandler() {
        skisBot2.logInfo("ResourceHandler Online");
    }

    public InputStream getFile(String fileName) {
        skisBot2.logDebug("retrieving file '" + fileName + "' from '" + getClass().getResourceAsStream("/" + fileName) + "'");
        InputStream in = getClass().getResourceAsStream("/" + fileName);
        return in;
    }
}
