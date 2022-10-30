class Controller...

public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        Author author = Author.findNamed(request.getParameter("name"));
        if (author == null)
        forward("/website.jsp", request, response);
        else {
        request.setAttribute("author", new Helper(author));
       forward("/website.jsp", request, response);

        AuthorDescription description = Author.findNamed(request.getParameter("description"));
        if (description == null)
        forward("/website.jsp", request, response);
        else {
        request.setAttribute("description", new Helper(description));
        forward("/website.jsp", request, response);

        BookList booklist = booklist.findNamed(request.getParameter("book list"));
        if (booklist == null)
        forward("/website.jsp", request, response);
        else {
        request.setAttribute("booklist", new Helper(booklist));
        forward("/website.jsp", request, response);
        }

        BookList booklist = booklist.findNamed(request.getParameter("book list"));
        if (booklist == null)
        forward("/website.jsp", request, response);
        else {
        request.setAttribute("booklist", new Helper(booklist));
        forward("/website.jsp", request, response);

        Image image = image.findNamed(request.getParameter("image"));
        if (image == null)
        forward("/website.jsp", request, response);
        else {
        request.setAttribute("image", new Helper(image));
        forward("/website.jsp", request, response);
        }

        }

     }