/**
 * ZH_JP翻译器
 * ZH_EN翻译器
 * .....
 */
public class Zh_JPTranslator implements Translator{
    @Override
    public String translate(String content) {
        if("你好".equals(content)){
            return "空尼几哇";
        }
        if ("什么".equals(content)){
            return "纳尼";
        }
        return "*******";
    }
}
