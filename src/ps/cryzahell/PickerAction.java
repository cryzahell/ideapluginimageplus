package ps.cryzahell;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.fileChooser.FileChooser;
import com.intellij.openapi.fileChooser.FileChooserDescriptor;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.LocalFileSystem;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.util.Consumer;
import org.apache.commons.io.IOUtils;
import org.apache.http.util.TextUtils;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PickerAction extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent e) {
        Project project = e.getData(PlatformDataKeys.PROJECT);

        File dirHome = new File(project.getBasePath());
        ArrayList<String> dirList = new ArrayList<>();
        findDirs(dirList, dirHome);

        try {
//            UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");

            TestDialog dialog = new TestDialog();
            for (ThreeBody item : dialog.items) {
                item.getBtn().addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        FileChooserDescriptor fileChooserDescriptor = new FileChooserDescriptor(
                                true,
                                false,
                                false,
                                false,
                                false,
                                false);
                        FileChooser.chooseFile(fileChooserDescriptor, project, null, new Consumer<VirtualFile>() {
                            @Override
                            public void consume(VirtualFile virtualFile) {
                                item.getTaFile().setText(virtualFile.getPath());
                            }
                        });
                    }
                });
            }
            for (int i = 0; i < dirList.size(); i++) {
                if (i >= 10) {
                    break;
                }
                ThreeBody threeBody = dialog.items.get(i);
                threeBody.getTaDir().setText(dirList.get(i));
            }
            dialog.buttonOK.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    HashMap<String, String> map = new HashMap<>();
                    for (ThreeBody item : dialog.items) {
                        map.put(item.getTaDir().getText(), item.getTaFile().getText().trim());
                    }
                    String name = dialog.editName.getText().trim();
                    if (map.isEmpty()) {
                        // TODO: 2019/3/29 no selected files
                    } else if (TextUtils.isEmpty(name)) {
                        // TODO: 2019/3/29 do not input new file name
                    } else {
                        try {
                            copyFiles(map, name);
                            dialog.dispose();
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                    }

                }
            });

            dialog.buttonCancel.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    dialog.dispose();
                }
            });
            dialog.pack();
            dialog.setVisible(true);
        } catch (Exception a) {
            a.printStackTrace();
        }


    }

    private void copyFiles(HashMap<String, String> map, String name) throws IOException {
        File tempDir = null;
        File tempFile = null;
        for (Map.Entry<String, String> item : map.entrySet()) {
            if (item.getKey().isEmpty() || item.getValue().isEmpty()) {
                continue;
            }
            tempDir = new File(item.getKey());
            tempFile = new File(tempDir, name);
            if (tempFile.exists()) {
                tempFile.delete();
            }
            tempFile.createNewFile();
            IOUtils.copy(new FileInputStream(item.getValue()), new FileOutputStream(tempFile));
            LocalFileSystem.getInstance().refreshAndFindFileByIoFile(tempFile);
        }
    }

    private void findDirs(List<String> list, File file) {
        if (file.isDirectory()) {
            if (file.getName().startsWith("mipmap")) {
                list.add(file.getAbsolutePath());
            }
            if (!file.getName().equals("build")){
                for (File item : file.listFiles()) {
                    findDirs(list, item);
                }
            }
        }
    }

}
