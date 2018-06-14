/*
 *         Java Media Player (MP3) Project
 * -------------------------------------------------
 *  Author: Kanoksilp Jindadoungrut, Sci 5733652723
 *  Course: [2301260] PROGRAMMING TECHNIQUES / 2014
 * -------------------------------------------------
 */

package com.kanoksilp.ui;

/**
 *
 * @author Kanoksilp
 */
public class TrackListCellRendererDesign extends javax.swing.JPanel {

	/**
	 * Creates new form NewJPanel
	 */
	public TrackListCellRendererDesign() {
		initComponents();
	}

	@SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        imageLabel = new javax.swing.JLabel();
        textPanel = new javax.swing.JPanel();
        titleLabel = new javax.swing.JLabel();
        infoLabel = new javax.swing.JLabel();

        setMinimumSize(new java.awt.Dimension(118, 52));
        setPreferredSize(new java.awt.Dimension(400, 52));
        setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        imageLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        imageLabel.setText("art");
        imageLabel.setMinimumSize(new java.awt.Dimension(48, 48));
        imageLabel.setPreferredSize(new java.awt.Dimension(48, 48));
        add(imageLabel);

        textPanel.setLayout(new java.awt.GridLayout(2, 0, 0, 4));

        titleLabel.setFont(new java.awt.Font("Meiryo UI", 0, 11)); // NOI18N
        titleLabel.setText("Song Title");
        textPanel.add(titleLabel);

        infoLabel.setFont(new java.awt.Font("Meiryo UI", 0, 11)); // NOI18N
        infoLabel.setText("Artist");
        textPanel.add(infoLabel);

        add(textPanel);
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel imageLabel;
    private javax.swing.JLabel infoLabel;
    private javax.swing.JPanel textPanel;
    private javax.swing.JLabel titleLabel;
    // End of variables declaration//GEN-END:variables
}
