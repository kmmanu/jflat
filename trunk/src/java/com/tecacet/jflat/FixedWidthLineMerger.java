package com.tecacet.jflat;

public class FixedWidthLineMerger implements LineMerger {

    private int[] widths;
    private String format;

    public FixedWidthLineMerger(int[] widths) {
        super();
        this.widths = widths;
        this.format = buildFormat();
    }

    private String buildFormat() {
        StringBuffer sb = new StringBuffer();
        for (int width : widths) {
            String format = "%-" + width + "s"; //This means a left-justified string of <width> characters
            sb.append(format);
        }
        return sb.toString();
    }

    @Override
    public String makeLine(String[] elements) {
        return String.format(format, (Object[])elements);
    }
}
