package reversi_Coursework;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Model model = new Model();
		
		new View(model, false, "White").createGUI();
		new View(model, false, "Black").createGUI();
	}

}
