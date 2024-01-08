package sevens.player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import sevens.player.objects.CPUPlayer;
import sevens.player.objects.HumanPlayer;
import sevens.player.utilities.interfaces.Player;
import utilities.Keyboard;
import utilities.MessageLoader;
import utilities.SettingLoader;
import utilities.exceptions.SystemException;

/**
 * プレイヤーを管理するクラス
 */
public class PlayerFactory {

    /**
     * 七並べを行うプレイヤーを生成する
     * 
     * @param
     * @return
     * @throws SystemException 不明なエラーが発生した場合にスローする
     */
    public static List<Player> createPlayer() throws SystemException {
        List<Player> playerList = new ArrayList<>();
        PlayerFactory.createHumanPlayer(playerList);

        if (playerList.size() < 4) {
            createCPUPlayer(playerList);
        }
        System.out.println(MessageLoader.loadMessage("sevens.order.player"));
        Collections.shuffle(playerList);

        return playerList;
    }

    /**
     * CPUを生成する
     * 
     * @param playerList
     * @return
     * @throws SystemException 不明なエラーが発生した場合にスローする
     */
    private static void createCPUPlayer(List<Player> playerList) throws SystemException {
        System.out.println(MessageLoader.loadMessage("sevens.input.cpu"));
        int number = Keyboard.inputInt(1,
                Integer.parseInt(SettingLoader.loadSetting("sevens.player.max")) - playerList.size());
        for (int i = 0; i < number; i++) {
            playerList.add(new CPUPlayer(MessageLoader.loadMessage("sevens.name.cpu", i + 1)));
        }
    }

    /**
     * プレイヤーを生成する
     * 
     * @param playerList
     * @return
     * @throws SystemException 不明なエラーが発生した場合にスローする
     */
    private static void createHumanPlayer(List<Player> playerList) throws SystemException {
        System.out.println(MessageLoader.loadMessage("sevens.input.human"));
        int number = Keyboard.inputInt(1,
                Integer.parseInt(SettingLoader.loadSetting("sevens.player.max")) - playerList.size());
        for (int i = 0; i < number; i++) {
            System.out.println(MessageLoader.loadMessage("sevens.name.human", i + 1));
            playerList.add(new HumanPlayer(Keyboard.inputString()));
        }
    }
}
