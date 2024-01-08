package utilities.interfaces;

import utilities.exceptions.SystemException;

public interface Game {
    /**
     * ゲームを実行する
     * 
     * @param
     * @throws SystemException 処理を停止する必要がある場合にスローする
     * @return
     */
    public void run() throws SystemException;
}