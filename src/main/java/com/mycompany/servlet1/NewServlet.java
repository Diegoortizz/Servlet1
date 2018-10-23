/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.servlet1;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import simplejdbc.*;

/**
 *
 * @author Utilisateur
 */
public class NewServlet extends HttpServlet {

    private DAO myDAO; // L'objet à tester
    private DataSource myDataSource; // La source de données à utiliser

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, DAOException {
        System.out.println("IN THE PROCESSREQUEST");
        myDataSource = DataSourceFactory.getDataSource();
        myDAO = new DAO(myDataSource);
        response.setContentType("text/html;charset=UTF-8");

        try (PrintWriter out = response.getWriter()) {
            String selectedvalue = request.getParameter("selection");
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet NewServlet</title>");
            out.println("<style>");
            out.println("table, tr, th, td {border: 1px solid black;}");
            out.println("select, form {display: inline-block;}");
            out.println("</style>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet NewServlet at " + request.getContextPath() + "</h1>");

            List<String> states = myDAO.EveryState();
            String param = selectedvalue;

            out.println("<form method = 'post'>");
            out.println("<br>");
            out.println("<select name=\"selection\">");
            for (int i = 0; i < states.size(); i++) {
                String state = states.get(i);
                System.out.println(state +" "+ param);
                if (state.equals(param)){
                        out.print("<option value='");
                out.print(state);
                out.print("'");
                out.print("selected>");
                out.print(state);
                out.print("</option>");
                out.println("");
                } else {
                out.print("<option value='");
                out.print(state);
                out.print("'");
                out.print(">");
                out.print(state);
                out.print("</option>");
                out.println("");
                }
            }
            out.println("</select>");
            out.println("<input type = \"submit\" value = \"Submit\" />");
            out.println("</form>");

            if (param != null) {
                out.println("<table style=\" border:1px solid black \" >");
                out.println("<tr>");
                out.println("<th> ID </th>");
                out.println("<th> Name </th>");
                out.println("<th> Adress </th>");
                out.println("</tr>");
                List<CustomerEntity> customersInState = myDAO.customersInState(param);
                for (CustomerEntity cs : customersInState) {
                    out.println("<tr>");
                    int id = cs.getCustomerId();
                    String name = cs.getName();
                    String adress = cs.getAddressLine1();
                    out.println("<td>");
                    out.println(id);
                    out.println("</td>");
                    out.println("<td>");
                    out.println(name);
                    out.println("</td>");
                    out.println("<td>");
                    out.println(adress);
                    out.println("</td>");
                }
                out.println("</table>");
            }

            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            System.out.println("IN THE DOGET");
            processRequest(request, response);
        } catch (DAOException ex) {
            Logger.getLogger(NewServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {

            System.out.println("IN THE DOPOST");
            String selectedvalue = request.getParameter("selection");
            System.out.println(selectedvalue);
            System.out.println("");
            processRequest(request, response);

        } catch (DAOException ex) {
            Logger.getLogger(NewServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
