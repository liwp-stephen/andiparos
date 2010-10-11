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

package org.parosproxy.paros.extension.option;

import org.parosproxy.paros.common.AbstractParam;

public class OptionsParamFonts extends AbstractParam {

	private static final String BASE_FONT_SIZE = "view.font.base.size";
	private static final String BASE_FONT_NAME = "view.font.base.name";
	private int baseFontSize = 0;
	private String baseFontName = null;
	
	private static final String CODE_FONT_SIZE = "view.font.code.size";
	private static final String CODE_FONT_NAME = "view.font.code.name";
	private int codeFontSize = 0;
	private String codeFontName = null;
	
	
    public OptionsParamFonts() {
    	
    }
    
    
    protected void parse() {
	    baseFontSize = getConfig().getInt(BASE_FONT_SIZE, 0);
	    baseFontName = getConfig().getString(BASE_FONT_NAME);
    }

	/**
	 * @return Returns the baseFontSize.
	 */
	public int getBaseFontSize() {
		return baseFontSize;
	}

	
	public void setBaseFontSize(int baseFontSize) {
		this.baseFontSize = baseFontSize;
		getConfig().setProperty(BASE_FONT_SIZE, baseFontSize);
	}
	
	
	/**
	 * @return Returns the baseFontName.
	 */
	public String getBaseFontName() {
		return baseFontName;
	}

	
	public void setBaseFontName(String baseFontName) {
		this.baseFontName = baseFontName;
		getConfig().setProperty(BASE_FONT_NAME, baseFontName);
	}
	
	
	
	/**
	 * @return Returns the codeFontSize.
	 */
	public int getCodeFontSize() {
		return codeFontSize;
	}

	
	public void setCodeFontSize(int codeFontSize) {
		this.codeFontSize = codeFontSize;
		getConfig().setProperty(CODE_FONT_SIZE, codeFontSize);
	}
	
	
	/**
	 * @return Returns the codeFontName.
	 */
	public String getCodeFontName() {
		return codeFontName;
	}

	
	public void setCodeFontName(String codeFontName) {
		this.codeFontName = codeFontName;
		getConfig().setProperty(CODE_FONT_NAME, codeFontName);
	}
	
}
