package Controler;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import Models.Aes;
import Models.Usuario;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author misaelpc
 */
public class Registrarse extends HttpServlet {

    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException,Exception {
       String masterPassword = "password";
       Aes a=new Aes();
       String user=request.getParameter("user");
       String nombre=a.encrypt(request.getParameter("nombre"),masterPassword, "2��\"trh�)6�|R", "q40mTVuvoo1XpEGU");
       String appat=a.encrypt(request.getParameter("app"),masterPassword, "2��\"trh�)6�|R", "q40mTVuvoo1XpEGU");
       String apmat=a.encrypt(request.getParameter("apm"),masterPassword, "2��\"trh�)6�|R", "q40mTVuvoo1XpEGU");
       String phone=a.encrypt(request.getParameter("Celular"),masterPassword, "2��\"trh�)6�|R", "q40mTVuvoo1XpEGU");
       String clave=a.encrypt(request.getParameter("pass"),masterPassword, "2��\"trh�)6�|R", "q40mTVuvoo1XpEGU");
        
        
        Usuario u=new Usuario();
        boolean b=u.RegistrarUsuario(nombre,appat,apmat,user,phone,clave,1);
       
        if(b){
            response.sendRedirect("logearse.jsp?men=Ya existe un Usuario con ese nombre de usuario"); 
        }
        else{
        Usuario u2=new Usuario();
        u2=u2.verificarUsuario(user, clave);
          HttpSession sesionOk = request.getSession();
          if (sesionOk.getAttribute("usuario") != null){
              sesionOk.invalidate();
          }
          else{
        if(u2!=null){
            //El usuario existe en la base de datos y clave correcta
            //Creamos la sesion
            HttpSession sesion=request.getSession(true);
            sesionOk.setAttribute("user", u2);
             Integer privi=u2.getUsuario_privilegio();
            sesionOk = request.getSession();
            sesionOk.setAttribute("usuario",user);
            sesionOk.setAttribute("privilegio",privi);
               
            if(u2.getUsuario_privilegio()==1){
                //El usuario tiene el privilegio de cliente
                response.sendRedirect("index.jsp");
            }else{
                //El usuario tiene el privilegio de empleado de la empresa
                response.sendRedirect("MostrarProductosEmpelado.jsp");
            }
        }else{
            //El usuario no existe o clave incorrecta
            response.sendRedirect("logearse.jsp?men=Ya existe un Usuario con ese nombre de usuario");
        }
      }
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
            processRequest(request, response);
        } catch (Exception ex) {
            Logger.getLogger(Registrarse.class.getName()).log(Level.SEVERE, null, ex);
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
            processRequest(request, response);
        } catch (Exception ex) {
            Logger.getLogger(Registrarse.class.getName()).log(Level.SEVERE, null, ex);
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
