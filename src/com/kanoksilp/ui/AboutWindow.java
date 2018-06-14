/*
 *         Java Media Player (MP3) Project
 * -------------------------------------------------
 *  Author: Kanoksilp Jindadoungrut, Sci 5733652723
 *  Course: [2301260] PROGRAMMING TECHNIQUES / 2014
 * -------------------------------------------------
 */

package com.kanoksilp.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Scanner;

import static com.kanoksilp.JMPApplication.R;
import static com.kanoksilp.util.TestingUtil.err;

/**
 *
 * @author Kanoksilp
 */
public class AboutWindow extends JDialog {
	
	public AboutWindow() {
        // set Look and Feel to system default
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
           err("@AboutWindow() : exception thrown while setting L&F:\n\t" + e);
        }

		this.initComponents();
        this.addActionListeners();
		this.setFonts();

		this.loadAboutText();
	}

	// <editor-fold desc="Component Initialization Codes">
	
	@SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        textAreaScrollPane = new javax.swing.JScrollPane();
        textArea = new javax.swing.JTextArea();
        closeButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("About");
        setAlwaysOnTop(true);
        setName("aboutDialog"); // NOI18N
        setResizable(false);

        textAreaScrollPane.setFocusable(false);

        textArea.setEditable(false);
        textArea.setFont(new java.awt.Font("Consolas", 0, 12)); // NOI18N
        textArea.setBorder(javax.swing.BorderFactory.createEmptyBorder(8, 8, 8, 8));
        textArea.setFocusable(false);
        textAreaScrollPane.setViewportView(textArea);

        closeButton.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        closeButton.setText("Close");
        closeButton.setPreferredSize(new java.awt.Dimension(72, 32));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(textAreaScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 680, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(closeButton, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(textAreaScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 420, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(closeButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton closeButton;
    private javax.swing.JTextArea textArea;
    private javax.swing.JScrollPane textAreaScrollPane;
    // End of variables declaration//GEN-END:variables

	// action listeners for buttons and checkbox
    @SuppressWarnings("unchecked")
    private void addActionListeners() {
        this.closeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                hideWindow();
            }
        });
    }

    private void setFonts() {
        this.textArea.setFont(new Font(R.getString("font.mono"), 0, 12));
        this.closeButton.setFont(new Font(R.getString("font.ui"), 0, 11));
    }

	// </editor-fold>

    private void hideWindow() {
        this.dispose();
    }

	private void loadAboutText() {
		Scanner scanner = new Scanner(getClass().getResourceAsStream(
                "/com/kanoksilp/ui/resource/about.txt"));
        while (scanner.hasNextLine()) {
            this.textArea.append(scanner.nextLine() + "\n");
        }
	}
}
