package com.epam.service;

import com.epam.entity.UserRole;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ShowErrorPage implements Command{
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try{
            request.getServletContext().getRequestDispatcher(ServletDestination.ERROPAGE.getPath()).forward(request, response);
        }catch (ServletException e) {
            e.printStackTrace();
        }
    }
}
