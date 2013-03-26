package gui;


import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dialog.ModalExclusionType;
import java.awt.Dialog.ModalityType;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTabbedPane;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.WindowConstants;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;

import misc.StopWatch;
import misc.SubmitServer;
import misc.Variables;

import org.powerbot.core.script.job.Task;

import script.LonelehMining;
import script.mining.MiningLocation;
import script.mining.MiningVars;
import script.mining.Ore;
import script.mining.OreType;

public class GUI extends JFrame
{
	private static final long serialVersionUID = -5490165515721657800L;
	
	public static boolean isFinished = false;
	
	private Thread loadGuiThread, finishGuiThread;
	
	private JPanel contentPane;
	private final JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
	private final JPanel step1Panel = new JPanel();
	private final JPanel step2Panel = new JPanel();
	
	//step1Panel
	private final JLabel lblTitle = new JLabel("<html><b>Loneleh Mining</b> - brought to you by xPropel</html>");;
	private final JPanel actionsPanel = new JPanel();
	private final JLabel lblInstructions1 = new JLabel("What will you be doing?");
	private final JCheckBox chckbxMining = new JCheckBox("Mining");
	
	//step2Panel
	private final JPanel miningLocPanel = new JPanel();
	private final JLabel lblMiningLocation = new JLabel("Location:");
	private final JComboBox<String> comboBoxMiningLoc = new JComboBox<String>();
	private final JPanel miningStrategyPanel = new JPanel();
	private final JLabel lblStrategy = new JLabel("Strategy:");
	private final JComboBox<String> comboBoxMiningStrategy = new JComboBox<String>();
	private final JPanel miningBottomPanel = new JPanel();
	private final JCheckBox chckbxTinOre = new JCheckBox("Tin ore");
	private final JCheckBox chckbxCopperOre = new JCheckBox("Copper ore");
	private final JCheckBox chckbxIronOre = new JCheckBox("Iron ore");
	private final JCheckBox chckbxSilverOre = new JCheckBox("Silver ore");
	private final JCheckBox chckbxCoal = new JCheckBox("Coal");
	private final JCheckBox chckbxGoldOre = new JCheckBox("Gold ore");
	private final JCheckBox chckbxMithrilOre = new JCheckBox("Mithril ore");
	private final JCheckBox chckbxAdamantiteOre = new JCheckBox("Adamantite ore");
	private final JCheckBox chckbxRuniteOre = new JCheckBox("Runite ore");
	private final JPanel priorityPanel = new JPanel();
	private final PriorityList priorityList = new PriorityList();
	private final JPanel finishPanel = new JPanel();
	private final JButton btnStartNow = new JButton("Start now!");;
	private final JLabel lblThanks = new JLabel("<html>\r\n<center>Thanks again for choosing <b>Loneleh Mining</b><br>\r\nEnjoy botting~</center>\r\n</html>");
	
	
	//loading pop up
	private JDialog popupDialog;
	private JProgressBar progressBar;
	private JLabel lblPopupStatus;
	
	private void start()
	{
		finishGuiThread.start();
		loadingPopup("Finalizing...", "Please wait while I retrieve some rock data", false);
		
		while (!Variables.canStart)
		{
			Task.sleep(100);
		}
		
		this.setVisible(false);
		isFinished = true;
		Variables.totalTimer = new StopWatch(true);
		Variables.miningTimer = new StopWatch(true);
		
		Variables.totalTimer.start();
		Variables.miningTimer.start();
		
		LonelehMining.submitter = new SubmitServer("http://loneleh.comxa.com/lonelehmining");
	}
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args)
	{
		EventQueue.invokeLater(new Runnable()
		{
			@Override
			public void run()
			{
				try
				{
					GUI frame = new GUI();
					frame.setTitle("Loneleh Mining (test)");
					frame.setLocationRelativeTo(null);
					javax.swing.SwingUtilities.updateComponentTreeUI(frame);
					frame.setVisible(true);
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		});
	}
	
	public void loadingPopup(String title, String text, boolean start)
	{
		//System.out.println("loading pop up");
		popupDialog = new JDialog(this, title);
		popupDialog.addWindowListener(new WindowAdapter()
		{
			@Override
			public void windowClosing(WindowEvent e) 
			{
				if (JOptionPane.showOptionDialog(null, "u sure u wanna quit?", "Quit " + LonelehMining.getName(), JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, new String[]{"yes pls", "no wtf?", "just kidding.."}, "just kidding..") == 0)
				{
					e.getWindow().setVisible(false);
					popupDialog.setVisible(false);
					setVisible(false);
					misc.Functions.stopScript();
					dispose();
				}
			}
		});
		popupDialog.addComponentListener(new ComponentAdapter()
		{
			@Override
			public void componentHidden(ComponentEvent e)
			{
				LonelehMining.mainWindow.requestFocus();
				LonelehMining.mainWindow.toFront();
			}
		});
		popupDialog.setResizable(false);
		popupDialog.setModalityType(ModalityType.DOCUMENT_MODAL);
		popupDialog.setType(Type.NORMAL);
		popupDialog.setModalExclusionType(ModalExclusionType.TOOLKIT_EXCLUDE);
		popupDialog.setBounds(100, 100, 300, 120);
		popupDialog.getContentPane().setLayout(new BorderLayout());
		JPanel panel = new JPanel();
		popupDialog.getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		progressBar = new JProgressBar();
		progressBar.setIndeterminate(true);
		progressBar.setBounds(10, 11, 264, 50);
		panel.add(progressBar);
		lblPopupStatus = new JLabel(text);
		lblPopupStatus.setHorizontalAlignment(SwingConstants.CENTER);
		lblPopupStatus.setBounds(0, 72, 294, 14);
		panel.add(lblPopupStatus);
		
		popupDialog.setLocationRelativeTo(null);
		
		try
		{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		}
		catch (ClassNotFoundException e)
		{
			e.printStackTrace();
		}
		catch (InstantiationException e)
		{
			e.printStackTrace();
		}
		catch (IllegalAccessException e)
		{
			e.printStackTrace();
		}
		catch (UnsupportedLookAndFeelException e)
		{
			e.printStackTrace();
		}
		
		
		javax.swing.SwingUtilities.updateComponentTreeUI(popupDialog);
		
		if (start) loadGuiThread.start();
		popupDialog.setVisible(true);
	}
	
	/**
	 * Create the frame.
	 */
	public GUI()
	{
		loadGuiThread = new Thread(new LoadGUI());
		finishGuiThread = new Thread(new FinishGUI());
		
		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		addWindowListener(new WindowAdapter()
		{
			@Override
			public void windowClosing(WindowEvent e) 
			{
				if (JOptionPane.showOptionDialog(null, "u sure u wanna quit?", "Quit " + LonelehMining.getName() + "?", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, new String[]{"yes pls", "no wtf?", "just kidding.."}, "just kidding..") == 0)
				{
					e.getWindow().setVisible(false);
					setVisible(false);
					misc.Functions.stopScript();
					dispose();
				}
			}
		});
		
		setResizable(false);
		setTitle(LonelehMining.getName());
		try
		{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		}
		catch (ClassNotFoundException e)
		{
			e.printStackTrace();
		}
		catch (InstantiationException e)
		{
			e.printStackTrace();
		}
		catch (IllegalAccessException e)
		{
			e.printStackTrace();
		}
		catch (UnsupportedLookAndFeelException e)
		{
			e.printStackTrace();
		}
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		contentPane.add(tabbedPane, BorderLayout.CENTER);
		tabbedPane.addTab("Step 1", null, step1Panel, null);
		step1Panel.setLayout(null);
		
		lblTitle.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblTitle.setBounds(64, 59, 300, 23);
		step1Panel.add(lblTitle);
		actionsPanel.setBounds(49, 141, 330, 33);
		
		step1Panel.add(actionsPanel);
		lblInstructions1.setToolTipText("Select any metal related tasks you want to do");
		
		actionsPanel.add(lblInstructions1);
		chckbxMining.setSelected(true);
		chckbxMining.setToolTipText("<html><b>Must</b> have a pickaxe on you (inventory, equipped or default)</html>");
		actionsPanel.add(chckbxMining);
		
		tabbedPane.addTab("Step 2: (mining)", null, step2Panel, null);
		step2Panel.setLayout(null);
		miningLocPanel.setBounds(61, 16, 306, 32);
		
		step2Panel.add(miningLocPanel);
		miningLocPanel.add(lblMiningLocation);
		comboBoxMiningLoc.setToolTipText("Mine location");
		miningLocPanel.add(comboBoxMiningLoc);
		
		comboBoxMiningLoc.setModel(new DefaultComboBoxModel<String>(MiningLocation.valuesToString()));
		miningStrategyPanel.setBounds(61, 64, 306, 32);
		
		step2Panel.add(miningStrategyPanel);
		miningStrategyPanel.add(lblStrategy);
		miningStrategyPanel.add(comboBoxMiningStrategy);
		comboBoxMiningStrategy.setToolTipText("<html>\r\n<b>Power Mining:</b> EXP; mine and drop ores<br>\r\n<b>Banking:</b> Profit; banks all your ores/gems mined\r\n</html>");
		comboBoxMiningStrategy.setModel(new DefaultComboBoxModel<String>(MiningVars.miningStrategies));
		
		miningBottomPanel.setBounds(10, 112, 306, 105);
		step2Panel.add(miningBottomPanel);
		chckbxTinOre.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				String name = "Tin ore";
				@SuppressWarnings("unchecked")
				DefaultListModel<String> dlm = (DefaultListModel<String>)priorityList.getModel();
				if (((JCheckBox)e.getSource()).isSelected())
				{
					dlm.addElement(name);
				}
				else if (dlm.contains(name))
				{
					dlm.removeElement(name);
				}
			}
		});
		miningBottomPanel.add(chckbxTinOre);
		
		chckbxCopperOre.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				String name = "Copper ore";
				@SuppressWarnings("unchecked")
				DefaultListModel<String> dlm = (DefaultListModel<String>)priorityList.getModel();
				if (((JCheckBox)e.getSource()).isSelected())
				{
					dlm.addElement(name);
				}
				else if (dlm.contains(name))
				{
					dlm.removeElement(name);
				}
			}
		});
		
		miningBottomPanel.add(chckbxCopperOre);
		
		chckbxIronOre.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				String name = "Iron ore";
				@SuppressWarnings("unchecked")
				DefaultListModel<String> dlm = (DefaultListModel<String>)priorityList.getModel();
				if (((JCheckBox)e.getSource()).isSelected())
				{
					dlm.addElement(name);
				}
				else if (dlm.contains(name))
				{
					dlm.removeElement(name);
				}
			}
		});
		miningBottomPanel.add(chckbxIronOre);
		
		chckbxSilverOre.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				String name = "Silver ore";
				@SuppressWarnings("unchecked")
				DefaultListModel<String> dlm = (DefaultListModel<String>)priorityList.getModel();
				if (((JCheckBox)e.getSource()).isSelected())
				{
					dlm.addElement(name);
				}
				else if (dlm.contains(name))
				{
					dlm.removeElement(name);
				}
			}
		});
		miningBottomPanel.add(chckbxSilverOre);
		
		chckbxCoal.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				String name = "Coal";
				@SuppressWarnings("unchecked")
				DefaultListModel<String> dlm = (DefaultListModel<String>)priorityList.getModel();
				if (((JCheckBox)e.getSource()).isSelected())
				{
					dlm.addElement(name);
				}
				else if (dlm.contains(name))
				{
					dlm.removeElement(name);
				}
			}
		});
		miningBottomPanel.add(chckbxCoal);
		
		chckbxGoldOre.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				String name = "Gold ore";
				@SuppressWarnings("unchecked")
				DefaultListModel<String> dlm = (DefaultListModel<String>)priorityList.getModel();
				if (((JCheckBox)e.getSource()).isSelected())
				{
					dlm.addElement(name);
				}
				else if (dlm.contains(name))
				{
					dlm.removeElement(name);
				}
			}
		});
		miningBottomPanel.add(chckbxGoldOre);
		
		chckbxMithrilOre.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				String name = "Mithril ore";
				@SuppressWarnings("unchecked")
				DefaultListModel<String> dlm = (DefaultListModel<String>)priorityList.getModel();
				if (((JCheckBox)e.getSource()).isSelected())
				{
					dlm.addElement(name);
				}
				else if (dlm.contains(name))
				{
					dlm.removeElement(name);
				}
			}
		});
		miningBottomPanel.add(chckbxMithrilOre);
		
		chckbxAdamantiteOre.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				String name = "Adamantite ore";
				@SuppressWarnings("unchecked")
				DefaultListModel<String> dlm = (DefaultListModel<String>)priorityList.getModel();
				if (((JCheckBox)e.getSource()).isSelected())
				{
					dlm.addElement(name);
				}
				else if (dlm.contains(name))
				{
					dlm.removeElement(name);
				}
			}
		});
		miningBottomPanel.add(chckbxAdamantiteOre);
		
		chckbxRuniteOre.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				String name = "Runite ore";
				@SuppressWarnings("unchecked")
				DefaultListModel<String> dlm = (DefaultListModel<String>)priorityList.getModel();
				if (((JCheckBox)e.getSource()).isSelected())
				{
					dlm.addElement(name);
				}
				else if (dlm.contains(name))
				{
					dlm.removeElement(name);
				}
			}
		});
		miningBottomPanel.add(chckbxRuniteOre);
		
		priorityPanel.setBounds(326, 112, 93, 105);
		step2Panel.add(priorityPanel);
		priorityPanel.setLayout(new BorderLayout(0, 0));
		priorityList.setVisibleRowCount(6);
		priorityList.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		
		priorityList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		priorityPanel.add(priorityList);
		
		tabbedPane.addTab("Finish", null, finishPanel, null);
		finishPanel.setLayout(null);
		
		btnStartNow.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				start();
			}
		});
		btnStartNow.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnStartNow.setBounds(119, 81, 190, 70);
		finishPanel.add(btnStartNow);
		
		lblThanks.setHorizontalAlignment(SwingConstants.CENTER);
		lblThanks.setBounds(0, 182, 429, 40);
		finishPanel.add(lblThanks);
	}
	
	@SuppressWarnings("unused")
	private void setAllOresChckbx()
	{
		for (int i = 0 ; i < miningBottomPanel.getComponents().length ; i++)
		{
			((JCheckBox)miningBottomPanel.getComponent(i)).setText(OreType.values()[i].getName());
		}
	}
	
	@SuppressWarnings("unused")
	private ArrayList<JCheckBox> getAllRocksChckbx()
	{
		ArrayList<JCheckBox> list = new ArrayList<JCheckBox>();
		for (Component chb : miningBottomPanel.getComponents())
			list.add((JCheckBox)chb);
		return list;
	}
	
	private void getChosenSettings()
	{
		//step1
		misc.Variables.choseMining = chckbxMining.isSelected();
		
		//mining
		script.mining.MiningVars.miningLocation = comboBoxMiningLoc.getSelectedItem().toString();
		script.mining.MiningVars.miningStrategy = comboBoxMiningStrategy.getSelectedItem().toString();
		
		for (int i = 0 ; i < priorityList.getModel().getSize() ; i++)
		{
			String name = priorityList.getModel().getElementAt(i).toString();
			lblPopupStatus.setText("Configuring " + name);
			MiningVars.oresToMine.add(new Ore((name)));
		}
		
		
		Task.sleep(10000);
		
		for (Ore o : MiningVars.oresToMine)
			for (int id : o.getRockGroupIds())
				if (!MiningVars.oresToMineIds.contains(id))
					MiningVars.oresToMineIds.add(id);
	}
	
	private abstract class TaskThread implements Runnable
	{
		protected volatile boolean running = true;
		
		public void terminate()
		{
			running = false;
		}
		
		@Override
		public abstract void run();
	}
	
	private class LoadGUI extends TaskThread implements Runnable
	{
		@Override
		public void run()
		{
			while (running)
			{
				//setAllOresChckbx(); //delay is annoying...
				//setAllBarsChckbx();
				popupDialog.setVisible(false);
				
				terminate();
			}
		}
	}
	
	private class FinishGUI extends TaskThread implements Runnable
	{
		@Override
		public void run()
		{
			while (running)
			{
				getChosenSettings();
				
				popupDialog.setVisible(false);
				
				Variables.canStart = true;
				
				terminate();
			}
		}
	}
}
