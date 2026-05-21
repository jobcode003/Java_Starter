import javax.swing.*;

public class ExamPractice{
    ExamPractice(){
        JFrame frame = new JFrame("Exam Practice");
        final JScrollBar scroll = new JScrollBar();
        scroll.setBounds(100,100,50,1000);
        frame.add(scroll);

        String data[][] = {
            {"job", "20", "300000"}, 
            {"pete", "25", "600000"},
             {"iko", "28", "900000"}
            };
        String column[] = {"name", "age", "salary"};
        JTable table=new JTable(data,column);
        table.setBounds(30,40,200,300);
        JScrollPane sp=new JScrollPane(table);
        frame.add(sp);           

        frame.setSize(300,400);
        frame.setVisible(true);
        frame.setLayout(null);

       
    }



    public static void main(String[] args) {
    new ExamPractice();
}
}

