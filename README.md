pandoc -s 01_preface.md 02_introduction.md 03_why_markdown_is_useful.md 04_limitations_of_markdown.md 05_conclusions.md 06_links.md -o output.md

pandoc -s 01_preface.md 02_introduction.md 03_why_markdown_is_useful.md 04_limitations_of_markdown.md 05_conclusions.md 06_links.md -o output.md --toc -V toc-title:"Table of Contents" --template=template.markdown

pandoc --toc -V toc-title:"Table of Contents" --template=template.markdown -o example-with-toc.md 01_preface.md