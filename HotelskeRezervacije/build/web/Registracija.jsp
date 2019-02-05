<%-- 
    Document   : Registracija
    Created on : 10-Dec-2018, 21:41:48
    Author     : Ilhan Kalac
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="sr">
    <head>
        
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
   

    <body>
        <% 
            if(request.getSession().getAttribute("ulogovan")!=null)
                response.sendRedirect("index.jsp");
        %>
        
        
        <%@ include file="navbar.jsp" %> 
        <%
            String rezultat =(String) request.getAttribute("rezultat");
            
            if(rezultat!=null)
            if(rezultat.equals("True")){%>
            <script type="text/javascript">
                    swal("Dobar  posao", "Registracija uspela", "success")
                    .then(function() {
                        window.location = "index.jsp";
                    });

            </script>
             
                     <%   }
            
            else if (rezultat.equals("False"))  {%>

                   <script type="text/javascript">
                        swal("Greška", "Uneli ste postojeće korisničko ime ili E-mail.", "error")
                       .then(function() {
                           window.location = "Registracija.jsp";
                       });
                   </script>

            <% }%>
            
        
        <h1>Hello World!</h1>
    <center>
         <form action="KlijentController">
            Korisničko ime: <input type="text" name="KIme">  <br><br>
            Lozinka: <input type="text" name="Lozinka">  <br><br>
            Ime: <input type="text" name="Ime">  <br><br>
            Prezime: <input type="text" name="Prezime">  <br><br>
            Email: <input type="text" name="Email">  <br><br>
            Telefon: <input type="text" name="Telefon">  <br><br>
            Adresa: <input type="text" name="Adresa">  <br><br>
            Država: <input type="text" name="Drzava">  <br><br>
            Grad: <input type="text" name="Grad">  <br><br>
            Postanski broj: <input type="text" name="PostanskiBroj">  <br><br>
            
            <input id ="button-a" type="submit" value="Registruj se">
        </form>
    </center>
    </body>
</html>

