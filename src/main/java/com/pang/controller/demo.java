package com.pang.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class demo {
	public static void main(String[] args) throws Exception {
		List<String> mList = new ArrayList<>();
		mList.add("北京");
		System.out.println(String.join("-", mList));
	}

}
