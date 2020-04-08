## How To generate playbook/role documentation

Table of Contents
=================

  * [How To generate a file concatenation markdown files](#how-to-generate-a-file-concatenation-markdown-files)
     * [Using pandoc](#using-pandoc)
     * [With the help of asciidoctor](#with-the-help-of-asciidoctor)
  * [To split the content generated](#to-split-the-content-generated)


### Using pandoc

- Execute the following command to generate from :
  - The different markdown files, which includes also a file with different roles / commands
  - The `generated.md` file
  - Including the TOC created using the `template.mardown`

```bash
cd markdown
pandoc --toc \
      -V toc-title:"Table of Contents" \
      --template=template.markdown \
      -o generated.md \
      01_preface.md \
      02_introduction.md \
      03_command1.md \
      03_command2.md \
      04_conclusion.md \
      06_links.md
cd ..
```

### With the help of asciidoctor
```bash
asciidoctor asciidoctor/generated.adoc
open asciidoctor/generated.html
```

## To split the content generated

- To split an existing file into individual files, use `cspli` with the regular expression `'/^Command: */'`
```bash
csplit -f gen/command -k generated.md '/^Command: */' '{999}'
```