<%-- 
    Document   : Sobe
    Created on : 19-Dec-2018, 19:49:54
    Author     : Ilhan Kalac
--%>

<%@page import="RepoPattern.SobeRepo"%>
<%@page import="Models.Soba"%>
<%@page import="Models.Soba"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <%
            String Hotel_Id = request.getParameter("Hotel_Id");

            if (Hotel_Id == null) {
                response.sendRedirect("Hoteli.jsp");
            }
        %>
        <jsp:include page="navbar.jsp" />  
        <%
            String ulogovanaRola = "";
            if ((String) request.getSession().getAttribute("UlogovanaRola") != null) {
                ulogovanaRola = (String) request.getSession().getAttribute("UlogovanaRola");
            }
            if (ulogovanaRola.equals("2")) {
        %>
        <a style="margin-top:20px; margin-left:40px" href="${pageContext.request.contextPath}/KreiranjeSoba.jsp?Hotel_Id=<%=Hotel_Id%>" class="btn btn-success">Dodaj novu sobu</a>
        <br>

        <% }
            String IDk = "";

            if (request.getSession().getAttribute("UlogovanaRola") != null) {
                IDk = (String) request.getSession().getAttribute("UlogovanaRola");
            }
            for (Soba soba : new SobeRepo().listaSobaSelektovanogHotela(Hotel_Id)) {
                {%>
        <div class="card" style="width: 18rem; float:left; margin:7.5px">              
            <img height="180px" width="286px" src="FotografijeSoba.jsp?SobaId=<%=soba.getSobaId()%>" />  
            <div class="card-body">
                <h5 class="card-title"> <%=soba.getBrojSobe()%></h5>
                <p class="card-text">Hotel: <%=soba.getHotel().getNaziv()%> </p>
                <p class="card-text">Tip sobe: <%=soba.getTipSobe().getNaziv()%> </p>
                <p class="card-text">Opis: <%=soba.getOpis()%> </p>
                <p class="card-text">Kratak opis: <%=soba.getKratkiOpis()%> </p>
                <p class="card-text">Cena: <%=soba.getCena()%> € </p>
                <p class="card-text">Cena sa poenima: <%=soba.getCenaUPoenima()%> </p>
                <p class="card-text">Poeni: <%=soba.getPoeni()%> </p>
                <p class="card-text">Kapacitet: <%=soba.getKapacitet()%> </p>
                <a href="${pageContext.request.contextPath}/Rezervisi.jsp?Soba_Id=<%=soba.getSobaId()%>" class="btn btn-primary">Rezerviši</a>
                <% if (IDk.equals("2")) {%> 
                <a href="${pageContext.request.contextPath}/EditSoba.jsp?Soba_Id=<%=soba.getSobaId()%>" class="btn btn-success">Izmeni</a>
                <%}
                %>
            </div>   
        </div>  
        <%}
            }
        %>

    </body>
</html>
