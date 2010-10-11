/*
 * Copyright (C) 2010, Andiparos Project, Axel Neumann
 * 
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, see http://www.gnu.org/copyleft/
 * 
 */

package org.parosproxy.paros.utils;

import java.awt.Font;

import org.parosproxy.paros.model.Model;

public class FontHelper {

	public static Font baseFont = null;
	private static String baseFontName = null;
	private static int baseFontSize = 0;
	
	public static final String FALLBACK_BASE_FONT = "Lucida Grande";
	public static final int FALLBACK_BASE_SIZE = 12;
	
	
	public static Font codeFont = null;
	private static String codeFontName = null;
	private static int codeFontSize = 0;
	
	public static final String FALLBACK_CODE_FONT = "Courier New";
	public static final int FALLBACK_CODE_SIZE = 12;
	
	
	static {
		baseFontSize = Model.getSingleton().getOptionsParam().getFontsParam().getBaseFontSize();
	
		if (baseFontSize == 0) {
			baseFontSize = FALLBACK_BASE_SIZE;
		}
		
		baseFontName = Model.getSingleton().getOptionsParam().getFontsParam().getBaseFontName();
		
		if (baseFontName == null) {
			baseFontName = FALLBACK_BASE_FONT;
		}
		
		codeFontSize = Model.getSingleton().getOptionsParam().getFontsParam().getCodeFontSize();
		
		if (codeFontSize == 0) {
			codeFontSize = FALLBACK_CODE_SIZE;
		}
		
		codeFontName = Model.getSingleton().getOptionsParam().getFontsParam().getCodeFontName();
		
		if (codeFontName == null) {
			codeFontName = FALLBACK_CODE_FONT;
		}
		
		baseFont = new Font(getBaseFontName(), Font.PLAIN, getBaseFontSize());
		codeFont = new Font(getCodeFontName(), Font.PLAIN, getCodeFontSize());
		
	}
	
	public FontHelper() {
		
	}
	
	// Base
	private static int getBaseFontSize() {		
		return baseFontSize;
	}
	
	private static String getBaseFontName() {
		return baseFontName;
	}
	
	// Code
	private static int getCodeFontSize() {		
		return codeFontSize;
	}
	
	private static String getCodeFontName() {
		return codeFontName;
	}
	
	public static Font getBaseFont() {
		return baseFont;
	}
	
	public static Font getCodeFont() {
		return codeFont;
	}
	
}
