package analyzer.crypto;

import java.io.*;
import java.util.Scanner;

public class MainApp {
    private static final String ALPHABET = "АБВГДЕЁЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯ абвгдеёжзийклмнопрстуфхцчшщъыьэюя.,”:-!?";

    public static void main(String[] args) {

        System.out.println(
                "Выберите режим работы программы: \n Введите 1, если хотите зашифровать текст."+
                        "\n Введите 2, если хотите расшифровать текст. \n Введите 3, если хотите расшифровать текст методом bruteForce.");

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
            String contentFromFile = readFile(pathFrom);

            //шифруем текст из стринги contentFromFile
            String cypherText = toCypherText(contentFromFile, key);

            //записываем зашифрованный текст в другой файл
            writeToFile(cypherText, pathOut);

            System.out.println(decipherBruteForce(cypherText));
        }
        else if (inSc == 2) {
            System.out.println("Добавьте путь к файлу, в котором хранится шифр:");
            Scanner scannerPathFrom = new Scanner(System.in);
            String pathFrom = scannerPathFrom.nextLine();

            System.out.println("Добавьте путь к файлу, в который нужно сохранить расшифрованный текст:");
            Scanner scannerPathOut = new Scanner(System.in);
            String pathOut = scannerPathOut.nextLine();

            System.out.println("Введите ключ в виде целого числа для дешифрования:");
            Scanner scKey = new Scanner(System.in);
            int key = scKey.nextInt();

            //Делаем чтение из файла и сохраняем в стрингу contentFromFile
            String contentFromFile = readFile(pathFrom);

            String cypherText = toCypherText(contentFromFile, key * (-1));//Делаем дешифровку текста, умножая ключ на отрицательную единицу для обратного сдвига символов

            writeToFile(cypherText, pathOut);
        }
        else if (inSc == 3) {
            System.out.println("Добавьте путь к файлу, в котором хранится шифр:");
            Scanner scannerPathFrom = new Scanner(System.in);
            String pathFrom = scannerPathFrom.nextLine();

            System.out.println("Добавьте путь к файлу, в который нужно сохранить расшифрованный текст:");
            Scanner scannerPathOut = new Scanner(System.in);
            String pathOut = scannerPathOut.nextLine();

            //Делаем чтение из файла и сохраняем в стрингу contentFromFile
            String contentFromFile = readFile(pathFrom);

            //Дешифруем текст
            String decipherText = decipherBruteForce(contentFromFile);

            //Сохраняем дешифрованный текст в файл
            writeToFile(decipherText, pathOut);
        }

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


    private  static String readFile(String path) { //Делает чтение из файла и возвращает в стринге
        File fileInput = new File(path);
        try(FileReader fileReader = new FileReader(fileInput)) {
            char[] buffer = new char[(int) fileInput.length()];
            fileReader.read(buffer);

            return new String(buffer).trim();

        }catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void writeToFile(String text, String path) { //записывает текст в файл
        try(FileWriter fileWriter = new FileWriter(path)) {
            char[] buffer = text.toCharArray();

            fileWriter.write(buffer);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static String decipherBruteForce(String cypherText) {
        int key = 0;
        String returnText;
        while (true) {
            String resultText = toCypherText(cypherText, key * (-1));

            if (resultText.indexOf("Ъ") == 0 || resultText.indexOf("Ь") == 0 || resultText.indexOf("Ы") == 0    //Проверяем, что текст не начинается на перечисленные символы
            || resultText.indexOf("ъ") == 0 || resultText.indexOf("ь") == 0 || resultText.indexOf("ы") == 0 ) {
                key++;
                continue;
            }
            if (searchFictionCharsCombinationsInText(resultText) == true) { //проверяем, что в тексте нет фиктивных комбинаций символов
                key++;
            } else {
                returnText = resultText;
                break;
            }
        }
        return returnText;
    }

    private static boolean searchFictionCharsCombinationsInText(String text) {
        boolean check = false;
        for (int i = 0; i < fictionCombination.length; i++) {
            if (text.contains(fictionCombination[i])) {
                check = true;
            }
        }
        return check;
    }


    private static String[] fictionCombination = {"аъ", "аы", "аь",
            "бй",
            "вй", "вэ",
            "гй", "гф", "гх", "гъ", "гь", "гэ",
            "дй",
            "еъ", "еы", "еь", "еэ",
            "ёъ", "еы", "еь", "ёэ", "ёа", "ёе", "ёё", "ёи", "ёу", "ёф", "ёя",
            "жй", "жф", "жх", "жш", "жщ",
            "зй", "зп", "зщ",
            "иъ", "иы", "иь",
            "йё", "йж", "йй", "йъ", "йы", "йь", "йэ",
            "кй", "кщ", "къ", "кь",
            "лй", "лъ", "лэ",
            "мй", "мъ",
            "нй",
            "оъ", "оы", "оь",
            "пв", "пг", "пж", "пз", "пй", "пъ",
            "ръ",
            "сй",
            "тй",
            "уъ", "уы", "уь",
            "фб", "фж", "фз", "фй", "фп", "фх", "фц", "фъ", "фэ",
            "хё", "хж", "хй", "хш", "хы", "хь", "хю", "хя",
            "цб", "цё", "цж", "цй", "цф", "цх", "цч", "цщ", "цъ", "ць", "цэ", "цю", "ця",
            "чб", "чг", "чз", "чй", "чп", "чф", "чщ", "чъ", "чы", "чэ", "чю",
            "шд", "шж", "шз", "шй", "шш", "шщ", "шъ", "шы", "шэ",
            "щб", "щг", "щд", "щж", "щз", "щй", "щл", "щп", "щп", "щф", "щх", "щц", "щч", "щш", "щщ", "щъ", "щы", "щь", "щэ",
            "ъа", "ъб", "ъв", "ъг", "ъд", "ъж", "ъз", "ъи", "ъй", "ък", "ъл", "ъм", "ън", "ъп", "ър", "ъс", "ът", "ъу", "ъф", "ъх", "ъц", "ъч", "ъш", "ъщ", "ъъ", "ъы", "ъи", "ъэ", "ъ", "ъ", "ъ",
            "ыа", "ыё", "ыо", "ыф", "ыъ", "ыы", "ыь", "ыэ",
            "ьа", "ьй", "ьл", "ьу", "ьь", "ьы", "ьъ",
            "эа", "эе", "эё", "эц", "эч", "эъ", "эы", "эь", "ээ", "эю",
            "юу", "юь", "юы", "юъ",
            "яа", "яё", "яо", "яъ", "яы", "яь", "яэ",
    };


}

