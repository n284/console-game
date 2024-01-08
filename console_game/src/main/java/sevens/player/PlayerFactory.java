package sevens.player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import sevens.player.objects.CPUPlayer;
import sevens.player.objects.HumanPlayer;
import sevens.player.utilities.interfaces.Player;
import utilities.Input;
import utilities.exceptions.SystemException;

/**
 * プレイヤーを管理するクラス
 */
public class PlayerFactory {

    public static List<Player> createPlayer() throws SystemException {
        List<Player> playerList = new ArrayList<>();
        PlayerFactory.createHumanPlayer(playerList);

        if (playerList.size() < 4) {
            createCPUPlayer(playerList);
        }

        System.out.println("順番をランダムで決めます");
        Collections.shuffle(playerList);

        return playerList;
    }

    /**
     * CPUを生成する
     * 
     * @param playerList
     * @throws SystemException
     * @return
     */
    private static void createCPUPlayer(List<Player> playerList) throws SystemException {
        System.out.println("CPUの人数を入力してください");
        for (int i = 0; i < Input.inputInt(1, 4 - playerList.size()); i++) {
            playerList.add(new CPUPlayer("CPU_" + (i + 1)));
        }
    }

    /**
     * プレイヤーを生成する
     * 
     * @param playerList
     * @throws SystemException
     * @return
     */
    private static void createHumanPlayer(List<Player> playerList) throws SystemException {
        System.out.println("操作するプレイヤーの人数を入力してください");
        for (int i = 0; i < Input.inputInt(1, 4 - playerList.size()); i++) {
            System.out.println("プレイヤー" + (i + 1) + "の名前を入力してください");
            playerList.add(new HumanPlayer(Input.inputString()));
        }
    }
}
