package utilities;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import utilities.exceptions.ApplicationException;
import utilities.exceptions.SystemException;

public class Input {
    private static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    public static int inputInt() throws SystemException, ApplicationException {
        try {
            return Integer.parseInt(br.readLine());
        } catch (IOException e) {
            throw new SystemException(500, MessageLoader.loadMessage("error.input"));
        } catch (NumberFormatException e) {
            throw new ApplicationException(300, MessageLoader.loadMessage("error.number"));
        }
    }

    public static int inputInt(int from, int to) throws SystemException, ApplicationException {
        int number = Input.inputInt();

        if (from <= number && number <= to) {
            return number;
        } else {
            throw new ApplicationException(300, MessageLoader.loadMessage("error.range", from, to));
        }
    }

    public static String inputString() throws SystemException, ApplicationException {
        try {
            return br.readLine();
        } catch (IOException e) {
            throw new SystemException(500, MessageLoader.loadMessage("error.input"));
        }
    }

}
