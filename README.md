## How To generate a file concatenation markdown files

- Execute the following command to generate from :
  - The different markdown files, which includes also a file with different roles / commands
  - The `generated.md` file
  - Including the TOC created using the `template.mardown`

```bash
pandoc --toc \
      -V toc-title:"Table of Contents" \
      --template=template.markdown \
      -o generated.md \
      01_preface.md \
      02_introduction.md \
      03_commands.md \
      04_conclusion.md \
      06_links.md
```

## To split the content generated

- To split an existing file into individual files, use `cspli` with the regular expression `'/^Command: */'`
```bash
csplit -f gen/command -k generated.md '/^Command: */' '{999}'
```