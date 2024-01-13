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
            boolean continued;
            Game game;

            while (true) {
                System.out.print(MessageLoader.loadMessage("menu.select"));
                switch (Keyboard.inputInt(1, 1)) {
                    case 1: {
                        game = new Sevens();
                        break;
                    }
                    default: {
                        throw new SystemException(MessageLoader.loadMessage("error.unknown"));
                    }
                }

                game.run();
                System.out.println(MessageLoader.loadMessage("menu.continue"));
                continued = Keyboard.inputInt(0, 1) == 1 ? true : false;

                if (continued) {
                    continue;
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