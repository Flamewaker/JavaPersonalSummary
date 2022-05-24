package com.atguigu.design.behavioral.chain.ext;

public class CharacterFilter implements Filter{
    @Override
    public void doFilter(Request request, Response response, FilterChain chain) {
        //功能
        request.msg +=" ====";

        System.out.println("CharacterFilter...doFilter之前");

        //放行
        chain.doFilter(request,response,chain);


        System.out.println("CharacterFilter...doFilter之后");
    }
}
