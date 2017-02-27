package com.ataybur.utils;

public class ContextFiller {
 private LineInfo lineInfo;
 private ContextHelper contextHelper = new ContextHelper();
		 
 public ContextFiller(LineInfo lineInfo){
	 this.lineInfo = lineInfo;
 }
 
	public ContextHelper applyLineInfo(){
		lineInfo.putParsedLineIntoContext(contextHelper);
		return contextHelper;
	}
}
