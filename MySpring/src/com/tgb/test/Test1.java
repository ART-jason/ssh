package com.tgb.test;

import static org.junit.Assert.*;

import java.util.Map;

import org.junit.Test;

import com.tgb.config.Bean;
import com.tgb.config.parse.ConfigManager;

public class Test1 {

	@Test
	public void test() {
		
		 Map<String, Bean> config = ConfigManager.getConfig("/applicationContext.xml");
		 
		 System.out.println(config);
	}

}
