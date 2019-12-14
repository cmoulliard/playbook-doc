## How To

- Execute the following command to generate from the markdown files the `generated.md` including the TOC created using the `template.mardown`

```bash
pandoc --toc \
      -V toc-title:"Table of Contents" \
      --template=template.markdown \
      -o generated.md \
      01_preface.md \
      02_introduction.md \
      03_why_markdown_is_useful.md \
      04_limitations_of_markdown.md \
      05_conclusions.md \
      06_links.md
```
- To split an existing file into individual files, use `cspli` with the regular expression `'/^Command: */'`
```bash
csplit -f gen/command -k generated.md '/^Command: */' '{999}'
```