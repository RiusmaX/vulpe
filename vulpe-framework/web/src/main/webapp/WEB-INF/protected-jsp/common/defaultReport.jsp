<%
// tenta pegar o atributo do request
byte[] bytes = (byte[]) request.getAttribute( "report_content" );
// se n&atilde;o achar, tenta pegar o atributo da sess&atilde;o
if ( bytes == null )  
bytes = (byte[]) request.getSession().getAttribute( "report_content" );

if ( bytes != null ) {
// tenta pegar o atributo do request
String filename = (String) request.getAttribute( "nome_arquivo" );
// se n&atilde;o achar, tenta pegar o atributo da sess&atilde;o
if ( filename == null ) 
filename = (String) request.getSession().getAttribute( "nome_arquivo" );

response.setContentType("application/octet-stream");
response.addHeader( "Content-Disposition",
"attachment; filename=" + filename );
ServletOutputStream ouputStream = response.getOutputStream();
ouputStream.write(bytes, 0, bytes.length);
ouputStream.flush();
ouputStream.close();
}
// remove os atributos da sess&atilde;o
if (request.getSession().getAttribute( "report_content" ) != null)  
request.getSession().removeAttribute( "report_content" );
if (request.getSession().getAttribute( "nome_arquivo" ) != null)  
request.getSession().removeAttribute( "nome_arquivo" );
%>
