import java.awt.EventQueue;

public class Starter {

	public static void main(String[] args){
		EventQueue.invokeLater(new Runnable(){

			@Override
			public void run() {
				try{
					Controller controller = new Controller();
				}
				catch(Exception e){
					e.printStackTrace();
				}
			}
			
		});
	}
}
