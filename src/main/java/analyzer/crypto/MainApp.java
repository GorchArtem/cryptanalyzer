package analyzer.crypto;

public class MainApp {
    public static final String ALPHABET = "АБВГДЕЁЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯ абвгдеёжзийклмнопрстуфхцчшщъыьэюя.,”:-!?";

    public static void main(String[] args) {

//        System.out.println("Выберите режим работы программы: \n Введите 1, если хотите зашифровать текст \n Введите 2, если хотите расшифровать текст");
//
//        Scanner scanner = new Scanner(System.in);
//        int inSc = scanner.nextInt();
//        if (inSc == 1) {
//
//        }

        int offset = -1; //TODO: Добавить считывание офсета

        String result = toCypherText("Сколько б я не искал Золотого святого, всюду грубый оскал - всюду черное слово.", offset);

        System.out.println(result);

        result  = toCypherText(result, offset * -1);

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

        if (targetPosition > alphabetLength)//TODO: надо придумать, как обрабатывать ситуацию, если в таргетПозишн попадет отрицательное число
        {
            targetPosition = targetPosition % alphabetLength;
        }

        return targetPosition;
    }
}

