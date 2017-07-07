package br.edu.unichristus.views;

import java.awt.AWTException;
import java.awt.Image;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowStateListener;

import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

@SuppressWarnings("serial")
public class InterfaceGraficaPrincipal extends JFrame {

	private TrayIcon trayIcon;
	private SystemTray systemTray;

	public InterfaceGraficaPrincipal() {
		super("SystemTray test");

		System.out.println("Settings look and feel");
		try {

			System.out.println(UIManager.getSystemLookAndFeelClassName());
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e) {

			System.out.println("Erro 001 - getSystemLookAndFeelClassName ");
			e.printStackTrace();
		}

		if (SystemTray.isSupported()) {

			System.out.println("isSupported");
			this.systemTray = SystemTray.getSystemTray();

			Image image = Toolkit.getDefaultToolkit().getImage("resources/printer.png");

			ActionListener exitActionListener = new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					System.out.println("Saindo");
					System.exit(0);
				}
			};

			PopupMenu popupMenu = new PopupMenu();
			MenuItem menuItem = new MenuItem("Sair");

			menuItem.addActionListener(exitActionListener);
			popupMenu.add(menuItem);

			menuItem = new MenuItem("Abrir");
			menuItem.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent arg0) {
					setVisible(true);
					setExtendedState(JFrame.NORMAL);
				}
			});

			popupMenu.add(menuItem);
			trayIcon = new TrayIcon(image, "Demo", popupMenu);
			trayIcon.setImageAutoSize(true);

		} else {

			System.out.println("Sysem tray false");

		}

		addWindowStateListener(new WindowStateListener() {

			@Override
			public void windowStateChanged(WindowEvent windowEvent) {

				if (windowEvent.getNewState() == ICONIFIED) {
					try {
						systemTray.add(trayIcon);
						setVisible(false);
					} catch (AWTException e) {
						e.getMessage();
					}
				}

				if (windowEvent.getNewState() == 7) {
					try {
						systemTray.add(trayIcon);
						setVisible(false);
						System.out.println("on tray");
					} catch (AWTException e) {
						System.out.println("não consegue né? - Sant");
					}
				}
				if (windowEvent.getNewState() == MAXIMIZED_BOTH) {
					systemTray.remove(trayIcon);
					System.out.println("saiu do tray");
				}

				if (windowEvent.getNewState() == NORMAL) {
					systemTray.remove(trayIcon);
					setVisible(true);
					System.out.println("ray sai");
				}
			}
		});

		setIconImage(Toolkit.getDefaultToolkit().getImage("resources/printer.png"));
		windowPropreties();
	}

	private void windowPropreties() {
		setVisible(true);
		setSize(300, 200);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}

}
