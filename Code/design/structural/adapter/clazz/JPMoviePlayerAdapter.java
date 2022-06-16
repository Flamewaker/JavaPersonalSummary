package com.todd.design.structural.adapter.clazz;

import com.todd.design.structural.adapter.Player;
import com.todd.design.structural.adapter.Zh_JPTranslator;

/**
 * 继承的方式：类结构模型，适配转换到了翻译器的功能上
 */
public class JPMoviePlayerAdapter extends Zh_JPTranslator implements Player {

    private Player target;//被适配对象
    public JPMoviePlayerAdapter(Player target){
        this.target = target;
    }

    @Override
    public String play() {

        String play = target.play();
        //转换字幕
        String translate = translate(play);
        System.out.println("日文："+translate);
        return translate;
    }
}
