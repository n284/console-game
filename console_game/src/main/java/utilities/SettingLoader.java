package utilities;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

import utilities.exceptions.SystemException;

/**
 * 設定を読み込むクラス
 */
public class SettingLoader {
    /** application.propertiesが配置されているパス */
    private static final String APPLICATION_PROPERTIES_PATH = "./console_game/src/main/resources/application.properties";

    /** プロパティズ */
    private static Properties properties;

    /** 初期処理済みを表す */
    private static boolean setuped;

    /**
     * static イニシャライザ
     */
    static {
        SettingLoader.properties = new Properties();
        SettingLoader.setuped = false;
    }

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
            throw new SystemException(500, "message.propertiesのセキュリティエラーが発生しました");
        } catch (FileNotFoundException e) {
            throw new SystemException(500, "message.propertiesが見つかりませんでした");
        } catch (IOException e) {
            throw new SystemException(500, "入力エラーが発生しました");
        } catch (IllegalArgumentException e) {
            throw new SystemException(500, "message.properties内に不正な文字が含まれています");
        } catch (NullPointerException e) {
            throw new SystemException(500, "message.propertiesが見つかりませんでした");
        }
    }

    /**
     * 引数のキーに対応する設定値を取得する
     * 
     * @param messageCode
     * @return {@link String}
     * @throws SystemException 処理の中断が必要な場合にスローする
     */
    public static String loadSetting(String messageCode) throws SystemException {
        if (!SettingLoader.setuped) {
            SettingLoader.setup();
        }

        return SettingLoader.properties.getProperty(messageCode);
    }
}
