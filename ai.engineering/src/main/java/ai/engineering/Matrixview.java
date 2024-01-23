package ai.engineering;

import java.util.*;
import com.change_vision.jude.api.inf.ui.IPluginExtraTabView;
import com.change_vision.jude.api.inf.ui.IPluginExtraTabView;
import com.change_vision.jude.api.inf.ui.ISelectionListener;
import com.change_vision.jude.api.inf.model.*;

import com.change_vision.jude.api.inf.project.ProjectEventListener;
import com.change_vision.jude.api.inf.project.ProjectEvent;

import java.awt.Component;

import javax.swing.Icon;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;


import javax.swing.*;

public class Matrixview extends JPanel implements IPluginExtraTabView, ProjectEventListener {
    private static JTable summaryTable;
    private static String[] headerString = {" ","1","2","3","4","5","6","7","8","9","10"};
    private static String[] rows = {"1","2","3","4","5","6","7","8","9","10"};

    public Matrixview(){
        // making the list of all elements that have hyperlinks
        List<IHyperlinkOwner> hyperlinks = ElementPicker.getAllHyperlinksOwner();
        Object[][] tableData = new Object[11][11];
        int index = 0;
        // hyperlinks.toArray(headerString);
        for (int i = 0; i < hyperlinks.size(); i++) {
            List<IEntity> related = ElementPicker.getRelatedEntities(hyperlinks.get(i));
            List<String>display = new ArrayList<String>();
            // getting names of related elements
            for (int j = 0; j < related.size(); j++) {
                INamedElement r2 = (INamedElement)related.get(j);
                display.add(r2.getName());
            }
            // set the header and first column
            if(!display.isEmpty()&& index<10){
                INamedElement r1 = (INamedElement)hyperlinks.get(i);
                headerString[index+1] = r1.getName();
                rows[index] = r1.getName();
                tableData[index][0] = r1.getName();
            }
            // positioning the data
            if(!display.isEmpty()&& index<10){
                for (int j = 0; j < display.size(); j++) {
                    String currentItem = display.get(j);
                    for (int k = 0; k < display.size(); k++) {
                        if(display.get(j).equals(tableData[k][0])){
                            tableData[k][index + 1] = "true";
                        }else{
                            tableData[k][index + 1] ="false";
                        }
                    }
                }
                index = index+1;
            }
        }


        DefaultTableModel tableModel = new DefaultTableModel(tableData, headerString){
            public Class getColumnClass(int column){
                switch(column){
                    case 5: return Icon.class;
                    default: return super.getColumnClass(column);
                }
            }
        };
        summaryTable = new JTable(tableModel);

        add(new JScrollPane(summaryTable));
    }

    public static void updateTable(){
        if(summaryTable == null){
            return;
        }


        Object[][] tableData = null;
        DefaultTableModel tableModel = new DefaultTableModel(tableData, headerString){
            public Class getColumnClass(int column){
                switch(column){
                    case 5: return Icon.class;
                    default: return super.getColumnClass(column);
                }
            }
        };
        summaryTable.setModel(tableModel);
    }

    @Override
    public void projectChanged(ProjectEvent e) {}

    @Override
    public void projectClosed(ProjectEvent e) {}

    @Override
    public void projectOpened(ProjectEvent e) {}

    @Override
    public void addSelectionListener(ISelectionListener listener) {}

    @Override
    public String getTitle() {return "Pipeline Monitoring Summary";}

    @Override
    public Component getComponent() {return this;}

    @Override
    public String getDescription() {return "Pipeline Monitoring Summary Class";}

    public void activated() {}

    public void deactivated() {}

}
