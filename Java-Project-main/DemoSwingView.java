

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;

import animation.BBox;
import animation.Scene;
import animation.SceneObject;
import animation.View;

public class DemoSwingView extends View {

	public DemoSwingView() {
		sceneObjects = new ArrayList<SceneObject>();
		currSceneObj = 0;
	}

	@SuppressWarnings("serial")
	void setUpGUI() {

		JFrame frame = new JFrame("Animation Demo");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout());

		topPanel = new JPanel();

		topPanel.setPreferredSize(new Dimension(Width + 200, Height + 10));
		topPanel.setLayout(new FlowLayout());
		Border bborder = BorderFactory.createLineBorder(Color.red);
		frame.add(topPanel);

		imagePanel = new JPanel() {
			public void paintComponent(Graphics g) {
				super.paintComponent(g);
				System.out.println("Repainting");

				for (SceneObject s : sceneObjects) {
					System.out.println("Drawing obj " + s);
					g.setColor(Color.GREEN);
					BBox bb = s.getBBox();
					System.out.println("BB " + bb);
					g.drawRect(bb.getMinPt().getX(), Height - bb.getMaxPt().getY(),
							bb.getMaxPt().getX() - bb.getMinPt().getX(), bb.getMaxPt().getY() - bb.getMinPt().getY());
					g.drawString(s.getObjName(), bb.getMaxPt().getX(), Height - bb.getMinPt().getY());
				}
			}
		};
		imagePanel.setPreferredSize(new Dimension(Width, Height));
		imagePanel.setBorder(bborder);
		topPanel.add(imagePanel);

		JPanel sidePanel = new JPanel();
		sidePanel.setLayout(new BoxLayout(sidePanel, BoxLayout.PAGE_AXIS));
		sidePanel.setBorder(bborder);
		topPanel.add(sidePanel);

		JButton obsButton = new JButton("Set Obstacles");
		obsButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mode = SelectMode.Obstacle;
				currSceneObj = 0;
			}

		});
		sidePanel.add(obsButton);

		JButton actorButton = new JButton("Set Actors");
		actorButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mode = SelectMode.ActorStart;
				currSceneObj = 0;
			}

		});
		sidePanel.add(actorButton);

		JButton destButton = new JButton("Set Destinations");
		destButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mode = SelectMode.ActorDest;
				currSceneObj = 0;
			}

		});
		sidePanel.add(destButton);

		JButton startButton = new JButton("Start");
		startButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("Start");
				new Thread() {
					public void run() {
						Scene.getScene().animate();
					}
				}.start();
			}

		});
		sidePanel.add(startButton);

		JButton exitButton = new JButton("Exit");
		exitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("Exit");
				System.exit(0);
			}

		});
		sidePanel.add(exitButton);

		MouseListener mlistener = new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				int mx = e.getX();
				int my = Width - e.getY();
				Scene currScene = Scene.getScene();
				switch (mode) {
				case Obstacle:
					currScene.getObstacles().get(currSceneObj).setPosition(mx, my);
					currScene.render();
					break;
				case ActorStart:
					currScene.getActors().get(currSceneObj).setPosition(mx, my);
					currScene.render();
					break;
				case ActorDest:
					currScene.getActors().get(currSceneObj).setDestPosition(mx, my);
					currScene.render();
					break;
				case None:
					break;
				default:
					break;
				}
				currSceneObj++;
			}
		};

		imagePanel.addMouseListener(mlistener);

		// Display the window.
		frame.pack();
		frame.setVisible(true);

	}

	public void init() {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				setUpGUI();
			}
		});
	}

	@Override
	public void clear() {
		sceneObjects.clear();
		imagePanel.removeAll();
	}

	@Override
	public void render(SceneObject s) {
		sceneObjects.add(s);
	}

	public void updateView() {
		if (imagePanel != null) {
			System.out.println("Updating view");
			imagePanel.repaint();
		}
	}

	private JPanel topPanel, imagePanel;
	private final int Height = 700;
	private final int Width = 700;

	private ArrayList<SceneObject> sceneObjects;

	enum SelectMode {
		Obstacle, ActorStart, ActorDest, None
	};

	private SelectMode mode = SelectMode.None;
	private int currSceneObj;

}
