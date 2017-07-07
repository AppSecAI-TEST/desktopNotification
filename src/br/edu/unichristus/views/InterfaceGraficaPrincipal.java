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
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

@SuppressWarnings("serial")
public class InterfaceGraficaPrincipal extends JFrame {

	private TrayIcon trayIcon;
	private SystemTray systemTray;

	private final Image IMAGE = Toolkit.getDefaultToolkit().getImage("resources/img/printer.png");

	public InterfaceGraficaPrincipal() throws AWTException {
		super("SystemTray test");

		try {

			System.out.println(UIManager.getSystemLookAndFeelClassName());
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e) {

			System.out.println("Erro 001 - getSystemLookAndFeelClassName ");
			e.printStackTrace();
		}

		if (SystemTray.isSupported()) {

			openWindow();

		} else {

			// TODO Melhorar esse tratamento de erro
			throw new AWTException("Não foi possível tratar esse evento");

		}

		minimizeWindowToTray();

		windowPropreties();
	}

	private void windowPropreties() {
		setIconImage(IMAGE);
		setVisible(true);
		setSize(300, 200);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

		addWindowListener(new java.awt.event.WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent windowEvent) {
//				if (JOptionPane.showConfirmDialog(frame, message)) {
//					
//				}
			}

		});

	}

	private void openWindow() {

		this.systemTray = SystemTray.getSystemTray();

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
		trayIcon = new TrayIcon(this.IMAGE, "Demo", popupMenu);
		trayIcon.setImageAutoSize(true);

	}

	private void minimizeWindowToTray() {
		addWindowStateListener(new WindowStateListener() {

			@Override
			public void windowStateChanged(WindowEvent windowEvent) {
				System.out.println(windowEvent.getNewState());

				if (windowEvent.getNewState() == ICONIFIED) {
					try {
						systemTray.add(trayIcon);
						System.out.println("foi pro tray");
						setVisible(false);
					} catch (AWTException e) {
						e.getMessage();
					}
				}

				if (windowEvent.getNewState() == 7) {
					try {
						System.out.println("on tray");
						systemTray.add(trayIcon);
						setVisible(false);
					} catch (AWTException e) {
						System.out.println("não consegue né? - Sant");
					}
				}
				if (windowEvent.getNewState() == MAXIMIZED_BOTH) {
					System.out.println("saiu do tray");
					systemTray.remove(trayIcon);
				}

				if (windowEvent.getNewState() == NORMAL) {
					System.out.println("ray sai");
					systemTray.remove(trayIcon);
					setVisible(true);
				}
			}
		});
	}

	// TODO: SEE
	// https://docs.oracle.com/javase/tutorial/displayCode.html?code=https://docs.oracle.com/javase/tutorial/uiswing/examples/misc/TrayIconDemoProject/src/misc/TrayIconDemo.java
}
