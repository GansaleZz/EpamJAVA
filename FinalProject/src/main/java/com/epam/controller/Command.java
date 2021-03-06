package com.epam.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Command pattern
 *
 */
public interface Command {

    String link = "controller?command=";

    void execute(HttpServletRequest request, HttpServletResponse response) throws IOException;

    static Command of(String commandName){
        return CommandInstance.commandOf(commandName);
    }
}
