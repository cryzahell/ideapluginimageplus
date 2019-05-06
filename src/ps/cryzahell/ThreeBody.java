package ps.cryzahell;

import javax.swing.*;

public class ThreeBody {
    private JTextArea taDir;
    private JTextArea taFile;
    private JButton btn;

    public ThreeBody(JTextArea taDir, JTextArea taFile, JButton btn) {
        this.taDir = taDir;
        this.taFile = taFile;
        this.btn = btn;
    }

    public JTextArea getTaDir() {
        return taDir;
    }

    public void setTaDir(JTextArea taDir) {
        this.taDir = taDir;
    }

    public JTextArea getTaFile() {
        return taFile;
    }

    public void setTaFile(JTextArea taFile) {
        this.taFile = taFile;
    }

    public JButton getBtn() {
        return btn;
    }

    public void setBtn(JButton btn) {
        this.btn = btn;
    }
}
