package utilities;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.MessageFormat;
import java.util.Properties;

import utilities.exceptions.SystemException;

/**
 * 設定を読み込むクラス
 */
public class SettingLoader {
    /** application.propertiesが配置されているパス */
    private static final String APPLICATION_PROPERTIES_PATH = "./console_game/src/main/resources/application.properties";

    /** プロパティズ */
    private static Properties properties = new Properties();

    /** 初期処理済みを表す */
    private static boolean setuped = false;

    /**
     * 初期化処理
     * 
     * @param
     * @return
     * @throws SystemException 処理の中断が必要な場合にスローする
     */
    private static void setup() throws SystemException {
        try {
            SettingLoader.properties
                    .load(new InputStreamReader(new FileInputStream(APPLICATION_PROPERTIES_PATH), "UTF-8"));
            SettingLoader.setuped = true;
        } catch (SecurityException e) {
            throw new SystemException("message.propertiesのセキュリティエラーが発生しました");
        } catch (FileNotFoundException e) {
            throw new SystemException("message.propertiesが見つかりませんでした");
        } catch (IOException e) {
            throw new SystemException("入力エラーが発生しました");
        } catch (IllegalArgumentException e) {
            throw new SystemException("message.properties内に不正な文字が含まれています");
        } catch (NullPointerException e) {
            throw new SystemException("message.propertiesが見つかりませんでした");
        }
    }

    /**
     * 引数のキーに対応する設定値を取得する
     * 
     * @param messageCode
     * @return int
     * @throws SystemException 処理の中断が必要な場合にスローする
     */
    public static int loadSetting(String messageCode) throws SystemException {
        try {
            if (!SettingLoader.setuped) {
                SettingLoader.setup();
            }

            return Integer.parseInt(SettingLoader.properties.getProperty(messageCode));
        } catch (NumberFormatException e) {
            throw new SystemException(MessageFormat.format("設定値が間違っています ({0})", messageCode));
        }
    }
}
