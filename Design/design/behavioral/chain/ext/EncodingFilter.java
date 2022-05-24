package com.atguigu.design.behavioral.chain.ext;

public class EncodingFilter  implements Filter{
    @Override
    public void doFilter(Request request, Response response, FilterChain chain) {
        request.msg+=" oooo";
        System.out.println("EncodingFilter...doFilter之前");

        //放行
        chain.doFilter(request,response,chain);


        System.out.println("EncodingFilter...doFilter之后");
    }
}
