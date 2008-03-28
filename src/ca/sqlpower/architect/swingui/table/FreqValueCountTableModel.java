/*
 * Copyright (c) 2007, SQL Power Group Inc.
 * 
 * All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 * 
 *     * Redistributions of source code must retain the above copyright
 *       notice, this list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above copyright
 *       notice, this list of conditions and the following disclaimer in
 *       the documentation and/or other materials provided with the
 *       distribution.
 *     * Neither the name of SQL Power Group Inc. nor the names of its
 *       contributors may be used to endorse or promote products derived
 *       from this software without specific prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
 * A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT
 * OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 * THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package ca.sqlpower.architect.swingui.table;

import java.text.NumberFormat;

import javax.swing.table.AbstractTableModel;

import ca.sqlpower.architect.profile.ColumnProfileResult;



public class FreqValueCountTableModel extends AbstractTableModel {

    private ColumnProfileResult profile;    
    
    private static final String COUNT="COUNT";
    private static final String VALUE="VALUE";
    private static final String PERCENT="PERCENT";
    
    public FreqValueCountTableModel(ColumnProfileResult profile) {
        super();
        this.profile = profile;
    }

    @Override
    public String getColumnName(int column) {
        if ( column == 0 ) {
            return COUNT;
        } else if ( column == 1 ) {
            return PERCENT;
        } else if (column == 2) {
            return VALUE;
        } else {
            throw new IllegalStateException("Unknown Column Index:"+column);
        }
    }
    
    public int getRowCount() {
        return profile==null?0:profile.getValueCount().size();
    }

    public int getColumnCount() {
        return 3;
    }

    public Object getValueAt(int rowIndex, int columnIndex) {
        if ( profile == null )
            return null;
        
        if ( columnIndex == 0 ) {
            return profile.getValueCount().get(rowIndex).getCount();
        } else if ( columnIndex == 1 ) {
            double d = profile.getValueCount().get(rowIndex).getPercent();
            if (d == -1) {
                //this is used to stop old saved files from breaking
                return "n/a";
            } else {
                NumberFormat percentFormat = NumberFormat.getPercentInstance();
                percentFormat.setMaximumFractionDigits(2);
                return percentFormat.format(d);
            }
        } else if (columnIndex == 2) {
            return profile.getValueCount().get(rowIndex).getValue();
        } else {
            throw new IllegalStateException("Unknown Column Index:"+columnIndex);
        }
    }

}
