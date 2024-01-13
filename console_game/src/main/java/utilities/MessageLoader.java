package utilities;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.MessageFormat;
import java.util.Properties;

import utilities.exceptions.SystemException;

/**
 * メッセージを読み込むクラス
 */
public class MessageLoader {
    /** message.propertiesが配置されているパス */
    private static final String MESSAGE_PROPERTIES_PATH = "./console_game/src/main/resources/message.properties";

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
            MessageLoader.properties.load(new InputStreamReader(new FileInputStream(MESSAGE_PROPERTIES_PATH), "UTF-8"));
            MessageLoader.setuped = true;
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
     * 引数のキーに対応するメッセージを取得する
     * 
     * @param messageCode
     * @return {@link String}
     * @throws SystemException 処理の中断が必要な場合にスローする
     */
    public static String loadMessage(String messageCode) throws SystemException {
        if (!MessageLoader.setuped) {
            MessageLoader.setup();
        }

        return MessageLoader.properties.getProperty(messageCode);
    }

    /**
     * 引数のキーに対応するメッセージに値を挿入して取得する
     * 
     * @param messageCode
     * @param params
     * @return {@link String}
     * @throws SystemException 処理の中断が必要な場合にスローする
     */
    public static String loadMessage(String messageCode, Object... params) throws SystemException {
        try {
            if (MessageLoader.setuped) {
                MessageLoader.setup();
            }

            return MessageFormat.format(MessageLoader.properties.getProperty(messageCode), params);
        } catch (NullPointerException e) {
            throw new SystemException("メッセージが取得できませんでした");
        } catch (IllegalArgumentException e) {
            throw new SystemException("メッセージにパラメータが無いか、パラメータが適切ではありません");
        }
    }
}
