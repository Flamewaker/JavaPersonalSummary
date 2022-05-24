package com.atguigu.design.behavioral.chain.ext;

public class HttpFilter implements Filter{
    @Override
    public void doFilter(Request request, Response response, FilterChain chain) {
        //第一个filter的功能
        request.msg+=">>>";

        System.out.println("HttpFilter...doFilter之前");

        //放行
        chain.doFilter(request,response,chain);


        System.out.println("HttpFilter...doFilter之后");
    }
}
