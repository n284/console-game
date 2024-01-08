import sevens.Sevens;
import utilities.Keyboard;
import utilities.MessageLoader;
import utilities.exceptions.SystemException;
import utilities.interfaces.Game;

/**
 * ゲームを管理するメインクラス
 */
public class Menu {
    /**
     * メインメソッド
     * 
     * @param args
     * @return
     */
    public static void main(String[] args) {
        try {
            int continued;
            Game game;

            while (true) {
                System.out.print(MessageLoader.loadMessage("menu.select"));
                while (true) {
                    switch (Keyboard.inputInt(1, 1)) {
                        case 1: {
                            game = new Sevens();
                            break;
                        }
                        default: {
                            throw new SystemException(500, MessageLoader.loadMessage("error.unknown"));
                        }
                    }
                    break;
                }
                game.run();
                System.out.println(MessageLoader.loadMessage("menu.continue"));
                continued = Keyboard.inputInt(0, 1);
                if (continued == 1) {
                    System.out.println();
                    continue;
                } else if (continued == 0) {
                    System.out.println(MessageLoader.loadMessage("menu.finish"));
                    break;
                } else {
                    throw new SystemException(500, MessageLoader.loadMessage("error.unknown"));
                }
            }
        } catch (SystemException e) {
            System.out.println(e.getMessage());
        }
    }
}