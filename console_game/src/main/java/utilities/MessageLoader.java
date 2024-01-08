package utilities;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.Properties;

import utilities.exceptions.SystemException;

/**
 * メッセージを読み込むクラス
 */
public class MessageLoader {
    /** message.propertiesが配置されているパス */
    private static final String MESSAGE_PROPERTIES_PATH = "../../resources/message.properties";

    /** プロパティズ */
    private static Properties properties;

    /** 初期処理済みを表す */
    private static boolean setuped;

    /**
     * static イニシャライザ
     */
    static {
        MessageLoader.properties = new Properties();
        MessageLoader.setuped = false;
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
            MessageLoader.properties.load(new FileInputStream(MESSAGE_PROPERTIES_PATH));
            MessageLoader.setuped = true;
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
     * 引数のキーに対応するメッセージを取得する
     * 
     * @param messageCode
     * @return {@link String}
     * @throws SystemException 処理の中断が必要な場合にスローする
     */
    public static String loadMessage(String messageCode) throws SystemException {
        if (MessageLoader.setuped) {
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
    public static String loadMessage(String messageCode, Integer... params) throws SystemException {
        if (MessageLoader.setuped) {
            MessageLoader.setup();
        }

        try {
            return MessageFormat.format(MessageLoader.properties.getProperty(messageCode), (Object[]) params);
        } catch (NullPointerException e) {
            throw new SystemException(500, "メッセージが取得できませんでした");
        } catch (IllegalArgumentException e) {
            throw new SystemException(500, "メッセージにパラメータが無いか、パラメータが適切ではありません");
        }
    }
}
