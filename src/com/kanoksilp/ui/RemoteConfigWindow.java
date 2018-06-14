/*
 *         Java Media Player (MP3) Project
 * -------------------------------------------------
 *  Author: Kanoksilp Jindadoungrut, Sci 5733652723
 *  Course: [2301260] PROGRAMMING TECHNIQUES / 2014
 * -------------------------------------------------
 */

package com.kanoksilp.ui;

import com.kanoksilp.JMPApplication;
import com.kanoksilp.network.ServerLogger;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;

import static com.kanoksilp.JMPApplication.R;
import static com.kanoksilp.util.TestingUtil.err;

/**
 *
 * @author Kanoksilp
 */
public class RemoteConfigWindow extends JDialog 
		implements ServerLogger {
	
	public RemoteConfigWindow() {
        // set Look and Feel to system default
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
           err("@RemoteConfigWindow() : exception thrown while setting L&F:\n\t" + e);
        }

		this.initComponents();
        this.addActionListeners();
        this.setFonts();
	}

	// <editor-fold desc="Component Initialization Codes">
	
	@SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        checkBox = new javax.swing.JCheckBox();
        textAreaScrollPane = new javax.swing.JScrollPane();
        textArea = new javax.swing.JTextArea();
        closeButton = new javax.swing.JButton();
        clearButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Android Remote Configuration");
        setResizable(false);

        checkBox.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        checkBox.setText("Turn on the server, allowing playback control by Android Remote app.");

        textAreaScrollPane.setFocusable(false);

        textArea.setEditable(false);
        textArea.setFont(new java.awt.Font("Consolas", 0, 12)); // NOI18N
        textArea.setBorder(javax.swing.BorderFactory.createEmptyBorder(8, 8, 8, 8));
        textArea.setFocusable(false);
        textAreaScrollPane.setViewportView(textArea);

        closeButton.setText("Close");
        closeButton.setPreferredSize(new java.awt.Dimension(72, 32));

        clearButton.setText("Clear Log");
        clearButton.setPreferredSize(new java.awt.Dimension(72, 32));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(checkBox)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(textAreaScrollPane)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(clearButton, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(closeButton, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(textAreaScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 207, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(checkBox)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(closeButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(clearButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox checkBox;
    private javax.swing.JButton clearButton;
    private javax.swing.JButton closeButton;
    private javax.swing.JTextArea textArea;
    private javax.swing.JScrollPane textAreaScrollPane;
    // End of variables declaration//GEN-END:variables

	// action listeners for buttons and checkbox
    @SuppressWarnings("unchecked")
    private void addActionListeners() {
        this.checkBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                toggleRemote();
            }
        });
        this.clearButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                clearLog();
            }
        });
        this.closeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                hideWindow();
            }
        });
    }

    private void setFonts() {
        String font_ui = R.getString("font.ui");

        this.textArea.setFont(new Font(R.getString("font.mono"), 0, 12));

        this.checkBox.setFont(new Font(font_ui, 0, 12));
        this.clearButton.setFont(new Font(font_ui, 0, 11));
        this.closeButton.setFont(new Font(font_ui, 0, 11));
    }

    // </editor-fold>
	
    private void toggleRemote() {
        if (checkBox.isSelected()) {
            JMPApplication.createServer();
            JMPApplication.setServerLogger(this);
            JMPApplication.startServer();
        } else {
            JMPApplication.stopServer();
        }
    }

    private void clearLog() {
        textArea.setText(null);
    }

    private void hideWindow() {
        this.dispose();
    }

	@Override
	public void append(String log) {
		String time = String.format("%tT > ", Calendar.getInstance().getTime());
		textArea.append(time + log);
		this.scrollToBottom();
	}

	@Override
	public void appendln(String line) {
		append(line + "\n");
		if (line.equals("server stopped.")) {
			this.checkBox.setSelected(false);
		}
	}
	
	private void scrollToBottom() {
		textAreaScrollPane.validate();
		textAreaScrollPane.getVerticalScrollBar().setValue(
				textAreaScrollPane.getVerticalScrollBar().getMaximum());
	}
}
