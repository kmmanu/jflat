package com.tecacet.jflat;

public class FixedWidthFileMerger implements LineMerger {

    private int[] widths;
    private String format;

    public FixedWidthFileMerger(int[] widths) {
        super();
        this.widths = widths;
        this.format = buildFormat();
    }

    private String buildFormat() {
        StringBuffer sb = new StringBuffer();
        for (int width : widths) {
            sb.append("%" + width + "s");
        }
        sb.append("\n"); //TODO get from properties
        return sb.toString();
    }

    @Override
    public String makeLine(String[] elements) {
        return String.format(format, (Object[])elements);
    }
}
