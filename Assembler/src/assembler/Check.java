/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package assembler;

/**
 * @author prit
 */
public class Check {
//two d array...colms are register and rows are ADD,ADI,MOV,MVI


    private String combinationOfOpcode[][] = {{"1A", "1B", "1C", "1D"},  //mov
            {"2A", "2B", "2C", "2D"},  //mvi
            {"3A", "3B", "3C", "3D"},   //add
            {"4A", "4B", "4C", "4D"}  //adi
    };

    private String regCode[] = {"FA", "FB", "FC", "FD"};
    private String inst[] = {"MOV", "MVI", "ADD", "ADI"};

    private String register[] = {"A", "B", "C", "D"};
    private String opcode[] = {"DEC", "INC", "MVI", "ADI", "ADD", "MOV", "START", "END", "STA", "JMP", "LDA"};
    private int length[] = {1, 1, 2, 2, 1, 1, 99, 0, 1, 3, 1};
    private int memonic[] = {11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21};
   private int opcodeIndex = 0;

   private int a[] = new int[2];

    public String getCombinationOfOpcode(int index1, int index2) {
        return combinationOfOpcode[index1][index2];
    }


    public String getRegCode(int index) {
        return regCode[index];
    }

    public int getMemonic(int index) {
        return memonic[index];
    }


    public int getLength(int index) {
        return length[index];
    }

    public int getOpcodeIndex() {
        return opcodeIndex;
    }

    public int[] checkMove(String s1, String s2) {

        for (int i = 0; i < 4; i++)
            for (int j = 0; j < 4; j++) {
                if (s1.equals(inst[i]) && s2.equals(register[j])) {
                    a[0] = i;
                    a[1] = j;
                    return a;
                }//colm

            }
        return null;
    }


    public String checkWhetherOpcodeOrLabel(String s) {

        for (int i = 0; i < 11; i++) {
            if (s.equals(opcode[i])) {
                opcodeIndex = i;
                return "opcode";
            }

        }
        return "label";
    }


    int checkInterRegister(String s) {
        for (int i = 0; i < 4; i++)

        {
            if (s.equals(register[i])) {
                return i;
            }

        }
        return 0;
    }


    int checkInterSymbol(String s) {
        for (int i = 0; i < 3; i++) {
            if (s.equals("<S" + i + ">")) {
                return i;
            }
        }
        return 99;
    }

    public int checkInterOpcode(String s) {

        for (int i = 0; i < 11; i++)

        {
            if (s.equals(opcode[i])) {
                return i;
            }

        }
        return 99;
    }


    public String checkOprand(String s) throws NumberFormatException {

        if (s.matches("[0-9]+")) {
            return "number";
        } else {

            for (int i = 0; i < 4; i++) {
                if (s.equals(register[i])) {
                    return "register";
                }
            }
            return "label";
        }
    }


}
