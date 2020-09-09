package com.todd.concurrent.ConcurrentCollection;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class T03SynchronizedList {
	public static void main(String[] args) {
		List<String> strs = new ArrayList<>();
		List<String> strsSync = Collections.synchronizedList(strs);
	}
}
