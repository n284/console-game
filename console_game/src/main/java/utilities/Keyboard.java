package utilities;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import utilities.exceptions.SystemException;

/**
 * キーボードを表すクラス
 */
public class Keyboard {
    /** 標準入力リーダー */
    private static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    /**
     * 整数を入力させる
     * 
     * @param
     * @return int
     * @throws SystemException 標準入力にエラーがある場合にスローする
     */
    public static int inputInt() throws SystemException {
        try {
            while (true) {
                try {
                    return Integer.parseInt(br.readLine());
                } catch (NumberFormatException e) {
                    System.out.println(MessageLoader.loadMessage("error.number"));
                }
            }
        } catch (IOException e) {
            throw new SystemException(500, MessageLoader.loadMessage("error.input"));
        }
    }

    /**
     * 範囲内の整数を入力させる
     * 
     * @param from
     * @param to
     * @return
     * @throws SystemException
     */
    public static int inputInt(int from, int to) throws SystemException {
        if (from > to) {
            throw new SystemException(300, MessageLoader.loadMessage("error.fromto"));
        }
        while (true) {
            int number = Keyboard.inputInt();

            if (from <= number && number <= to) {
                return number;
            } else {
                System.out.println(MessageLoader.loadMessage("error.range", from, to));
            }
        }
    }

    /**
     * 文字列を入力させる
     * 
     * @return
     * @throws SystemException
     */
    public static String inputString() throws SystemException {
        try {
            return br.readLine();
        } catch (IOException e) {
            throw new SystemException(500, MessageLoader.loadMessage("error.input"));
        }
    }
}