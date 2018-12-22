<%-- 
    Document   : Placanje
    Created on : 22-Dec-2018, 18:56:56
    Author     : Ilhan Kalac
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Plaćanje</title>
    </head>
    <body>
        <jsp:include page="navbar.jsp" />  
        <div style="margin-right:50%;" >
            <div style=" margin-left: 40%;" class="form-control"  >
            
                <h2> Plaćanje  </h2>
                <br>
                Vrsta kreditne kartice: 
                <select name="KreditnaKartica">
                    <option value="Visa"> Visa </option>
                    <option value="Maestro"> Maestro </option>    
                    <option value="MasterCard"> MasterCard </option>
                </select>


                <br> <br>
                Broj Kreditne kartice: <input style=" margin-right: 30%" class="form-control" type="number" name="BrojKartice" placeholder="XXXX-XXXX-XXXX-XXXX"> <br> <br>
                Datum  isteka: (Mesec) 
                <select name="DatumIstekaMesec">
                    <%
                        for(int i = 1; i<=12; i++) {
                            {%>
                            <option value="<%=i%>"> <%=i%> </option>
                            <%}
                        }
                    %>

                </select> <br> <br>
                 Datum  isteka: (Godina) 
                <select name="DatumIstekaGodina">
                    <%
                        for(int i = 2018; i<=2030; i++) {
                            {%>
                            <option value="<%=i%>"> <%=i%> </option>
                            <%}
                        }
                    %>

                </select>
                  <br> <br>  
                <input type="submit" value="Rezerviši" class="button btn-success"> <br> <br>
            </div>
        </div>
        
    </body>
</html>
