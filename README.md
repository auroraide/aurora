# Aurora

[![pipeline status](https://git.scc.kit.edu/ap/Aurora/badges/master/pipeline.svg)](https://git.scc.kit.edu/ap/Aurora/commits/master)

The Lambda Calculus IDE

## Prerequisites

- [GWT](http://www.gwtproject.org)
    - You need to unpack your _GWT SDK_ folder in `../gwt-2.8.2` or adapt the `build.xml` file accordingly.
- [Ant](http://ant.apache.org)
- [Ivy](http://ant.apache.org/ivy)
- [Caddy](https://caddyserver.com) (optional)
- [Selenium](https://www.seleniumhq.org)
    - [How to use Selenium Webdriver](https://www.seleniumhq.org/docs/03_webdriver.jsp)
    - you need to execute ant build and start Caddy before testing with Selenium
    - We are using JBrowser as our webdriver. Have a look at the ExampleSeleniumTest.java, which is located in 
      Aurora/test/client.
      
 ## Testing
      
To run all tests execute
      
```
ant test  
```
To run only JUnit unit tests execute
      
```
ant junit-test
```
      
To run only Selenium tests execute
      
```
ant selenium-test
```
      
To run only GWTTestCase tests execute
      
```
ant gwt-test
```
      
## Build

### Dev mode

Start a local development server with

```
ant devmode
```

### Prod mode

Just do

```
ant build
```

then start a web server with `war/` as document root.

We're using [Caddy](https://caddyserver.com) for that.

```
caddy
```

Now, visit [`http://localhost:4000`](http://localhost:4000) in your browser.



## Documentation

You can generate the API documentation from the source code as follows.

Run

```bash
ant doc
```

and check out `docs/entwurf/html/index.html`.



## CodeMirror

This project uses CodeMirror to display code.
CodeMirror is licensed under the MIT License.
Visit [https://github.com/codemirror/CodeMirror] for its github page.
