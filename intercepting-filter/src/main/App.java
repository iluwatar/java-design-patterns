 import java.util.*;
import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
public class App{
	public static void main(String[] args){
		FilterManager filterManager = new FilterManager(new Target());
		filterManager.setFilter(new NameFilter());
		filterManager.setFilter(new ContactFilter());
		filterManager.setFilter(new AddressFilter());
		filterManager.setFilter(new DepositFilter());
		filterManager.setFilter(new OrderFilter());

		Client client = new Client();
		client.setFilterManager(filterManager);
	}
}

interface Filter{
	public String execute(String[] request);
}

class NameFilter implements Filter{
	public String execute(String[] request){
		if(request[0].equals("") || request[0].matches(".*[^\\w|\\s]+.*")){
			return null;
		}else return request[0];
	}
}

class ContactFilter implements Filter{
	public String execute(String[] request){
		if(request[1].equals("") || request[1].matches(".*[^\\d]+.*") || request[1].length() != 11){
			return null;
		}else return request[1];
	}
}

class AddressFilter implements Filter{
	public String execute(String[] request){
		if(request[2].equals("")){
			return null;
		}else return request[2];
	}
}

class DepositFilter implements Filter{
	public String execute(String[] request){
		if(request[3].equals("")){
			return null;
		}else return request[3];
	}
}

class OrderFilter implements Filter{
	public String execute(String[] request){
		if(request[4].equals("")){
			return null;
		}else return request[4];
	}
}

class Target extends JFrame{
	JTable jt;
	JScrollPane jsp;
	DefaultTableModel dtm;
	JButton del;
	public Target(){
		super("Order System");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(640,480);
		dtm = new DefaultTableModel(new Object[]{"Name", "Contact Number", "Address", "Deposit Number", "Order"},0);
		jt = new JTable(dtm);
		del = new JButton("Delete");
		setup();
	}
	private void setup(){
		setLayout(new BorderLayout());
		JPanel bot = new JPanel();
		add(jt.getTableHeader(), BorderLayout.NORTH);
		bot.setLayout(new BorderLayout());
		bot.add(del, BorderLayout.EAST);
		add(bot, BorderLayout.SOUTH);
		jsp = new JScrollPane(jt);
		jsp.setPreferredSize(new Dimension(500,250));
		add(jsp, BorderLayout.CENTER);

		del.addActionListener(new DListener());

		JRootPane rootPane = SwingUtilities.getRootPane(del); 
		rootPane.setDefaultButton(del);
		setVisible(true);
	}
	public void execute(String[] request){
		//System.out.println(request[4]);
		dtm.addRow(new Object[]{request[0],request[1],request[2],request[3],request[4]});
	}

	class DListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e){
			int temp = jt.getSelectedRow();
			if(temp == -1) return;
			int temp2 = jt.getSelectedRowCount();
			for(int i = 0; i < temp2; i++){
				dtm.removeRow(temp);
			}
		}
	}
}

class FilterChain{
	private ArrayList<Filter> filters = new ArrayList<Filter>();
	private Target target;

	public void addFilter(Filter filter){
		filters.add(filter);
	}

	public String execute(String request){
		String tempout[] = new String[filters.size()];

		String tempin[] = request.split("&");
		int i = 0;
		try{
			for(Filter filter:filters){
				tempout[i] = null;
				tempout[i++] = filter.execute(tempin);
				//System.out.println(tempout[i]);
			}
		}catch(Exception e){
			return "NOT ENOUGHT INPUT";
		}
		
		if(tempout[4] == null){
			return "INVALID ORDER!";
		}else if(tempout[3] == null){
			return "INVALID DEPOSIT NUMBER!";
		}else if(tempout[2] == null){
			return "INVALID ADRDESS!";
		}else if(tempout[1] == null){
			return "INVALID Contact Number!";
		}else if(tempout[0] == null){
			return "INVALID Name!";
		}else{
			target.execute(tempout);
			return "RUNNING...";
		}
	}

	public void setTarget(Target target){
		this.target = target;
	}
}

class FilterManager{
	FilterChain filterChain;

	public FilterManager(Target target){
		filterChain = new FilterChain();
		filterChain.setTarget(target);
	}
	public void setFilter(Filter filter){
		filterChain.addFilter(filter);
	}
	public String filterRequest(String request){
		return filterChain.execute(request);
	}
}

class Client extends JFrame{
	FilterManager filterManager;
	JLabel jl;
	JTextField[] jtfarr;
	JTextArea[] jtaarr;
	JButton[] buttarr;
	public Client(){
		super("Client System");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(300,300);
		jl = new JLabel("RUNNING...");
		jtfarr = new JTextField[3];
		for(int i = 0; i < 3; i++){
			jtfarr[i] = new JTextField();
		}
		jtaarr = new JTextArea[2];
		for(int i = 0; i < 2; i++){
			jtaarr[i] = new JTextArea();
		}
		buttarr = new JButton[2];
		buttarr[0] = new JButton("Clear");
		buttarr[1] = new JButton("Process");

		setup();
	}
	private void setup(){
		setLayout(new BorderLayout());
		JPanel panel = new JPanel();
		add(jl,BorderLayout.SOUTH);
		add(panel, BorderLayout.CENTER);
		panel.setLayout(new GridLayout(6,2));
		panel.add(new JLabel("Name"));
		panel.add(jtfarr[0]);
		panel.add(new JLabel("Contact Number"));
		panel.add(jtfarr[1]);
		panel.add(new JLabel("Address"));
		panel.add(jtaarr[0]);
		panel.add(new JLabel("Deposit Number"));
		panel.add(jtfarr[2]);
		panel.add(new JLabel("Order"));
		panel.add(jtaarr[1]);
		panel.add(buttarr[0]);
		panel.add(buttarr[1]);

		buttarr[0].addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				for(JTextArea i : jtaarr){
					i.setText("");
				}
				for(JTextField i : jtfarr){
					i.setText("");
				}
			}
		});

		buttarr[1].addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				jl.setText(sendRequest(jtfarr[0].getText()+"&"+jtfarr[1].getText()+"&"+jtaarr[0].getText()+"&"+jtfarr[2].getText()+"&"+jtaarr[1].getText()));
			}
		});

		JRootPane rootPane = SwingUtilities.getRootPane(buttarr[1]); 
		rootPane.setDefaultButton(buttarr[1]);
		setVisible(true);
	} 
	public void setFilterManager(FilterManager filterManager){
		this.filterManager = filterManager;
	}
	public String sendRequest(String request){
		return filterManager.filterRequest(request);
	}
}