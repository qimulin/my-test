package zhou.wu.boot.web.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import java.util.Locale;

/**
 * @author zhou.wu
 * @date 2024/2/29
 **/
@Component
public class I18nMessageUtils {

    private static MessageSource messageSource;

    @Autowired
    public void setMessageSource(MessageSource messageSource){
        I18nMessageUtils.messageSource = messageSource;
    }

    public static String getMessage(String code){
        return getMessage(code, null);
    }

    /**
     * 获取消息
     *
     * @param code 参数是消息的代码或键，用于在 properties 文件中查找对应的消息文本。
     * @param args 参数是一个可选的对象数组，用于传递消息中的参数值。如果消息中包含占位符，这些参数值将替换占位符的位置。
     * @return 消息
     * @author zhou.wu
     * @date 2024/3/1
     */
    public static String getMessage(String code, Object[] args){
        /*
            LocaleContextHolder.getLocale() 中文：Locale.SIMPLIFIED_CHINESE 英文： Locale.US
            如果你没有显式地设置Accept-Language请求头，那么LocaleContextHolder.getLocale()默认会根据服务器的默认Locale进行设置。
            在大多数情况下，服务器的默认Locale是根据操作系统的默认Locale来确定的。

        */
        Locale locale = LocaleContextHolder.getLocale();
        // 写死返回默认语言示例，也是对应操作系统的
//        locale = Locale.getDefault();
        System.out.println("Current Locale: " + locale);
        /**
         * 根据消息键和参数 获取消息 委托给spring messageSource
         * 【提醒】具体可以尝试看源码，比较清晰
         * - 当找不到对应的locale时，会取操作系统默认的Locale
         * - message.properties的作用相当于最后一道防线，当在对应Locale中找不到code对应信息的时候，看message.properties中是否存在对应的信息，有的话返回，没有的话报错
         * @param code 消息键
         * @param args 参数
         * @return 获取国际化翻译值
         */
        return messageSource.getMessage(code, args, locale);
    }

}
