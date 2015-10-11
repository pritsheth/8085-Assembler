/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package assembler;

/**
 * @author prit
 */
public class SymbolTable {


    private int lineCount[] = new int[20];
    private String label[] = new String[20];
    int in_sym = 0;

    SymbolTable() {
        for (int i = 0; i < 20; i++) {
            lineCount[i] = 0;
            label[i] = "";
        }
    }

    Boolean isLabelExist(String key) {

        for (int i = 0; i < 8; i++) {
            if (key.equals(label[i])) {
                in_sym = i;
                return false;
            }
        }
        return true;
    }

    public int getLineCount(int index) {
        return lineCount[index];
    }

    public void setLineCount(int index, int lineCount) {
        this.lineCount[index] = lineCount;
    }

    public String getLabel(int index) {
        return label[index];
    }

    public void setLabel(int index, String label) {
        this.label[index] = label;
    }
}
