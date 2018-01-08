# Aurora

The Lambda Calculus IDE

## Prerequisites

- [GWT](http://www.gwtproject.org)
    - You need to unpack your _GWT SDK_ folder in `../gwt-2.8.2` or adapt the `build.xml` file accordingly.
- [Ant](http://ant.apache.org)
- [Ivy](http://ant.apache.org/ivy)

## Build

Just do

```
ant build
```

or

```
ant devmode
```

if you want to start a local development server.

## Documentation

You can generate the API documentation from the source code as follows.

### HTML

Run

```bash
ant doc
```

and check out `docs/entwurf/html/index.html`.

### TeX

Run

```bash
ant texdoc
```

and build the generated `docs/entwurf/tex/javadoc.tex` by running

```
pdflatex javadoc.tex
```

(twice).
