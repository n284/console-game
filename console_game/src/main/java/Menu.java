import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import utilities.Input;
import utilities.MessageLoader;
import utilities.exceptions.ApplicationException;
import utilities.exceptions.SystemException;
import utilities.interfaces.Game;

public class Menu {
    public static void main(String[] args) {
        int mode;
        int continued;
        Game game;

        try {
            while (true) {
                System.out.print(MessageLoader.loadMessage("menu.select"));
                while (true) {
                    try {
                        mode = Input.inputInt(1, 2);
                        switch (mode) {
                            case 1: {
                                game = new Sevens();
                                break;
                            }
                            default: {
                                throw new SystemException(500, MessageLoader.loadMessage("error.unknown"));
                            }
                        }

                    } catch (ApplicationException e) {
                        System.out.println(e.getMessage());
                    }
                }
                game.run();
                continued = Input.inputInt(0, 1);
                if (continued == 1) {

                } else {
                    System.out.println(MessageLoader.loadMessage("menu.finish"));
                    break;
                }
            }
        } catch (SystemException e) {
            System.out.println(e.getMessage());
        }
    }
}