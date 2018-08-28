<%@tag import="it.unitn.webprog2018.ueb.shoppinglist.entities.utils.AbstractEntity"%>
<%@tag import="java.util.Set"%>
<%@tag import="java.util.Map"%>

<%@tag description="field errors tag" pageEncoding="UTF-8"%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%-- The list of normal or fragment attributes can be specified here: --%>
<%@attribute name="entity" required="true" type="AbstractEntity" %>
<%@attribute name="field" required="true" type="String" %>

<%-- any content can be specified here e.g.: --%>
<c:forEach items="${entity.getFieldErrors(field)}" var="v">
	${v}
</c:forEach>