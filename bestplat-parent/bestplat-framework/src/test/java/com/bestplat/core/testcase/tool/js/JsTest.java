package com.bestplat.core.testcase.tool.js;

import org.junit.Test;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;

public class JsTest {
	@Test
	public void testExecute() {
		Context cx = Context.enter();
		try {
			Scriptable scope = cx.initStandardObjects();
			String s = "(function (s){//return 'Hello '+s+'!';\nreturn 32323*787878;})('world')";
			Object result = cx.evaluateString(scope, s, "<cmd>", 1, null);
			System.out.println(Context.toString(result));
		} finally {
			Context.exit();
		}
	}
}
