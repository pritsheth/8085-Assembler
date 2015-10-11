/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package assembler;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.StringTokenizer;

/**
 * @author prit
 */
public class Main {


    public static void main(String[] args) throws IOException, NumberFormatException {

        int i;
        int lineCounter = 100, isym = 0;

        Check check = new Check();//for cheking the opcode and oprand
        SymbolTable symbolTable = new SymbolTable();

        String chkop = null;

        int number = 0;

        File input = new File("input.txt");
        Scanner inputScanner = new Scanner(input);

        File intermediate = new File("intermediate.txt");
        FileWriter fw = new FileWriter(intermediate);
        Scanner intermediateScanner = new Scanner(intermediate);


        File symTable = new File("symboltable.txt");
        FileWriter sfw = new FileWriter(symTable);

        File output = new File("output.txt");
        FileWriter outputWriter = new FileWriter(output);


        while (inputScanner.hasNextLine()) {
            fw.write(lineCounter + " ");
            String line = inputScanner.nextLine();
            i = 0;//starting from the 0th word

            StringTokenizer st = new StringTokenizer(line, " ,:");
            while (st.hasMoreTokens()) {

                String key = st.nextToken();
                System.out.println(" for " + i + "word " + key);


                if (i == 0 || i == 1) //only for the first  word check dat is label or opcode
                {
                    chkop = check.checkWhetherOpcodeOrLabel(key);
                    if (chkop.equals("opcode")) {
                        i++;//if first one is opcode den go for cheking the oprand
                        fw.write(key);

                    } else {
                        isym = processSymbolTable(lineCounter, isym, symbolTable, key);
                    }


                } else//check for oprands

                {
                    chkop = check.checkOprand(key);
                    if (chkop.equals("number")) {
                        fw.write(key);
                        number = Integer.parseInt(key);
                    } else {
                        if (chkop.equals("register")) {
                            fw.write(key);
                        }//if first one is opcode den go for cheking the oprand
                        else {
                            isym = processSymbolTableForOprands(isym, symbolTable, fw, key);
                        }
                    }
                }

                i++;

                fw.write(" ");
            }//end of one line

            fw.append("\r\n");


            if (check.getLength(check.getOpcodeIndex()) != 99)
                lineCounter = lineCounter + check.getLength(check.getOpcodeIndex());
            else
                lineCounter = number;

        }//end of page

        fw.close();
        sfw.write("   LC   " + " symbol \r\n");
        sfw.write("\r\n");
        String key2;
        for (i = 0; i < isym; i++) {

            sfw.write(" | " + symbolTable.getLineCount(i) + " | " + symbolTable.getLabel(i) + "|");
            System.out.println("LC value is" + symbolTable.getLineCount(i) + "symble stored is" + symbolTable.getLabel(i));

            sfw.write("\r\n");
        }
        sfw.close();
//----------------------------- 2nd Pass:::------------------------------------

        int x,y;
        while (intermediateScanner.hasNextLine()) { //fw.write(lineCounter+" ");
            String line1 = intermediateScanner.nextLine();
            int i1 = 0;//starting from the 0th word
            int a1[] = new int[1];
            StringTokenizer st1 = new StringTokenizer(line1, " ");
            outputWriter.write(st1.nextToken() + " ");
            while (st1.hasMoreTokens()) {


                String key1 = st1.nextToken();
                if (i1 == 0) {

                    if (key1.equals("MOV") || key1.equals("MVI")) {
                        key2 = st1.nextToken();
                        a1 = check.checkMove(key1, key2);
                        x = a1[0];
                        y = a1[1];
                        outputWriter.write(check.getCombinationOfOpcode(x, y) + " ");
                        i1++;
                    } else {
                        x = check.checkInterOpcode(key1);//for opcode checking

                        i1++;
                        if (x != 99)
                            outputWriter.write(check.getMemonic(x) + " ");
                        else
                            outputWriter.write(" " + key1);
                    }

                } else

                {

                    if (key1.matches("[0-9]+"))
                        outputWriter.write(key1);

                    else {

                        x = check.checkInterSymbol(key1);//for symbolTable-no checking
                        if (x != 99)
                            outputWriter.write(" " + symbolTable.getLineCount(x));
                        else //its register or start value

                        {
                            i = check.checkInterRegister(key1);
                            outputWriter.write(" " + check.getRegCode(i));
                            // outputWriter.write(key1);
                        }
                    }

                    System.out.println("label is " + key1);
                   }
            }

            outputWriter.write("\r\n");
        }
        outputWriter.close();


    }

    private static int processSymbolTableForOprands(int isym, SymbolTable symbolTable, FileWriter fw, String key) throws IOException {
        if (symbolTable.isLabelExist(key))//not inputScanner symbolTable tab
        {
            //symbolTable.LC[isym] = lineCounter;
            symbolTable.setLabel(isym,key);
            fw.write("<S" + isym + ">");
            isym++;
        } else {
            fw.write("<S" + symbolTable.in_sym + ">");
        }
        return isym;
    }

    private static int processSymbolTable(int lineCounter, int isym, SymbolTable symbolTable, String key) {
        if (symbolTable.isLabelExist(key))//symbol is not der inputScanner table
        {
            symbolTable.setLineCount(isym, lineCounter);
            symbolTable.setLabel(isym, key);
            //fw.write("<S"+isym+">");
            isym++;
        } else//label is der
        {
            symbolTable.setLineCount(symbolTable.in_sym,lineCounter);
            //fw.write("<S"+symbolTable.in_sym+">");
        }
        return isym;
    }

}
