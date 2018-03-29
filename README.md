# wrong-content-type-ajax-excecute-all-reproducer (reproducer for [Mojarra #4358](https://github.com/javaserverfaces/mojarra/issues/4358))

## Steps to reproduce:

1. Clone the [wrong-content-type-ajax-excecute-all-reproducer](https://github.com/stiemannkj1/wrong-content-type-ajax-excecute-all-reproducer) project:

    ```
    git clone https://github.com/stiemannkj1/wrong-content-type-ajax-excecute-all-reproducer.git
    ```

2. Build the project:

    ```
    cd wrong-content-type-ajax-excecute-all-reproducer && mvn clean package
	```

3. Deploy the project to Tomcat:

    ```
    cp target/*.war $TOMCAT_HOME/webapps/wrong-content-type-ajax-excecute-all-reproducer.war
    ```

4. Navigate to the deployed webapp at [http://localhost:8080/wrong-content-type-ajax-excecute-all-reproducer/](http://localhost:8080/wrong-content-type-ajax-excecute-all-reproducer/).
5. Note that the *External Context Calls:* show that `setResponseContentType("text/html")` was correctly called before the first call to `getResponseOutputWriter()`.
6. Click the *Execute (default) Ajax Request* button.
7. Note that the *External Context Calls:* show that `setResponseContentType("text/xml")` was correctly called before the first call to `getResponseOutputWriter()`.
6. Click the *Execute @all Ajax Request* button.

If the bug still exists, the *External Context Calls:* will show that `setResponseContentType("text/html")` was **incorrectly** called before the first call to getResponseOutputWriter()`.

If the bug is fixed, the *External Context Calls:* will show that `setResponseContentType("text/xml")` was called immediately before the first call to `getResponseOutputWriter()`.

## Additional Information:

The Servlet 3.1 Specification states (in section *5.5 Internationalization*):

> The `setCharacterEncoding`, `setContentType`, and `setLocale` methods can be called repeatedly to change the character encoding. Calls made after the servlet response’s `getWriter` method has been called or after the response is committed have no effect on the character encoding.

Therefore, before the first call to `externalContext.getResponseOutputWriter()`, `externalContext.setResponseContentType()` must be called with the correct content type.

Although this bug does not seem to break anything, that's likely because servlet implementations do not perfectly implement the requirements of the servlet spec. This does cause issues in Servlet and Portlet Containers (such as Liferay) that correctly implement their respective specs.
