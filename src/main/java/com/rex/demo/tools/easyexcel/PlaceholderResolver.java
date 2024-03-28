package com.rex.demo.tools.easyexcel;


import java.util.Map;
import java.util.Properties;
import java.util.function.Function;
import java.util.stream.Stream;

/**
 * 佔位符解析器
 */
public class PlaceholderResolver {
    /**
     * 預設前綴佔位符
     */
    public static final String DEFAULT_PLACEHOLDER_PREFIX = "${";

    /**
     * 預設後綴佔位符
     */
    public static final String DEFAULT_PLACEHOLDER_SUFFIX = "}";

    /**
     * 預設單例解析器
     */
    private static PlaceholderResolver defaultResolver = new PlaceholderResolver();

    /**
     * 佔位符前綴
     */
    private String placeholderPrefix = DEFAULT_PLACEHOLDER_PREFIX;

    /**
     * 佔位符後綴
     */
    private String placeholderSuffix = DEFAULT_PLACEHOLDER_SUFFIX;


    private PlaceholderResolver() {
    }

    private PlaceholderResolver(String placeholderPrefix, String placeholderSuffix) {
        this.placeholderPrefix = placeholderPrefix;
        this.placeholderSuffix = placeholderSuffix;
    }

    /**
     * 取得預設的佔位符解析器，即佔位符前綴為"${", 後綴為"}"
     *
     * @return
     */
    public static PlaceholderResolver getDefaultResolver() {
        return defaultResolver;
    }

    public static PlaceholderResolver getResolver(String placeholderPrefix, String placeholderSuffix) {
        return new PlaceholderResolver(placeholderPrefix, placeholderSuffix);
    }

    /**
     * 解析帶有指定佔位符的模板字串，預設佔位符為前綴：${ 後綴：}<br/><br/>
     * 如： template = category:${}:product:${}<br/>
     * values = {"1", "2"}<br/>
     * 返回 category:1:product:2<br/>
     *
     * @param content 要解析的帶有佔位符的模板字串
     * @param values  依照範本佔位符索引位置設定對應的值
     * @return
     */
    public String resolve(String content, String... values) {
        int start = content.indexOf(this.placeholderPrefix);
        if (start == -1) {
            return content;
        }
        //值索引
        int valueIndex = 0;
        StringBuilder result = new StringBuilder(content);
        while (start != -1) {
            int end = result.indexOf(this.placeholderSuffix);
            String replaceContent = values[valueIndex++];
            result.replace(start, end + this.placeholderSuffix.length(), replaceContent);
            start = result.indexOf(this.placeholderPrefix, start + replaceContent.length());
        }
        return result.toString();
    }

    /**
     * 解析帶有指定佔位符的模板字串，預設佔位符為前綴：${ 後綴：}<br/><br/>
     * 如： template = category:${}:product:${}<br/>
     * values = {"1", "2"}<br/>
     * 返回 category:1:product:2<br/>
     *
     * @param content 要解析的帶有佔位符的模板字串
     * @param values  依照範本佔位符索引位置設定對應的值
     * @return
     */
    public String resolve(String content, Object[] values) {
        return resolve(content, Stream.of(values).map(String::valueOf).toArray(String[]::new));
    }

    /**
     * 根據替換規則來取代指定範本中的佔位符值
     *
     * @param content 要解析的字串
     * @param rule    解析規則回呼
     * @return
     */
    public String resolveByRule(String content, Function<String, String> rule) {
        int start = content.indexOf(this.placeholderPrefix);
        if (start == -1) {
            return content;
        }
        StringBuilder result = new StringBuilder(content);
        while (start != -1) {
            int end = result.indexOf(this.placeholderSuffix, start);
            //获取占位符属性值，如${id}, 即获取id
            String placeholder = result.substring(start + this.placeholderPrefix.length(), end);
            //替换整个占位符内容，即将${id}值替换为替换规则回调中的内容
            String replaceContent = placeholder.trim().isEmpty() ? "" : rule.apply(placeholder);
            result.replace(start, end + this.placeholderSuffix.length(), replaceContent);
            start = result.indexOf(this.placeholderPrefix, start + replaceContent.length());
        }
        return result.toString();
    }

    /**
     * 取代範本中佔位符內容，佔位符的內容即為map key對應的值，key為佔位符中的內容。 <br/><br/>
     * 如：content = product:${id}:detail:${did}<br/>
     * valueMap = id -> 1; pid -> 2<br/>
     * 經過解析回傳 product:1:detail:2<br/>
     *
     * @param content  範本內容。
     * @param valueMap 值映射
     * @return 取代完成後的字串。
     */
    public String resolveByMap(String content, final Map<String, Object> valueMap) {
        return resolveByRule(content, placeholderValue -> String.valueOf(valueMap.get(placeholderValue)));
    }

    /**
     * 根據properties檔案取代佔位符內容
     *
     * @param content
     * @param properties
     * @return
     */
    public String resolveByProperties(String content, final Properties properties) {
        return resolveByRule(content, placeholderValue -> properties.getProperty(placeholderValue));
    }

}
