package utilities;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.Properties;

import utilities.exceptions.SystemException;

public class MessageLoader {
    private static final String MESSAGE_PROPERTIES_FILE_PATH = "../../resources/message.properties";
    private static Properties properties = new Properties();
    private static boolean setuped = false;

    private static void setup() throws SystemException {
        try {
            MessageLoader.properties.load(new FileInputStream(MESSAGE_PROPERTIES_FILE_PATH));
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

    public static String loadMessage(String messageCode) throws SystemException {
        if (MessageLoader.setuped) {
            MessageLoader.setup();
        } else {

        }

        return MessageLoader.properties.getProperty(messageCode);
    }

    public static String loadMessage(String messageCode, Integer... params) throws SystemException {
        if (MessageLoader.setuped) {
            MessageLoader.setup();
        } else {

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
