package com.example.export.word;

public class Main {
	public static void main(String[] args) {
		
		String remark = "<span>展示内容</span><br>";
		try {
			WordGeneratorWithFreemarker.test(remark);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
