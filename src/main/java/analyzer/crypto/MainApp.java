package analyzer.crypto;

import java.io.*;
import java.util.Scanner;

public class MainApp {
    public static final String ALPHABET = "АБВГДЕЁЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯ абвгдеёжзийклмнопрстуфхцчшщъыьэюя.,”:-!?";

    public static void main(String[] args) {

        System.out.println(
                "Выберите режим работы программы: \n Введите 1, если хотите зашифровать текст. \n Введите 2, если хотите расшифровать текст.");

        Scanner scanner = new Scanner(System.in);
        int inSc = scanner.nextInt();
        //Шифруем текст из файла
        if (inSc == 1) {

            System.out.println("Добавьте путь к файлу, который нужно зашифровать:");
            Scanner scannerPathFrom = new Scanner(System.in);
            String pathFrom = scannerPathFrom.nextLine();

            System.out.println("Добавьте путь к файлу, в котором будет хранится шифр:");
            Scanner scannerPathOut = new Scanner(System.in);
            String pathOut = scannerPathOut.nextLine();

            System.out.println("Введите ключ для шифрования в виде целого числа:");
            Scanner scKey = new Scanner(System.in);
            int key = scKey.nextInt();

            //Делаем чтение из файла и сохраняем в стрингу contentFromFile
            String contentFromFile;
            File fileInput = new File(pathFrom);
            try(FileReader fileReader = new FileReader(fileInput)) {
                char[] buffer = new char[(int) fileInput.length()];
                fileReader.read(buffer);

                contentFromFile = new String(buffer).trim();
//                System.out.println(contentFromFile);
            }catch (IOException e) {
                throw new RuntimeException(e);
            }

            String cypherText = toCypherText(contentFromFile, key);

            try(FileWriter fileWriter = new FileWriter(pathOut)) {
                char[] buffer = cypherText.toCharArray();

                fileWriter.write(buffer);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        int offset = -1; //TODO: Добавить считывание офсета

        String result = toCypherText("Сколько б я не искал Золотого святого, всюду грубый оскал - всюду черное слово.", offset);

        System.out.println(result);

        result = toCypherText(result, offset * -1);

        System.out.println(result);

    }

    public static String toCypherText(String sourceText, int offset)
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
        int index = ALPHABET.indexOf(symbol);

        int targetPosition = index + offset;

        if (targetPosition < 0) //Расшифровка
        {
            targetPosition = ALPHABET.length() - getPureIndex(Math.abs(targetPosition));
        }
        else //Зашифровка
        {
            targetPosition = getPureIndex(Math.abs(targetPosition));
        }

        return ALPHABET.charAt(targetPosition);
    }

    //определяем индекс, если сдвиг символа происходит дальше длины алфавита
    private static int getPureIndex(int targetPosition)
    {
        int alphabetLength = ALPHABET.length();

        if (targetPosition > alphabetLength)
        {
            targetPosition = targetPosition % alphabetLength;
        }

        return targetPosition;
    }
}

