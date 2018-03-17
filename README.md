# Assembler

I have developed two pass assembler of 8085 Microprocessor using symbol table.
I have summed up some of the core functions of it as below. When the application gets the assembly dump as an input it will process it in 2 phases. In the first pass, it will process all the labels and update symbol table with location counter(LC) and in the second pass, it will generate machine code which will directly process by CPU.

Two pass  assembler using symbol table

A)In the First Pass :- We will process all the labels and update symbol table with 
                       location counter(LC)
                       
                       
B)In the Second Pass:- We will generate machine code which will directly process by CPU                       
