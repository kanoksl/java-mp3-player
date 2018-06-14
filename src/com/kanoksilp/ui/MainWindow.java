/*
 *         Java Media Player (MP3) Project
 * -------------------------------------------------
 *  Author: Kanoksilp Jindadoungrut, Sci 5733652723
 *  Course: [2301260] PROGRAMMING TECHNIQUES / 2014
 * -------------------------------------------------
 */
package com.kanoksilp.ui;

import com.kanoksilp.data.Playlist;
import com.kanoksilp.data.Track;
import com.kanoksilp.network.CommandListener;
import com.kanoksilp.network.MediaControlServer.ServerStatus;
import com.kanoksilp.network.ServerStatusWatcher;
import com.kanoksilp.player.ImprovedPlayerController;
import com.kanoksilp.player.TrackEndedListener;
import com.kanoksilp.player.TrackEndedNotifier;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.*;
import java.io.FileNotFoundException;

import static com.kanoksilp.JMPApplication.R;
import static com.kanoksilp.util.TestingUtil.err;
import static com.kanoksilp.util.TestingUtil.out;

/**
 * @author Kanoksilp
 */
public class MainWindow extends JFrame
		implements CommandListener, ServerStatusWatcher {


	public MainWindow() {
		// set Look and Feel to system default
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
            err("@MainWindow() : exception thrown while setting L&F:\n\t" + e);
		}

		// set the startup position to the center of the screen
		this.setLocationRelativeTo(null);

		// initialize components from NetBeans-generated code
		this.initComponents();

		// continue initialize components from custom code
		this.initComponentsMore();
		this.addActionListeners();
        this.addMenuActionListeners();
        this.setFonts();

        mnuSave.setEnabled(false);

		this.updateNowPlayingInfo(null);
		this.updateServerStatus(ServerStatus.NOT_RUNNING);
	}


    // <editor-fold desc="Component Initialization Codes">

	@SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.JPanel bottomPanel = new javax.swing.JPanel();
        playbackStatusLabel = new javax.swing.JLabel();
        trackCountLabel = new javax.swing.JLabel();
        serverStatusLabel = new javax.swing.JLabel();
        javax.swing.JPanel leftPanel = new javax.swing.JPanel();
        seekBar = new javax.swing.JProgressBar();
        albumArtLabel = new javax.swing.JLabel();
        javax.swing.JPanel controlButtonsPanel = new javax.swing.JPanel();
        cbPrev = new javax.swing.JButton();
        cbPlayPause = new javax.swing.JButton();
        cbNext = new javax.swing.JButton();
        ctbRepeat = new javax.swing.JToggleButton();
        ctbShuffle = new javax.swing.JToggleButton();
        javax.swing.JPanel volumeControlPanel = new javax.swing.JPanel();
        ctbMute = new javax.swing.JToggleButton();
        volumeSlider = new javax.swing.JSlider();
        javax.swing.JPanel infoPanel = new javax.swing.JPanel();
        nowPlayingLabel = new javax.swing.JLabel();
        infoTitleLabel = new javax.swing.JLabel();
        infoArtistLabel = new javax.swing.JLabel();
        infoAlbumLabel = new javax.swing.JLabel();
        infoMP3Technical = new javax.swing.JLabel();
        javax.swing.JPanel playlistManagementPanel = new javax.swing.JPanel();
        pmbSaveOrdering = new javax.swing.JButton();
        pmbMoveUp = new javax.swing.JButton();
        pmbMoveDown = new javax.swing.JButton();
        pmbDelete = new javax.swing.JButton();
        listScrollPane = new javax.swing.JScrollPane();
        list = new javax.swing.JList();
        javax.swing.JMenuBar menuBar = new javax.swing.JMenuBar();
        javax.swing.JMenu fileMenu = new javax.swing.JMenu();
        mnuAdd = new javax.swing.JMenuItem();
        mnuAddFolder = new javax.swing.JMenuItem();
        mnuAddFolderAndSub = new javax.swing.JMenuItem();
        mnuAddPlaylist = new javax.swing.JMenuItem();
        javax.swing.JPopupMenu.Separator jSeparator1 = new javax.swing.JPopupMenu.Separator();
        mnuOpen = new javax.swing.JMenuItem();
        javax.swing.JPopupMenu.Separator jSeparator3 = new javax.swing.JPopupMenu.Separator();
        mnuSave = new javax.swing.JMenuItem();
        mnuSaveAs = new javax.swing.JMenuItem();
        mnuSaveAsRelative = new javax.swing.JMenuItem();
        javax.swing.JPopupMenu.Separator jSeparator6 = new javax.swing.JPopupMenu.Separator();
        mnuClear = new javax.swing.JMenuItem();
        javax.swing.JPopupMenu.Separator jSeparator4 = new javax.swing.JPopupMenu.Separator();
        mnuExit = new javax.swing.JMenuItem();
        javax.swing.JMenu mnuTools = new javax.swing.JMenu();
        mnuToolsRemote = new javax.swing.JMenuItem();
        javax.swing.JPopupMenu.Separator jSeparator5 = new javax.swing.JPopupMenu.Separator();
        javax.swing.JMenu helpMenu = new javax.swing.JMenu();
        mnuAbout = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        setMinimumSize(new java.awt.Dimension(372, 680));
        setPreferredSize(new java.awt.Dimension(840, 620));

        bottomPanel.setBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createMatteBorder(1, 0, 0, 0, javax.swing.UIManager.getDefaults().getColor("MenuBar.shadow")), javax.swing.BorderFactory.createMatteBorder(1, 0, 0, 0, javax.swing.UIManager.getDefaults().getColor("MenuBar.highlight"))));
        bottomPanel.setMaximumSize(new java.awt.Dimension(32767, 32));
        bottomPanel.setPreferredSize(new java.awt.Dimension(255, 32));

        playbackStatusLabel.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        playbackStatusLabel.setText("00:00 / 00:00");
        playbackStatusLabel.setEnabled(false);
        playbackStatusLabel.setMaximumSize(new java.awt.Dimension(270, 15));

        trackCountLabel.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        trackCountLabel.setText("Tracks: 0");
        trackCountLabel.setEnabled(false);
        trackCountLabel.setMinimumSize(new java.awt.Dimension(0, 15));

        serverStatusLabel.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        serverStatusLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        serverStatusLabel.setText("Server running. \nRemote connected.");
        serverStatusLabel.setEnabled(false);
        serverStatusLabel.setMinimumSize(new java.awt.Dimension(0, 15));

        javax.swing.GroupLayout bottomPanelLayout = new javax.swing.GroupLayout(bottomPanel);
        bottomPanel.setLayout(bottomPanelLayout);
        bottomPanelLayout.setHorizontalGroup(
            bottomPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(bottomPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(playbackStatusLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 270, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(trackCountLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 179, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(serverStatusLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        bottomPanelLayout.setVerticalGroup(
            bottomPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(bottomPanelLayout.createSequentialGroup()
                .addGap(7, 7, 7)
                .addGroup(bottomPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(trackCountLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(playbackStatusLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(serverStatusLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        leftPanel.setBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 0, 1, javax.swing.UIManager.getDefaults().getColor("MenuBar.highlight")), javax.swing.BorderFactory.createMatteBorder(0, 0, 0, 1, javax.swing.UIManager.getDefaults().getColor("MenuBar.shadow"))));
        leftPanel.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        leftPanel.setPreferredSize(new java.awt.Dimension(282, 394));

        seekBar.setValue(50);
        seekBar.setBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, javax.swing.UIManager.getDefaults().getColor("MenuBar.highlight")), javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, javax.swing.UIManager.getDefaults().getColor("MenuBar.shadow"))));
        seekBar.setPreferredSize(new java.awt.Dimension(146, 12));

        albumArtLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        albumArtLabel.setOpaque(true);
        albumArtLabel.setPreferredSize(new java.awt.Dimension(280, 280));

        controlButtonsPanel.setBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, javax.swing.UIManager.getDefaults().getColor("MenuBar.highlight")), javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, javax.swing.UIManager.getDefaults().getColor("MenuBar.shadow"))));
        controlButtonsPanel.setPreferredSize(new java.awt.Dimension(235, 68));

        cbPrev.setFont(new java.awt.Font("Segoe UI Symbol", 0, 24)); // NOI18N
        cbPrev.setText("");
        cbPrev.setToolTipText("Previous Song");
        cbPrev.setPreferredSize(new java.awt.Dimension(64, 64));

        cbPlayPause.setFont(new java.awt.Font("Segoe UI Symbol", 0, 24)); // NOI18N
        cbPlayPause.setText("");
        cbPlayPause.setToolTipText("Play / Pause");
        cbPlayPause.setMargin(new java.awt.Insets(0, 0, 0, 0));
        cbPlayPause.setPreferredSize(new java.awt.Dimension(80, 64));

        cbNext.setFont(new java.awt.Font("Segoe UI Symbol", 0, 24)); // NOI18N
        cbNext.setToolTipText("Next Song");
        cbNext.setLabel("");
        cbNext.setPreferredSize(new java.awt.Dimension(64, 64));

        ctbRepeat.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        ctbRepeat.setToolTipText("Toggle repeating current playlist");
        ctbRepeat.setLabel("Repeat");
        ctbRepeat.setMargin(new java.awt.Insets(0, 0, 0, 0));
        ctbRepeat.setMinimumSize(new java.awt.Dimension(64, 32));
        ctbRepeat.setPreferredSize(new java.awt.Dimension(64, 32));

        ctbShuffle.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        ctbShuffle.setToolTipText("Toggle shuffling song in this list");
        ctbShuffle.setLabel("Shuffle");
        ctbShuffle.setMargin(new java.awt.Insets(0, 0, 0, 0));
        ctbShuffle.setMinimumSize(new java.awt.Dimension(64, 32));
        ctbShuffle.setPreferredSize(new java.awt.Dimension(64, 32));

        javax.swing.GroupLayout controlButtonsPanelLayout = new javax.swing.GroupLayout(controlButtonsPanel);
        controlButtonsPanel.setLayout(controlButtonsPanelLayout);
        controlButtonsPanelLayout.setHorizontalGroup(
            controlButtonsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(controlButtonsPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(cbPrev, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(cbPlayPause, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(cbNext, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(2, 2, 2)
                .addGroup(controlButtonsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(ctbRepeat, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(ctbShuffle, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addContainerGap())
        );
        controlButtonsPanelLayout.setVerticalGroup(
            controlButtonsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, controlButtonsPanelLayout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addGroup(controlButtonsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cbPlayPause, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(controlButtonsPanelLayout.createSequentialGroup()
                        .addComponent(ctbRepeat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(ctbShuffle, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(cbPrev, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbNext, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, 0))
        );

        volumeControlPanel.setBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, javax.swing.UIManager.getDefaults().getColor("MenuBar.highlight")), javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, javax.swing.UIManager.getDefaults().getColor("MenuBar.shadow"))));
        volumeControlPanel.setPreferredSize(new java.awt.Dimension(235, 68));

        ctbMute.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        ctbMute.setText("Mute");
        ctbMute.setToolTipText("Mute the sound");
        ctbMute.setMargin(new java.awt.Insets(0, 0, 0, 0));
        ctbMute.setMinimumSize(new java.awt.Dimension(64, 32));
        ctbMute.setPreferredSize(new java.awt.Dimension(64, 32));

        volumeSlider.setMajorTickSpacing(10);
        volumeSlider.setMinorTickSpacing(2);
        volumeSlider.setPaintTicks(true);
        volumeSlider.setToolTipText("Volume");
        volumeSlider.setValue(85);

        javax.swing.GroupLayout volumeControlPanelLayout = new javax.swing.GroupLayout(volumeControlPanel);
        volumeControlPanel.setLayout(volumeControlPanelLayout);
        volumeControlPanelLayout.setHorizontalGroup(
            volumeControlPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(volumeControlPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(ctbMute, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(2, 2, 2)
                .addComponent(volumeSlider, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        volumeControlPanelLayout.setVerticalGroup(
            volumeControlPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(volumeControlPanelLayout.createSequentialGroup()
                .addGap(2, 2, 2)
                .addGroup(volumeControlPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(volumeSlider, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ctbMute, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(2, 2, 2))
        );

        infoPanel.setBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, javax.swing.UIManager.getDefaults().getColor("MenuBar.highlight")), javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, javax.swing.UIManager.getDefaults().getColor("MenuBar.shadow"))));
        infoPanel.setPreferredSize(new java.awt.Dimension(235, 68));

        nowPlayingLabel.setFont(new java.awt.Font("Meiryo UI", 1, 11)); // NOI18N
        nowPlayingLabel.setForeground(new java.awt.Color(102, 102, 102));
        nowPlayingLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        nowPlayingLabel.setText("Now Playing");

        infoTitleLabel.setFont(new java.awt.Font("Meiryo UI", 0, 12)); // NOI18N
        infoTitleLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        infoTitleLabel.setText("Title");

        infoArtistLabel.setFont(new java.awt.Font("Meiryo UI", 0, 11)); // NOI18N
        infoArtistLabel.setForeground(new java.awt.Color(102, 102, 102));
        infoArtistLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        infoArtistLabel.setText("Artist");

        infoAlbumLabel.setFont(new java.awt.Font("Meiryo UI", 0, 11)); // NOI18N
        infoAlbumLabel.setForeground(new java.awt.Color(102, 102, 102));
        infoAlbumLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        infoAlbumLabel.setText("Album (year)");

        infoMP3Technical.setFont(new java.awt.Font("Meiryo UI", 0, 11)); // NOI18N
        infoMP3Technical.setForeground(new java.awt.Color(153, 153, 153));
        infoMP3Technical.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        infoMP3Technical.setText("mp3 info");

        javax.swing.GroupLayout infoPanelLayout = new javax.swing.GroupLayout(infoPanel);
        infoPanel.setLayout(infoPanelLayout);
        infoPanelLayout.setHorizontalGroup(
            infoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(infoPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(infoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(infoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(nowPlayingLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(infoTitleLabel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(infoAlbumLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(infoArtistLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(infoMP3Technical, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        infoPanelLayout.setVerticalGroup(
            infoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(infoPanelLayout.createSequentialGroup()
                .addComponent(nowPlayingLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(infoTitleLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(infoArtistLabel)
                .addGap(1, 1, 1)
                .addComponent(infoAlbumLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(infoMP3Technical)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        playlistManagementPanel.setBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, javax.swing.UIManager.getDefaults().getColor("MenuBar.highlight")), javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, javax.swing.UIManager.getDefaults().getColor("MenuBar.shadow"))));
        playlistManagementPanel.setPreferredSize(new java.awt.Dimension(235, 68));

        pmbSaveOrdering.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        pmbSaveOrdering.setText("Save Track Order");
        pmbSaveOrdering.setMargin(new java.awt.Insets(0, 0, 0, 0));

        pmbMoveUp.setFont(new java.awt.Font("Segoe UI Symbol", 0, 14)); // NOI18N
        pmbMoveUp.setText("");
        pmbMoveUp.setToolTipText("Move the selected track up");
        pmbMoveUp.setMargin(new java.awt.Insets(0, 0, 0, 0));

        pmbMoveDown.setFont(new java.awt.Font("Segoe UI Symbol", 0, 14)); // NOI18N
        pmbMoveDown.setText("");
        pmbMoveDown.setToolTipText("Move the selected track down");
        pmbMoveDown.setMargin(new java.awt.Insets(0, 0, 0, 0));

        pmbDelete.setFont(new java.awt.Font("Segoe UI Symbol", 0, 14)); // NOI18N
        pmbDelete.setText("");
        pmbDelete.setToolTipText("Remove selected tracks");
        pmbDelete.setMargin(new java.awt.Insets(0, 0, 0, 0));

        javax.swing.GroupLayout playlistManagementPanelLayout = new javax.swing.GroupLayout(playlistManagementPanel);
        playlistManagementPanel.setLayout(playlistManagementPanelLayout);
        playlistManagementPanelLayout.setHorizontalGroup(
            playlistManagementPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(playlistManagementPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pmbSaveOrdering, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(pmbDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pmbMoveDown, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(pmbMoveUp, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        playlistManagementPanelLayout.setVerticalGroup(
            playlistManagementPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(playlistManagementPanelLayout.createSequentialGroup()
                .addGroup(playlistManagementPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(pmbSaveOrdering, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(playlistManagementPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(pmbMoveUp)
                        .addComponent(pmbMoveDown)
                        .addComponent(pmbDelete)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout leftPanelLayout = new javax.swing.GroupLayout(leftPanel);
        leftPanel.setLayout(leftPanelLayout);
        leftPanelLayout.setHorizontalGroup(
            leftPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(albumArtLabel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(seekBar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(controlButtonsPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 280, Short.MAX_VALUE)
            .addComponent(volumeControlPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 280, Short.MAX_VALUE)
            .addComponent(infoPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 280, Short.MAX_VALUE)
            .addComponent(playlistManagementPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 280, Short.MAX_VALUE)
        );
        leftPanelLayout.setVerticalGroup(
            leftPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, leftPanelLayout.createSequentialGroup()
                .addComponent(albumArtLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 280, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(seekBar, javax.swing.GroupLayout.PREFERRED_SIZE, 12, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(controlButtonsPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(volumeControlPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(infoPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(playlistManagementPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        listScrollPane.setBorder(null);
        listScrollPane.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        list.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        listScrollPane.setViewportView(list);

        menuBar.setPreferredSize(new java.awt.Dimension(89, 24));

        fileMenu.setMnemonic('f');
        fileMenu.setText("File");

        mnuAdd.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_A, java.awt.event.InputEvent.ALT_MASK));
        mnuAdd.setMnemonic('a');
        mnuAdd.setText("Add Songs ...");
        fileMenu.add(mnuAdd);

        mnuAddFolder.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_A, java.awt.event.InputEvent.ALT_MASK | java.awt.event.InputEvent.SHIFT_MASK));
        mnuAddFolder.setMnemonic('f');
        mnuAddFolder.setText("Add From Folder ...");
        fileMenu.add(mnuAddFolder);

        mnuAddFolderAndSub.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_B, java.awt.event.InputEvent.ALT_MASK | java.awt.event.InputEvent.SHIFT_MASK));
        mnuAddFolderAndSub.setMnemonic('f');
        mnuAddFolderAndSub.setText("Add From Folder and its Subfolders ...");
        fileMenu.add(mnuAddFolderAndSub);

        mnuAddPlaylist.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_A, java.awt.event.InputEvent.ALT_MASK | java.awt.event.InputEvent.SHIFT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        mnuAddPlaylist.setMnemonic('p');
        mnuAddPlaylist.setText("Add From Another Playlist ...");
        fileMenu.add(mnuAddPlaylist);
        fileMenu.add(jSeparator1);

        mnuOpen.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_O, java.awt.event.InputEvent.SHIFT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        mnuOpen.setMnemonic('o');
        mnuOpen.setText("Open Playlist ...");
        fileMenu.add(mnuOpen);
        fileMenu.add(jSeparator3);

        mnuSave.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.CTRL_MASK));
        mnuSave.setText("Save Current Playlist");
        fileMenu.add(mnuSave);

        mnuSaveAs.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.SHIFT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        mnuSaveAs.setText("Save Current Playlist As ...");
        fileMenu.add(mnuSaveAs);

        mnuSaveAsRelative.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.ALT_MASK | java.awt.event.InputEvent.SHIFT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        mnuSaveAsRelative.setText("Save Current Playlist As ... (Using Relative Filepaths)");
        fileMenu.add(mnuSaveAsRelative);
        fileMenu.add(jSeparator6);

        mnuClear.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_C, java.awt.event.InputEvent.ALT_MASK));
        mnuClear.setMnemonic('c');
        mnuClear.setText("Clear Playlist");
        fileMenu.add(mnuClear);
        fileMenu.add(jSeparator4);

        mnuExit.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_X, java.awt.event.InputEvent.ALT_MASK));
        mnuExit.setMnemonic('x');
        mnuExit.setText("Exit");
        fileMenu.add(mnuExit);

        menuBar.add(fileMenu);

        mnuTools.setMnemonic('e');
        mnuTools.setText("Tools");

        mnuToolsRemote.setMnemonic('t');
        mnuToolsRemote.setText("Android Remote Configuration ...");
        mnuTools.add(mnuToolsRemote);
        mnuTools.add(jSeparator5);

        menuBar.add(mnuTools);

        helpMenu.setMnemonic('h');
        helpMenu.setText("Help");

        mnuAbout.setMnemonic('a');
        mnuAbout.setText("About ...");
        helpMenu.add(mnuAbout);

        menuBar.add(helpMenu);

        setJMenuBar(menuBar);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(leftPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(listScrollPane))
            .addComponent(bottomPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 827, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(leftPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 564, Short.MAX_VALUE)
                    .addComponent(listScrollPane))
                .addComponent(bottomPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    javax.swing.JLabel albumArtLabel;
    javax.swing.JButton cbNext;
    javax.swing.JButton cbPlayPause;
    javax.swing.JButton cbPrev;
    javax.swing.JToggleButton ctbMute;
    javax.swing.JToggleButton ctbRepeat;
    javax.swing.JToggleButton ctbShuffle;
    javax.swing.JLabel infoAlbumLabel;
    javax.swing.JLabel infoArtistLabel;
    javax.swing.JLabel infoMP3Technical;
    javax.swing.JLabel infoTitleLabel;
    javax.swing.JList list;
    javax.swing.JScrollPane listScrollPane;
    javax.swing.JMenuItem mnuAbout;
    javax.swing.JMenuItem mnuAdd;
    javax.swing.JMenuItem mnuAddFolder;
    javax.swing.JMenuItem mnuAddFolderAndSub;
    javax.swing.JMenuItem mnuAddPlaylist;
    javax.swing.JMenuItem mnuClear;
    javax.swing.JMenuItem mnuExit;
    javax.swing.JMenuItem mnuOpen;
    javax.swing.JMenuItem mnuSave;
    javax.swing.JMenuItem mnuSaveAs;
    javax.swing.JMenuItem mnuSaveAsRelative;
    javax.swing.JMenuItem mnuToolsRemote;
    javax.swing.JLabel nowPlayingLabel;
    javax.swing.JLabel playbackStatusLabel;
    javax.swing.JButton pmbDelete;
    javax.swing.JButton pmbMoveDown;
    javax.swing.JButton pmbMoveUp;
    javax.swing.JButton pmbSaveOrdering;
    javax.swing.JProgressBar seekBar;
    javax.swing.JLabel serverStatusLabel;
    javax.swing.JLabel trackCountLabel;
    javax.swing.JSlider volumeSlider;
    // End of variables declaration//GEN-END:variables

    // special customizations
    @SuppressWarnings("unchecked")
	private void initComponentsMore() {

		// remove text from album art area
		this.albumArtLabel.setText(null);
		
		// set window title text
		this.setTitle(R.getString("mainWindow.title"));

		// set the SeekBar's UI
		this.seekBar.setUI(new SeekBarSimpleUI());
		this.seekBar.setMaximum(this.seekBar.getWidth());
		this.seekBar.setValue(0);

		// set the list's cell renderer to our custom one
		this.list.setCellRenderer(new TrackListCellRenderer());
		// set the list's model to represent the playlist
		this.list.setModel(new AbstractListModel<Track>() {
            @Override
            public int getSize() {
                // get the number of tracks in the playlist
                return playlist.size();
            }

            @Override
            public Track getElementAt(int index) {
                // get a specific track
                return playlist.getTrack(index);
            }
        });

		// add mouse event to the list
		this.list.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                // if it's a double click
                if (evt.getClickCount() == 2) {
                    if (list.isSelectionEmpty()) {
                        return; // if there's nothing selected
                    }

                    out(2, "ListItem DoubleClicked: "
                            + list.getSelectedValue().toString());

                    // set the current playing track to the selected one and play
                    currentIndex = list.getSelectedIndex();
                    playlistPlayer.play();
                }
            }
        });
		// add key event to the list
		this.list.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent evt) {
                // check which keys are pressed and call appropiate method
                if (evt.getKeyCode() == KeyEvent.VK_DOWN && evt.isAltDown()) {
                    out("List: Key Pressed: Alt-Down");
                    playlistManage_moveDown();
                } else if (evt.getKeyCode() == KeyEvent.VK_UP && evt.isAltDown()) {
                    out("List: Key Pressed: Alt-Up");
                    playlistManage_moveUp();
                } else if (evt.getKeyCode() == KeyEvent.VK_DELETE) {
                    out("List: Key Pressed: Delete");
                    playlistManage_delete();
                }
            }
        });
	}

    // action listeners for buttons etc.
    @SuppressWarnings("unchecked")
	private void addActionListeners() {
		this.albumArtLabel.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent evt) {
				albumArtLabel_clicked();
			}
		});

        this.seekBar.addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseDragged(MouseEvent evt) {
                seekBar_mouseDragged(evt);
            }
        });
        this.seekBar.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent evt) {
                seekBar_mousePressed(evt);
            }

            public void mouseReleased(MouseEvent evt) {
                seekBar_mouseReleased();
            }
        });


        this.cbPrev.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                playerControl_prev();
            }
        });
        this.cbPlayPause.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                playerControl_playPause();
            }
        });
        this.cbNext.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                playerControl_next();
            }
        });
        this.ctbRepeat.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                playerControl_toggleRepeat();
            }
        });
        this.ctbShuffle.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                playerControl_toggleShuffle();
            }
        });


        this.ctbMute.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                volControl_toggleMute();
            }
        });
        this.volumeSlider.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent evt) {
                volControl_volChange();
            }
        });

        this.pmbSaveOrdering.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                playlistManage_saveOrdering();
            }
        });
        this.pmbDelete.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                playlistManage_delete();
            }
        });
        this.pmbMoveUp.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                playlistManage_moveUp();
            }
        });
        this.pmbMoveDown.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                playlistManage_moveDown();
            }
        });
	}

    // action listeners for menu items
    @SuppressWarnings("unchecked")
    private void addMenuActionListeners() {
        this.mnuAdd.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                PlaylistIO.addFromMP3Multiple(playlist);
                refresh();
            }
        });

        this.mnuAddFolder.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                PlaylistIO.addFromFolderSingle(playlist);
                refresh();
            }
        });
		
		this.mnuAddFolderAndSub.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                PlaylistIO.addFromFolderRecursive(playlist);
                refresh();
            }
        });

        this.mnuAddPlaylist.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                PlaylistIO.addFromPlaylistMultiple(playlist);
                refresh();
            }
        });

        this.mnuOpen.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                PlaylistIO.openPlaylist(playlist);
                refresh();
            }
        });

        this.mnuSave.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // TODO: add save functionality
            }
        });

        this.mnuSaveAs.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                PlaylistIO.savePlaylistAs(playlist);
            }
        });

		this.mnuSaveAsRelative.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                PlaylistIO.savePlaylistAsRelative(playlist);
            }
        });
		
        this.mnuClear.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                playlist.clear();	// clear the list
                refresh();
            }
        });

        this.mnuExit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        this.mnuToolsRemote.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                callRemoteConfigWindow();
            }
        });

        this.mnuAbout.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // TODO: add About window
                callAboutWindow();
            }
        });
    }

    private void setFonts() {
        String font_ui = R.getString("font.ui");
        String font_symbol = R.getString("font.symbol");
        String font_list = R.getString("font.list");

		this.setFont(new Font(font_ui, 0, 12));
		
        this.playbackStatusLabel.setFont(new Font(font_ui, 0, 11));
        this.trackCountLabel.setFont(new Font(font_ui, 0, 11));
        this.serverStatusLabel.setFont(new Font(font_ui, 0, 11));

        this.cbPlayPause.setFont(new Font(font_symbol, 0, 24));
        this.cbNext.setFont(new Font(font_symbol, 0, 24));
        this.cbPrev.setFont(new Font(font_symbol, 0, 24));

        this.ctbRepeat.setFont(new Font(font_ui, 0, 10));
        this.ctbShuffle.setFont(new Font(font_ui, 0, 10));

        this.ctbMute.setFont(new Font(font_ui, 0, 10));

        this.nowPlayingLabel.setFont(new Font(font_list, 0, 11));
        this.infoTitleLabel.setFont(new Font(font_list, 0, 12));
        this.infoArtistLabel.setFont(new Font(font_list, 0, 11));
        this.infoAlbumLabel.setFont(new Font(font_list, 0, 11));
        this.infoMP3Technical.setFont(new Font(font_list, 0, 11));

        this.pmbSaveOrdering.setFont(new Font(font_ui, 0, 10));

        this.pmbDelete.setFont(new Font(font_symbol, 0, 14));
        this.pmbMoveUp.setFont(new Font(font_symbol, 0, 14));
        this.pmbMoveDown.setFont(new Font(font_symbol, 0, 14));

    }

    // </editor-fold>

	private void refresh() {
		this.list.updateUI();
		this.trackCountLabel.setText(String.format(("Tracks: %d"), this.playlist.size()));
	}

	public void loadDefaultPlaylist() {
		this.playlist = new Playlist();
		try {
			// load the default playlist specified in the resource bundle
			this.playlist.load(R.getString("filepath.defaultPlaylist"));
		} catch (FileNotFoundException e) {
			err("@MainWindow : default playlist not found.");
		}
        refresh();
	}


    // <editor-fold desc="Events: AlbumArtLabel and SeekBar">

	private void albumArtLabel_clicked() {
        if(this.albumArtLabel.getIcon() != null) {
            this.list.setSelectedIndex(this.currentIndex);
        }
    }

    private void seekBar_mouseDragged(MouseEvent evt) {
        // stop the player from updating the progressbar
        this.playlistPlayer.setPlayerProgressBar(null);
        // set the progressbar's value according to mouse position
        this.seekBar.setValue((int) Math.round((double) evt.getX()
                / this.seekBar.getWidth() * this.seekBar.getMaximum()));
        this.seekBar.validate();
    }
    private void seekBar_mousePressed(MouseEvent evt) {
        seekBar_mouseDragged(evt);
    }
    private void seekBar_mouseReleased() {
        // call player.seek(percentage)
        this.player.seek(((double) this.seekBar.getValue()) / this.seekBar.getMaximum());
        // let the player update the progressbar again
        this.playlistPlayer.setPlayerProgressBar(this.seekBar);
    }

    // </editor-fold>
    // <editor-fold desc="Events: Playback Control Buttons">

    private void playerControl_playPause() {
        // if the playlist is empty, let user add mp3 files first
        if (this.playlist.size() == 0 && this.player == null) {
            PlaylistIO.addFromMP3Multiple(this.playlist);
            refresh();
        }

        // if we already have a Player object, toggle play/pause
        if (this.player != null) {
            this.player.pauseToggle();
        } else {
            // we don't have a Player object
            if (this.list.getSelectedValue() != null) {
                // we selected a track in the list, start playing that track
                this.playlistPlayer.play(this.list.getSelectedIndex());
            } else {
                // we didn't select anything, start playing the first track
                this.playlistPlayer.play(0);
            }
        }
    }
    private void playerControl_prev() {
        // stop the current track first, the request previous track
        if (this.player != null) {
            this.player.stop();
        }
        if (this.currentIndex > 0) {
            this.currentIndex--;
            this.playlistPlayer.play();
        } else {
            this.playlistPlayer.play();
        }
    }
    private void playerControl_next() {
        // stop the current track first, the request next track
        if (this.player != null) {
            this.player.stop();
        }
        this.playlistPlayer.requestNext();
    }

    private void playerControl_toggleRepeat() {
        // set the playlist's Repeat property
        this.playlist.setRepeating(this.ctbRepeat.isSelected());
    }

    private int indexBeforeShuffling;
    private Track trackBeforeShuffling;

    private void playerControl_toggleShuffle() {
        // set the playlist's Shuffle property
        this.playlist.setShuffling(this.ctbShuffle.isSelected());

        if (this.playlist.isShuffling()) {
            // if set to shuffle, shuffle now
            //// this.playlist.shuffle();
            this.indexBeforeShuffling = this.currentIndex;
            this.trackBeforeShuffling = this.playlist.getTrack(this.indexBeforeShuffling);
            this.playlist.shuffleEnhanced(this.currentIndex);
            this.currentIndex = 0;
            this.list.setSelectedIndex(0);
        } else if (this.player!=null) {
            if (this.player.getTrack().equals(this.trackBeforeShuffling) && this.currentIndex==0) {
                out("shuffling A");
                this.currentIndex = this.indexBeforeShuffling;
            } else {
                out("shuffling B");
                this.currentIndex = this.playlist.indexOf(this.player.getTrack());
            }
            this.list.setSelectedIndex(this.currentIndex);
        }

        refresh();
    }

    // </editor-fold>
    // <editor-fold desc="Events: Volume Controls">

    private void volControl_toggleMute() {
        // toggle volumeSlider's enabled state first
        this.volumeSlider.setEnabled(!this.ctbMute.isSelected());
        if (this.ctbMute.isSelected()) {
            // if mute, set the volume to a very low value
            if (this.player != null) {
                this.player.setGain(-200f);
                this.player.setInitialGain(0);
            }
        } else {
            // if un-mute, set the volume according to volumeSlider
            volControl_volChange();
        }
    }
    private void volControl_volChange() {
        // set the player's volume by percentage
        if (this.player != null) {
            this.player.setGain((double) this.volumeSlider.getValue() /
                    this.volumeSlider.getMaximum());
            this.player.setInitialGain((double) this.volumeSlider.getValue() /
                    this.volumeSlider.getMaximum());
        }
    }

    // </editor-fold>
    // <editor-fold desc="Events: Playlist Management Buttons">

    private void playlistManage_saveOrdering() {
        // save current track ordering
        this.playlist.saveOrdering();
    }
    private void playlistManage_moveUp() {
        if (this.list.isSelectionEmpty()) return;

        int i = this.list.getSelectedIndex();
        // move Track up the list by 1 position
        this.playlist.moveTrack(i, i - 1);
        this.list.setSelectedIndex(i - 1); // select the moved track
    }
    private void playlistManage_moveDown() {
        if (this.list.isSelectionEmpty()) return;

        int i = this.list.getSelectedIndex();
        // move Track down the list by 1 position
        this.playlist.moveTrack(i, i + 2);
        this.list.setSelectedIndex(i + 1); // select the moved track
    }
    private void playlistManage_delete() {
        int c = 0; // counter
        for(int i : this.list.getSelectedIndices()) {
            // when removing a Track with index = i
            // the following Tracks's index will shift by -1
            // so we add a counter to deal with that shift
            this.playlist.remove(i-c);
            c++; // add the value of counter
        }
        refresh();
        this.list.clearSelection();	// select nothing
    }

    // </editor-fold>

    // <editor-fold desc="Android Remote Related Codes">

	// create an RCW for later uses
	private final RemoteConfigWindow rcw = new RemoteConfigWindow();

    private void callRemoteConfigWindow() {
        if (this.rcw.isVisible()) {
            // if the window is already showing, bring it to front
            this.rcw.toFront();
        } else {
            // if not, show the window
            this.rcw.setLocationRelativeTo(this);
            this.rcw.setVisible(true);
        }
    }

	@Override	// as a CommandListener
	public void receiveCommand(String cmd) {
		// receive a text command
		// control the playback by simulating clicking the control buttons
		switch (cmd) {
			case "playpause":		// toggle play/pause
				playerControl_playPause();
				break;
			case "next":			// request next track
				playerControl_next();
				break;
			case "prev":			// request previous track
				playerControl_prev();
				break;
			case "stop":			// stop the playback
				if (this.player != null) {
					this.player.stop();
					this.player = null;
					this.seekBar.setValue(0);
					updateNowPlayingInfo(null);
				}
				break;
			case "toggle_shuffle":
				// invert the selection state and call the method
				this.ctbShuffle.setSelected(!this.ctbShuffle.isSelected());
				playerControl_toggleShuffle();
				break;
			case "toggle_repeat":
				// invert the selection state and call the method
				this.ctbRepeat.setSelected(!this.ctbRepeat.isSelected());
				playerControl_toggleRepeat();
				break;
			case "vol_up":		// increase volume
                if (this.ctbMute.isSelected()) return;
				this.volumeSlider.setValue(this.volumeSlider.getValue() + 5);
				volControl_volChange();
				break;
			case "vol_down":	// decrease volume
                if (this.ctbMute.isSelected()) return;
				this.volumeSlider.setValue(this.volumeSlider.getValue() - 5);
				volControl_volChange();
				break;
		}
	}

    // </editor-fold>

    private final AboutWindow aboutWindow = new AboutWindow();
    private void callAboutWindow() {
        this.aboutWindow.setLocationRelativeTo(null);
        this.aboutWindow.setVisible(true);
    }

    // <editor-fold desc="Playback Control and GUI Update Codes">

	// playlist and player objects
	private Playlist playlist;
	private ImprovedPlayerController player;
	private PlaylistPlayer playlistPlayer = new PlaylistPlayer();

	// index of the current playing track in playlist
	private int currentIndex = 0;

	@Override	// as a ServerStatusWatcher
	public void updateServerStatus(ServerStatus status) {
		// set the server status text on the bottom-right of the window
		this.serverStatusLabel.setText(R.getString("statusText." + status));
	}

	public void updateNowPlayingInfo(Track nowPlaying) {
		if (nowPlaying == null) {
			// if nothing is being played, clear the info texts
			this.albumArtLabel.setIcon(null);
			this.infoTitleLabel.setText("--");
			this.infoArtistLabel.setText("--");
			this.infoAlbumLabel.setText("--");
			this.infoMP3Technical.setText("<nothing is playing right now>");
			return;
		}

		// set album artwork
		this.albumArtLabel.setIcon(nowPlaying.getArtworkIcon(
				this.albumArtLabel.getWidth()));
		if (this.albumArtLabel.getIcon() == null) {
			// if the file doesn't contain album art, use the default one
			this.albumArtLabel.setIcon(new ImageIcon(getClass().getResource(
					"/com/kanoksilp/ui/resource/track280.png")));
		}
		// set other info
		this.infoTitleLabel.setText(nowPlaying.getTitle());
		this.infoArtistLabel.setText(nowPlaying.getArtist());
		this.infoAlbumLabel.setText(nowPlaying.getAlbumAndYearString());
		// set the mp3 technical details info
		this.infoMP3Technical.setText(String.format("%s - %dKbps - %.1fKHz - %s",
				nowPlaying.getEncoding(),
				nowPlaying.getBitRate(),
				nowPlaying.getSampleRate() / 1000.,
				nowPlaying.getChannels()));
	}

	// a class for dealing with playing songs from a playlist
	private class PlaylistPlayer implements TrackEndedListener {

		// create a notifier with *this* as a listener
		// when a track finished playing, the player's notifier will call *this*
		private TrackEndedNotifier trackEndedNotifier 
				= new TrackEndedNotifier(this);

		public void setPlayerProgressBar(JProgressBar progressBar) {
			// set the progressbar of the player
			// will be used to display playback progress
			player.setProgressBar(progressBar);
		}

		public void play() {
			// if there's something already playing, stop it first
			if (player != null) {
				player.stop();
			}

			// if the playlist is empty, do nothing
			if (playlist.size() == 0) {
				out("PlaylistPlayer.play() : the playlist is empty");
				return;
			}

			// create a Player object to play a track
			player = new ImprovedPlayerController(playlist.getTrack(currentIndex));

			// set the progressbar and label for displaying progress
			player.setProgressBar(seekBar);
			player.setProgressLabel(playbackStatusLabel);

			// set the notifier to the one created earlier
			player.setTrackEndedNotifier(this.trackEndedNotifier);

			// update the GUI to display current track's info
			updateNowPlayingInfo(playlist.getTrack(currentIndex));
			list.setSelectedIndex(currentIndex);

			// set the volume of the player
			if (ctbMute.isSelected()) {
				player.setInitialGain(0);
			} else {
				player.setInitialGain((double) 
						volumeSlider.getValue() / volumeSlider.getMaximum());
			}

			// start playing
			player.play();

		}

		public void play(int index) {
			currentIndex = index;
			this.play();
		}

		public void requestNext() {
			out(2, "[called] PlaylistPlayer.requestNext()");
			seekBar.setValue(seekBar.getMaximum());
			if (currentIndex < playlist.size() - 1) {
				// if we don't reach the end of playlist yet, play the next song
				currentIndex++;
				this.play();

			} else {
				// if we are at the last track of playlist
				out(2, "PlaylistPlayer.requestNext() : last track reached");

				if (playlist.isRepeating()) {
                    // if the playlist is set to repeat

					// set the index back to the first track
					currentIndex = 0;

					// also shuffle it first if needed
					if (playlist.isShuffling()) {
						playlist.shuffle();
					}

					// resume the playback
					this.play();
				} else {
					out(2, "PlaylistPlayer.requestNext() : finished playback");
				}
			}
		}

		@Override
		public void trackEnded() {
			// when finished playing a track, request next track in the list
			this.requestNext();
		}
	}

    // </editor-fold>

}
