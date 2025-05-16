package mvc;


public interface AppFactory {
    public Model makeModel();
    public View makeView(Model m);
    public String getTitle();
    public String[] getHelp(); //String* return type
    public String about();
    public String[] getEditCommands();  //String* return type
    public Command makeEditCommands(Model model, String type, Object source);
}
