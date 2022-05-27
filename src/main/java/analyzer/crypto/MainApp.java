package analyzer.crypto;

import java.util.Scanner;

public class MainApp {
    public static final String alphabet = "АБВГДЕЁЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯ абвгдеёжзийклмнопрстуфхцчшщъыьэюя . , ”” : - ! ?" + " ";

    public static void main(String[] args) {

        System.out.println("Выберите режим работы программы: \n Введите 1, если хотите зашифровать текст \n Введите 2, если хотите расшифровать текст");

        Scanner scanner = new Scanner(System.in);
        int inSc = scanner.nextInt();
        if (inSc == 1) {

        }

        int offset = 1; //TODO: Добавить считывание офсета

        String result = cypherText("Сколько б я не искал Золотого святого, всюду грубый оскал - всюду черное слово.", offset);

        System.out.println(result);

        result  = cypherText(result, offset * -1);

        System.out.println(result);

    }

    public static String cypherText(String sourceText, int offset)
    {
        String resText = "";

        for (char curChar : sourceText.toCharArray()
             ) {
            resText += cypherSymbol(curChar, offset);
        }

        return resText;
    }

    private static char cypherSymbol(char symbol, int offset)
    {
        int alphavitLength = alphabet.length();

        int index = alphabet.indexOf(symbol);

        int targetPosition = index + offset;

        if (targetPosition > alphavitLength)
        {
            targetPosition = targetPosition % alphavitLength;
        }

        return alphabet.charAt(targetPosition);
    }


}

