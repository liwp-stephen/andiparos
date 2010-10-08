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

	private static final String BASE_FONT_SIZE = "view.fontSize";

	private int baseFontSize = 0;
	
    public OptionsParamFonts() {
    }

    protected void parse() {
	    baseFontSize = getConfig().getInt(BASE_FONT_SIZE, 0);
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
	
}
