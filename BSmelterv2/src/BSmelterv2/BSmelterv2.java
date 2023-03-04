package BSmelterv2;

import simple.api.coords.WorldPoint;
import simple.api.script.Category;
import simple.api.script.Script;
import simple.api.script.ScriptManifest;
import simple.api.script.interfaces.SimplePaintable;
import simple.api.wrappers.SimpleGameObject;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;



@ScriptManifest(author = "Beorhtic", name = "BSmelter", category = Category.SMITHING, version = "1.0",
description = "Smelts Bars.", discord = "", servers = { "Ikov"})

public class BSmelterv2 extends Script implements SimplePaintable {
	
	private JPanel contentPane;
	gui frame = new gui();
	
	 public static final WorldPoint[] PATH_TO_BANK_AREA = {
	            new WorldPoint(3274,3186,0),
	            new WorldPoint(3279,3185,0),
	            new WorldPoint(3281,3184,0),
	            new WorldPoint(3280,3182,0),
	            new WorldPoint(3279,3180,0),
	            new WorldPoint(3277,3178,0),
	            new WorldPoint(3276,3176,0),
	            new WorldPoint(3275,3173,0),
	            new WorldPoint(3274,3171,0),
	            new WorldPoint(3274,3168,0),
	            new WorldPoint(3273,3167,0),
	            new WorldPoint(3271,3166,0),
	            new WorldPoint(3269,3167,0)
	    };
	 
	 public int ore1 = 0;	 
	 public int ore2 = 0;
	 public int amount1 = 0;
	 public int amount2 = 0;
	 public int cmd3 = 0;
	 public boolean start = false;
	 
	    @Override
	    public boolean onExecute() {
	    	
	    	frame.setVisible(true);
	        return true;
	    }

	    @Override
	    public void onProcess() {
	    	
	    	if (start) {

	            if (ctx.inventory.populate().filter(ore1,ore2).isEmpty()) {
	            	SimpleGameObject bankBooth = (SimpleGameObject) ctx.objects.populate().filter(10355).nearest().next();
	            	if(bankBooth != null && ctx.players.getLocal().getLocation().distanceTo(bankBooth) >= 3) {
	            		System.out.println("Walking to bank booth.");
	            		ctx.pathing.walkPath(PATH_TO_BANK_AREA);
	            		 ctx.sleep(1000);
	            		 return;
	            	}
	            	
	            	if (!ctx.bank.bankOpen()) {
	            		System.out.println("Opening bank.");
	            		 ctx.bank.openBank();
	            		 ctx.sleep(350);
	            		 if (ctx.bank.populate().filter(ore1).isEmpty()) {
	            			 ctx.stopScript();
	            		 }
	            		 if (ctx.bank.populate().filter(ore2).isEmpty()) {
	            			 System.out.println("We are out of ore, stopping script!");
	            			 ctx.stopScript();
	            		 }
	            	} else {
	            		if (!ctx.inventory.populate().filterContains("bar").isEmpty()) {
	            			ctx.bank.depositInventory();
	            			return;
	            		}
	            		
	            		if(!ctx.bank.populate().filter(ore1).isEmpty()) {
	            			System.out.println("Withdrawing " + ctx.definitions.getItemDefinition(ore1).getName() + ".");
	            			 ctx.bank.withdraw(ore1, amount1);
	            			 ctx.sleep(750);
	            		}
	            		
	            		if(!ctx.bank.populate().filter(ore2).isEmpty()) {
	            			System.out.println("Withdrawing " + ctx.definitions.getItemDefinition(ore2).getName()+ ".");
	            			 ctx.bank.withdraw(ore2, amount2);
	            			 ctx.sleep(750);
	            		}
	            		   ctx.bank.closeBank();
	            	}
	            }
	                 else {
	                	 
	                	 SimpleGameObject furnace = (SimpleGameObject) ctx.objects.populate().filter(24009).nextNearest();
	                	 
	                		                    // We are in the area... so lets grab the furnace
	                	 
	                    if (ctx.players.getLocal().getAnimation() == -1) {
	                        if (furnace != null && ctx.players.getLocal().getLocation().distanceTo(furnace) <= 3) {
	                            ctx.objects.next().interact(900);
	                            ctx.sleep(750);
	                            ctx.menuActions.sendAction(315, 0, 0, cmd3);
	                            ctx.sleep(750);
	                        } else {
	                        	ctx.pathing.step(3274, 3186 );
	                            System.out.println("Pathing to the furnace.");
	                        }
	                    } else {
	                        ctx.sleep(500);
	                        if (ctx.players.getLocal().getAnimation() == -1) {
	                            System.out.println("We need to interact again.");
	                        }
	                    }
	                 }
	    	}
	        
	    	}

		@Override
	    public void onTerminate() {

	    }

	    @Override
	    public void onPaint(final Graphics2D g) {
	    	// paint

	    }
	    
	    @SuppressWarnings("serial")
		class gui extends JFrame {
	    	
	    	public gui() {
	    		setResizable(false);
	    		setBackground(new Color(255, 255, 255));
	    		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    		setBounds(100, 100, 215, 111);
	    		contentPane = new JPanel();
	    		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

	    		setContentPane(contentPane);
	    		contentPane.setLayout(null);
	    		
	    		JLabel lblNewLabel = new JLabel("Bar: ");
	    		lblNewLabel.setBounds(10, 15, 46, 14);
	    		contentPane.add(lblNewLabel);
	    		
	    		JComboBox comboBox = new JComboBox();
	    		comboBox.setModel(new DefaultComboBoxModel(new String[] {"Bronze", "Iron", "Steel", "Gold", "Mithril", "Adamant", "Rune"}));
	    		comboBox.setBounds(66, 11, 113, 22);
	    		contentPane.add(comboBox);
	    		
	    		JButton btnNewButton = new JButton("Start");
	    		btnNewButton.addActionListener(new ActionListener() {
	    			public void actionPerformed(ActionEvent e) {
	    				
	    				if (comboBox.getSelectedIndex() == 0) {
	    					
	    					ore1 = 438;
	    					ore2 = 436;
	    					amount1 = 14;
	    					amount2 = 14;
	    					cmd3 = 51400;
	    					
	    					start = true;
	    					
	    				} else if(comboBox.getSelectedIndex() == 1) {
	    					
	    					ore1 = 440;
	    					ore2 = 440;
	    					amount1 = 14;
	    					amount2 = 14;
	    					cmd3 = 51401;

	    					
	    					start = true;
	    					
	    				} else if(comboBox.getSelectedIndex() == 2) {
	    					
	    					ore1 = 453;
	    					ore2 = 440;
	    					amount1 = 18;
	    					amount2 = 9;
	    					cmd3 = 51403;

	    					start = true;
	    					
	    				} else if(comboBox.getSelectedIndex() == 3) {
	    					
	    					ore1 = 444;
	    					ore2 = 444;
	    					amount1 = 14;
	    					amount2 = 14;
	    					cmd3 = 51404;
	    					
	    					start = true;
	    					
	    				} else if(comboBox.getSelectedIndex() == 4) {
	    					
	    					ore1 = 453;
	    					ore2 = 447;
	    					amount1 = 20;
	    					amount2 = 5;
	    					cmd3 = 51405;
	    					
	    					start = true;
	    					
	    				} else if(comboBox.getSelectedIndex() == 5) {
	    					
	    					ore1 = 453;
	    					ore2 = 447;
	    					amount1 = 24;
	    					amount2 = 4;
	    					cmd3 = 51406;
	    					
	    					start = true;
	    					
	    				} else if(comboBox.getSelectedIndex() == 6) {
	    					
	    					ore1 = 453;
	    					ore2 = 447;
	    					amount1 = 24;
	    					amount2 = 3;
	    					cmd3 = 51407;
	    					
	    					start = true;
	    					
	    				}
	    				
	    				frame.setVisible(false);
	    				
	    			}
	    		});
	    		btnNewButton.setBounds(76, 44, 89, 23);
	    		contentPane.add(btnNewButton);
	    	}
	    	
	    	
	    	
	    }
	}


