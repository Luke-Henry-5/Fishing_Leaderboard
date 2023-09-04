package FishingApp;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class FishingApp {
    JLabel first, second, third;
    ArrayList<Fisherman> lst = new ArrayList<>();
    public static FishingApp app;
    public static void main(String[] args) {
        app = new FishingApp();
    }
    public void buildGUI() {
        JFrame frame = new JFrame("Fishermen");
        JPanel panel = new JPanel();
        frame.setSize(400,300);
        panel.setLayout(null);

        JLabel header = new JLabel("Current Fishermen:");
        header.setBounds(10, -30, 150, 100);
		panel.add(header);

        JList<Fisherman> fishermen = new JList<Fisherman>();
		DefaultListModel<Fisherman> model = new DefaultListModel<Fisherman>();
        //fishermen.addListSelectionListener((event) -> System.out.println(fishermen.getSelectedValue().toString()));
        //fishermen.addListSelectionListener((event) -> printPeeps(fishermen, model));
        

		fishermen.setModel(model);
		fishermen.setBounds(10, 50, 150, 200);
        populateList(model, "FishingApp/data.txt");
        panel.add(fishermen);

        JButton addB = new JButton("Add Fisherman");
		addB.setBounds(200, 50, 140, 50);
        addB.addActionListener((event) -> {
            newFisher(fishermen, model, "FishingApp/data.txt");
        });		
		panel.add(addB);

        JButton editB = new JButton("New Fishing Trip");
		editB.setBounds(200, 110, 140, 50);
		editB.addActionListener((event) -> editList(fishermen, model, "FishingApp/data.txt"));
		panel.add(editB);

        JLabel ranking = new JLabel("TOP 3:");
        ranking.setBounds(250, 130, 150, 100);
		panel.add(ranking);

        first = new JLabel("");
        first.setBounds(230, 145, 150, 100);
		panel.add(first);

        second = new JLabel("");
        second.setBounds(230, 160, 150, 100);
		panel.add(second);

        third = new JLabel("");
        third.setBounds(230, 175, 150, 100);
		panel.add(third);

        sortList(lst);
        setTop3();

        frame.add(panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
    public void newFisher(JList<Fisherman> fishermen, DefaultListModel<Fisherman> model, String filepath) {
        JFrame frame = new JFrame();
        JPanel panel = new JPanel();
        frame.setSize(550, 250);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(panel);
        panel.setLayout(null);

        JLabel intro = new JLabel("<html>Enter the name of the fisherman "
                            + "and the number of fish caught to calculate <br>points, "
                            + "largemouth are worth 2 points, smallmouth are worth "
                            + "3 points, pike are <br> worth 5, and muskie are worth 10!</html>");
        intro.setBounds(10, 135, 1000, 90);
        panel.add(intro);
        
        JLabel sum = new JLabel("Total Points: ");
        sum.setBounds(10, 120, 100, 24);
        panel.add(sum);
        
        JLabel header = new JLabel("Fisher:");
        header.setBounds(10, 20, 80, 24);
        panel.add(header);
        
        JTextField fisher = new JTextField(20);
        fisher.setBounds(10, 50, 125, 20);
        panel.add(fisher);
        
        JLabel lmBass = new JLabel("Number of LargeMouth Bass:                      (2 pts)");
        lmBass.setBounds(190, -75, 400, 200);
        JTextField largeMouth_N = new JTextField(20);
        largeMouth_N.setBounds(370, 10, 50, 30);
        panel.add(lmBass);
        panel.add(largeMouth_N);
        
        JLabel smBass = new JLabel("Number of SmallMouth Bass:                      (3 pts)");
        smBass.setBounds(190, -45, 400, 200);
        JTextField smallMouth_N = new JTextField(20);
        smallMouth_N.setBounds(370, 40, 50, 30);
        panel.add(smBass);
        panel.add(smallMouth_N);
        
        JLabel nPike = new JLabel("Number of Northern Pike:                             (5 pts)");
        nPike.setBounds(190, -15, 400, 200);
        JTextField pike_N = new JTextField(20);
        pike_N.setBounds(370, 70, 50, 30);
        panel.add(nPike);
        panel.add(pike_N);
        
        JLabel nMuskie = new JLabel("Number of Muskellunge:                               (10 pts)");
        nMuskie.setBounds(190, 15, 400, 200);
        JTextField  muskie_N = new JTextField(20);
        muskie_N.setBounds(370, 100, 50, 30);
        panel.add(nMuskie);
        panel.add(muskie_N);
        
        
        JButton calc = new JButton("Calculate");
        calc.setBounds(10, 80, 100, 25);
        calc.addActionListener((event) -> {
            try {
                caclulatePtsNew(fisher.getText(), filepath, model, fishermen, frame, largeMouth_N, smallMouth_N, pike_N, muskie_N);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        panel.add(calc);
        frame.setVisible(true);
    }
    public void editFisher(String name, int pts, JList<Fisherman> fishermen, DefaultListModel<Fisherman> model, String filepath) {
        JFrame frame = new JFrame();
        JPanel panel = new JPanel();
        frame.setSize(550, 250);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(panel);
        panel.setLayout(null);

        JLabel intro = new JLabel("<html>Enter the name of the fisherman "
                            + "and the number of fish caught to calculate <br>points, "
                            + "largemouth are worth 2 points, smallmouth are worth "
                            + "3 points, pike are <br> worth 5, and muskie are worth 10!</html>");
        intro.setBounds(10, 135, 1000, 90);
        panel.add(intro);
        
        JLabel sum = new JLabel("Total Points: ");
        sum.setBounds(10, 120, 100, 24);
        panel.add(sum);
        
        JLabel header = new JLabel("Fisher:");
        header.setBounds(10, 20, 80, 24);
        panel.add(header);
        
        JLabel fisher = new JLabel(name);
        fisher.setBounds(10, 50, 125, 20);
        panel.add(fisher);
        
        JLabel lmBass = new JLabel("Number of LargeMouth Bass:                      (2 pts)");
        lmBass.setBounds(190, -75, 400, 200);
        JTextField largeMouth_N = new JTextField(20);
        largeMouth_N.setBounds(370, 10, 50, 30);
        panel.add(lmBass);
        panel.add(largeMouth_N);
        
        JLabel smBass = new JLabel("Number of SmallMouth Bass:                      (3 pts)");
        smBass.setBounds(190, -45, 400, 200);
        JTextField smallMouth_N = new JTextField(20);
        smallMouth_N.setBounds(370, 40, 50, 30);
        panel.add(smBass);
        panel.add(smallMouth_N);
        
        JLabel nPike = new JLabel("Number of Northern Pike:                             (5 pts)");
        nPike.setBounds(190, -15, 400, 200);
        JTextField pike_N = new JTextField(20);
        pike_N.setBounds(370, 70, 50, 30);
        panel.add(nPike);
        panel.add(pike_N);
        
        JLabel nMuskie = new JLabel("Number of Muskellunge:                               (10 pts)");
        nMuskie.setBounds(190, 15, 400, 200);
        JTextField  muskie_N = new JTextField(20);
        muskie_N.setBounds(370, 100, 50, 30);
        panel.add(nMuskie);
        panel.add(muskie_N);
        
        
        JButton calc = new JButton("Calculate");
        calc.setBounds(10, 80, 100, 25);
        calc.addActionListener((event) -> {
            try {
                caclulatePtsEdit(fisher.getText(), pts, filepath, model, fishermen, frame, largeMouth_N, smallMouth_N, pike_N, muskie_N);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        panel.add(calc);
        frame.setVisible(true);
    }
    public void caclulatePtsEdit(String text, int pts, String filepath, DefaultListModel<Fisherman> model,
            JList<Fisherman> fishermen, JFrame frame, JTextField largeMouth_N, JTextField smallMouth_N,
            JTextField pike_N, JTextField muskie_N) throws IOException {
                int total = 0;
                int large = 0;
                int small = 0;
                int pike = 0;
                int muskie = 0;
                if(!largeMouth_N.getText().isEmpty()) {
                    large = 2 * Integer.parseInt(largeMouth_N.getText());
                }
                if(!smallMouth_N.getText().isEmpty()) {
                    small = 3 * Integer.parseInt(smallMouth_N.getText());
                }
                if(!pike_N.getText().isEmpty()) {
                    pike = 5 * Integer.parseInt(pike_N.getText());
                }
                if(!muskie_N.getText().isEmpty()) {
                    muskie = 10 * Integer.parseInt(muskie_N.getText());
                }
                total = large + small + pike + muskie;
        
                RandomAccessFile raf = new RandomAccessFile("FishingApp/data.txt", "rw");
                int linelen = fishermen.getSelectedIndex() * 16;
                raf.seek(linelen+9);
                raf.writeBytes(properEditFormat(fishermen.getSelectedValue(), total));
                raf.close();
                populateList(model, filepath);
                setTop3();
                frame.setVisible(false);
    }
    public String properEditFormat(Fisherman temp, int pt) {
        String points = temp.getPoints()+pt + "";
        while(points.length() !=6) {
            points += " ";
        }
        if(points.length() > 6) {
            points.substring(0,6);
        }
        return points;
    }
    public void caclulatePtsNew(String name, String filepath, DefaultListModel<Fisherman> model, JList<Fisherman> fishermen, JFrame frame, JTextField largeMouth_N, JTextField smallMouth_N, JTextField pike_N, JTextField muskie_N) throws IOException {
        int total = 0;
        int large = 0;
        int small = 0;
        int pike = 0;
        int muskie = 0;
        if(!largeMouth_N.getText().isEmpty()) {
            large = 2 * Integer.parseInt(largeMouth_N.getText());
        }
        if(!smallMouth_N.getText().isEmpty()) {
            small = 3 * Integer.parseInt(smallMouth_N.getText());
        }
        if(!pike_N.getText().isEmpty()) {
            pike = 5 * Integer.parseInt(pike_N.getText());
        }
        if(!muskie_N.getText().isEmpty()) {
            muskie = 10 * Integer.parseInt(muskie_N.getText());
        }
        total = large + small + pike + muskie;
        System.out.println(total);

        RandomAccessFile raf = new RandomAccessFile("FishingApp/data.txt", "rw");
        raf.seek(raf.length());
        Fisherman temp = new Fisherman(name, total);
        raf.writeBytes(properFormat(temp,0));
        raf.close();
        populateList(model, filepath);
        setTop3();
        frame.setVisible(false);
    }
    public String properFormat(Fisherman temp, int prevPoints) {
        String name = temp.getName();
        while(name.length() != 8) {
            name += " ";
        }
        if(name.length() > 8) {
            name = name.substring(0,8);
        }
        String points = temp.getPoints()+prevPoints + "";
        while(points.length() !=6) {
            points += " ";
        }
        if(points.length() > 6) {
            points.substring(0,6);
        }
        name += ":" + points +"\n";
        return name;
    }
    public void populateList(DefaultListModel<Fisherman> model, String filepath) {
        model.clear();
        File fyle = new File(filepath);
        try {
            Scanner x = new Scanner(fyle);
            String tempName = "";
            String tempPoints = "";
            x.useDelimiter("[:\n]");
            while(x.hasNext()) {
                tempName = x.next().trim();
                tempPoints = x.next().trim();
                Fisherman temp = (new Fisherman(tempName, Integer.parseInt(tempPoints)));
                model.addElement(temp);
            }
            x.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        lst.clear();
        int n = model.size();
        for (int i = 0; i < n; i++) {
            lst.add(model.get(i));
        }
        sortList(lst);
    }
    public void setTop3() {
        if (lst.size() >= 3) {
            first.setText(lst.get(0).toString());
            second.setText(lst.get(1).toString());
            third.setText(lst.get(2).toString());
        }
    }
    public void sortList(ArrayList<Fisherman> lst) {
        if (lst.size() > 1) {
            for (int i =0; i < lst.size()-1; i++) {
                for (int j = 0; j < lst.size() -1; j++) {
                    if (lst.get(j).getPoints() < lst.get(j+1).getPoints()) {
                        swap(lst, j, j+1);
                    }
                }
            }
        }
        
    }
    public ArrayList<Fisherman> swap(ArrayList<Fisherman> ls, int j, int i) {
        Fisherman t = ls.get(i);
        Fisherman s = ls.get(j);
        Fisherman b = ls.get(i);
        b = s;
        s = t;
        ls.set(j,s);
        ls.set(i, b);
        return ls;
    }
    public void editList(JList<Fisherman> fishermen, DefaultListModel<Fisherman> model, String filepath) {
        try {
            int pts = fishermen.getSelectedValue().getPoints();
            String name = fishermen.getSelectedValue().getName();
            editFisher(name, pts, fishermen, model, filepath);
            model.setElementAt(fishermen.getSelectedValue(), fishermen.getSelectedIndex());
        } catch (Exception e) {
            System.out.println("Please Select Someone");
        }

        
        
    }
    public FishingApp() {
        buildGUI();
    }
}