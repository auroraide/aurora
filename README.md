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
    - You need to download chromedriver, if you intend to use Chrome as your default Webbrowser
    - Download link: https://sites.google.com/a/chromium.org/chromedriver/
    - You need to put the downloaded chromedriver file in Aurora's parent directory
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
