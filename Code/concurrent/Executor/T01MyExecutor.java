/**
 * 认识Executor
 */
package com.todd.concurrent.Executor;

import java.util.concurrent.Executor;

public class T01MyExecutor implements Executor{

	public static void main(String[] args) {
		new T01MyExecutor().execute(()->System.out.println("hello executor"));
	}

	@Override
	public void execute(Runnable command) {
		//new Thread(command).run();
		command.run();
		
	}

}

