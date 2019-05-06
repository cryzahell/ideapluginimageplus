package ps.cryzahell;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetAdapter;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TestDialog extends JDialog {
    public JPanel contentPane;
    public JButton buttonOK;
    public JButton buttonCancel;
    public JTextArea taDir1;
    public JTextArea taDir2;
    public JTextArea taDir3;
    public JTextArea taDir4;
    public JTextArea taDir5;
    public JTextArea taDir6;
    public JTextArea taDir7;
    public JTextArea taDir8;
    public JTextArea taDir9;
    public JTextArea taDir10;
    public JTextArea taFile1;
    public JTextArea taFile2;
    public JTextArea taFile3;
    public JTextArea taFile4;
    public JTextArea taFile5;
    public JTextArea taFile6;
    public JTextArea taFile7;
    public JTextArea taFile8;
    public JTextArea taFile9;
    public JTextArea taFile10;
    public JEditorPane editName;
    public JButton button1;
    public JButton button2;
    public JButton button3;
    public JButton button4;
    public JButton button5;
    public JButton button6;
    public JButton button7;
    public JButton button8;
    public JButton button9;
    public JButton button10;

    public ArrayList<ThreeBody> items = new ArrayList<>();

    public TestDialog() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);



        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                dispose();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);


        items.add(new ThreeBody(taDir1, taFile1, button1));
        items.add(new ThreeBody(taDir2, taFile2, button2));
        items.add(new ThreeBody(taDir3, taFile3, button3));
        items.add(new ThreeBody(taDir4, taFile4, button4));
        items.add(new ThreeBody(taDir5, taFile5, button5));
        items.add(new ThreeBody(taDir6, taFile6, button6));
        items.add(new ThreeBody(taDir7, taFile7, button7));
        items.add(new ThreeBody(taDir8, taFile8, button8));
        items.add(new ThreeBody(taDir9, taFile9, button9));
        items.add(new ThreeBody(taDir10, taFile10, button10));

        for (ThreeBody item : items) {
            item.getTaDir().setMinimumSize(new Dimension(600, 30));
            item.getTaFile().setMinimumSize(new Dimension(600, 30));

            //拖入
            new DropTarget(item.getTaFile(), DnDConstants.ACTION_COPY_OR_MOVE, new DropTargetAdapter() {
            @Override
            public void drop(DropTargetDropEvent dtde) {
                try {
                    if (dtde.isDataFlavorSupported(DataFlavor.javaFileListFlavor)) {
                        dtde.acceptDrop(DnDConstants.ACTION_COPY_OR_MOVE);
                        List<File> fileList = (List<File>) dtde.getTransferable().getTransferData(DataFlavor.javaFileListFlavor);
                        for (File file : fileList) {
                            item.getTaFile().setText(file.getAbsolutePath());
                        }
                    } else {
                        dtde.rejectDrop();
                    }
                } catch (UnsupportedFlavorException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        }


    }

    public static void main(String[] args) {
        TestDialog dialog = new TestDialog();
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }

}
