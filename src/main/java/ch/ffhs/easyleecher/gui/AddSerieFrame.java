package ch.ffhs.easyleecher.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.ButtonModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import ch.ffhs.easyleecher.gui.worker.AddSerieFrameTvDbWorker;

/**
 * Dieses Frame ermöglicht das hinzufügen einer neuen Serie
 * 
 * @author thierry baumann
 */
@SuppressWarnings("serial")
public class AddSerieFrame extends JFrame {
	private JPanel searchResultWrapper;
	private JTextField searchField;

	private JButton searchButton;
	private JButton addButton;
	private JButton closeButton;
	private JPanel mainWrapper;

	private ButtonGroup buttonGroup;

	public AddSerieFrame() {
		super("Add Serie");
		buttonGroup = new ButtonGroup();

		setContentPane(createContentPane());
		addListeners();
		setPreferredSize(new Dimension(500, 600));
		setResizable(false);
	}

	private JPanel createContentPane() {
		mainWrapper = new JPanel(new BorderLayout());
		// JPanel formWrapper = new JPanel(new BorderLayout(15, 0));
		JPanel formWrapper = new JPanel(new GridLayout(0, 2));
		JPanel mainPanel = new JPanel(new BorderLayout());
		searchResultWrapper = new JPanel();
		searchResultWrapper.setLayout(new BoxLayout(searchResultWrapper,
				BoxLayout.PAGE_AXIS));

		// Folder Name
		formWrapper.add(new JLabel("Search"));
		searchField = new JTextField(20);
		formWrapper.add(searchField);

		// actionpanel for buttons
		JPanel actionPanel = new JPanel(new FlowLayout(0));
		// button save
		searchButton = new JButton("Search");
		actionPanel.add(searchButton);

		addButton = new JButton("Add");
		addButton.setVisible(false);
		actionPanel.add(addButton);

		closeButton = new JButton("Close");
		actionPanel.add(closeButton);
		mainPanel.add(actionPanel, BorderLayout.NORTH);
		mainPanel.add(searchResultWrapper, BorderLayout.CENTER);
		mainWrapper.add(formWrapper, BorderLayout.NORTH);
		mainWrapper.add(mainPanel, BorderLayout.CENTER);

		return mainWrapper;
	}

	private void addListeners() {
		searchButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				AddSerieFrameTvDbWorker addSerieFrameTvDbWorker = new AddSerieFrameTvDbWorker(
						searchResultWrapper, searchField, addButton,
						buttonGroup);
				addSerieFrameTvDbWorker.execute();
			}
		});

		addButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ButtonModel selectedSerie = buttonGroup.getSelection();

				if (selectedSerie.isSelected()) {
					AddSerieFrameTvDbWorker addSerieFrameTvDbWorker = new AddSerieFrameTvDbWorker(
							selectedSerie.getActionCommand());
					addSerieFrameTvDbWorker.execute();

					setVisible(false);
					dispose();
				} else {
					JOptionPane.showMessageDialog(new JFrame(),
							"Choose serie first", "Error",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		});

		closeButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				setVisible(false);
				dispose();
			}
		});
	}
}