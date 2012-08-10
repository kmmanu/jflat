package com.tecacet.jflat;

public class FixedWidthLineMerger implements LineMerger {

    private int[] widths;
    private String format;
    private final String EMPTY_STRING = "";

    public FixedWidthLineMerger(int[] widths) {
        super();
        this.widths = widths;
        this.format = buildFormat();
    }

    @Override
    public String makeLine(String[] elements) throws LineMergerException{
        convertNullsToEmptyStrings(elements);
        validateFields(elements);
        return String.format(format, (Object[]) elements);
    }

    private String buildFormat() {
        StringBuffer sb = new StringBuffer();
        for (int width : widths) {
            String format = "%-" + width + "s"; //This means a left-justified string of <width> characters
            sb.append(format);
        }
        return sb.toString();
    }

    private void validateFields(String[] elements) throws LineMergerException {
        if (elements.length > widths.length) {
            throw new TooManyFieldsException(elements.length, widths.length);
        }

        for (int i = 0; i < elements.length; i++) {
            if (elements[i] == null) {
                continue; 
            }
            if (elements[i].length() > widths[i]) {
                throw new FieldTooWideException(elements[i], widths[i]);
            }
        }
    }
    
    private void convertNullsToEmptyStrings(String[] elements) {
        for (int i = 0; i < elements.length; i++) {
            if (elements[i] == null) {
                elements[i] = EMPTY_STRING;
            }
        }
    }
       
}
