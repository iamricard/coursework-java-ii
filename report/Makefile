TARGET = report.pdf
OUT_FORMAT = latex
IN_FORMAT = markdown
HEADER = source/header.tex

.PHONY: all clean

all: $(TARGET)

$(TARGET): source/*.yaml source/*.md
	pandoc \
	-H $(HEADER) \
	-f $(IN_FORMAT) \
	-t $(OUT_FORMAT) \
	-s -o $(TARGET) $^ \
	--top-level-division=chapter \
	--number-sections \
	--listings \
	--latex-engine=xelatex

clean:
	-@rm -f $(TARGET)
