package editor;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import java.io.*;

class MyEditor extends JFrame{
    JTextArea text;
    JScrollPane scroll;
    JMenuBar bar;
    JMenu menu_file, menu_edit;
    JMenuItem mi_new, mi_open, mi_save,
    mi_exit, mi_copy, mi_paste, mi_cut;

    MyEditor(String title) {
        super(title);

        EditorEventListener event = new EditorEventListener(this);

        setLayout(new BorderLayout());
        text = new JTextArea();
        text.setLineWrap(true);

        scroll = new JScrollPane(text);
        add(scroll, BorderLayout.CENTER);

        bar = new JMenuBar();
        bar.setBorder(new BevelBorder(BevelBorder.RAISED));

        menu_file = new JMenu("File");
        menu_file.setMnemonic('F');
        mi_new = new JMenuItem("New", new ImageIcon("editor\\new.gif"));
        mi_open = new JMenuItem("Open", new ImageIcon("editor\\open.gif"));
        mi_save = new JMenuItem("Save", new ImageIcon("editor\\save.gif"));
        mi_exit = new JMenuItem("Exit", new ImageIcon("editor\\exit.gif"));

        mi_new.addActionListener(event);
        mi_open.addActionListener(event);
        mi_save.addActionListener(event);
        mi_exit.addActionListener(event);

        menu_file.add(mi_new);
        menu_file.add(mi_open);
        menu_file.add(mi_save);
        menu_file.addSeparator();
        menu_file.add(mi_exit);
        bar.add(menu_file);

        menu_edit = new JMenu("Edit");
        menu_edit.setMnemonic('E');
        mi_copy = new JMenuItem("Copy", new ImageIcon("editor\\copy.gif"));
        mi_paste = new JMenuItem("Paste", new ImageIcon("editor\\paste.gif"));
        mi_cut = new JMenuItem("Cut", new ImageIcon("editor\\cut.gif"));

        mi_copy.addActionListener(event);
        mi_paste.addActionListener(event);
        mi_cut.addActionListener(event);

        menu_edit.add(mi_copy);
        menu_edit.add(mi_paste);
        menu_edit.add(mi_cut);
        bar.add(menu_edit);

        setJMenuBar(bar);
        setDefaultCloseOperation(
        JFrame.EXIT_ON_CLOSE);
        this.setSize(500, 300);
        this.setVisible(true);
    }

    public static void main(String s[]) {
        MyEditor app = new MyEditor("My Editor");
    }
}

class EditorEventListener implements ActionListener {
    private MyEditor editor;
    private JFileChooser file_chooser;

    EditorEventListener (MyEditor editor) {
        this.editor = editor;
    }

    public void actionPerformed(ActionEvent e) {
        JMenuItem source = (JMenuItem)e.getSource();

        //クリックされたメニュー項目によりイベントを分岐
        if (source == editor.mi_new) {
            editor.text.setText("");

        }else if(source == editor.mi_open) {
            file_chooser = new JFileChooser();
            int ret =  file_chooser.showOpenDialog(editor);
            if (ret == JFileChooser.APPROVE_OPTION) {

                //ファイルを読み込む処理を行う
                try {
                    String strLine;
                    File file = file_chooser.getSelectedFile();
                    BufferedReader reader =  new BufferedReader(
                                  new FileReader(file.getAbsolutePath()));
                    editor.text.setText(reader.readLine());
                        while ((strLine =reader.readLine()) != null) {
                        editor.text.append("\n" + strLine);
                    }
                    reader.close();
                }catch (IOException ie) { }
            }

        }else if(source == editor.mi_save) {
            file_chooser = new JFileChooser();
            int intRet = file_chooser.showSaveDialog(editor);
            if(intRet == JFileChooser.APPROVE_OPTION){

                //ファイルを保存する処理を行う
                try {
                    File file =file_chooser.getSelectedFile();
                    PrintWriter writer = new PrintWriter(
                               new BufferedWriter(new FileWriter(
                                    file.getAbsolutePath())));
                    writer.write(
                    editor.text.getText());
                    writer.flush();
                    writer.close();
                } catch (IOException ie) { }
            }

        //終了
        }else if(source == editor.mi_exit) {
            System.exit(0);

        //コピー
        }else if(source == editor.mi_copy) {
            editor.text.copy();

        //ペースト
        }  else if(source == editor.mi_paste) {
            editor.text.paste();

        //カット
        }  else if(source == editor.mi_cut) {
            editor.text.cut();
        }
    }
}
