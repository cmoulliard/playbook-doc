# HowTo generate playbook/role documentation

Table of Contents
=================

  * [Asciidoctor generator](#asciidoctor-generator)
     * [Using Java class](#using-java-class)
     * [Using the Client](#using-the-client)
  * [Pandoc tool](#pandoc-tool)
  * [To split the content generated](#to-split-the-content-generated)

## Asciidoctor generator

### Using Java class
```bash
mvn exec:java -Dexec.mainClass=dev.snowdrop.AsciidoctorGenerator -Dexec.args="asciidoctor/all.adoc"
```

### Using the Client
```bash
asciidoctor asciidoctor/all.adoc
open asciidoctor/all.html
```

## Pandoc tool

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

## To split the content generated

- To split an existing file into individual files, use `cspli` with the regular expression `'/^Command: */'`
```bash
csplit -f gen/command -k generated.md '/^Command: */' '{999}'
```